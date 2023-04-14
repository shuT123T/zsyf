package com.slj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.common.BaseContext;
import com.slj.common.CustomException;
import com.slj.domain.*;
import com.slj.dto.EcharsDTO;
import com.slj.dto.OrdersDTO;
import com.slj.mapper.OrdersMapper;
import com.slj.service.*;
import com.slj.domain.Echars;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*
 * @Author shu
 * @Create 2022-10-28
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService{
    @Autowired
    private OrdersMapper orderMapper;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private DishService dishService;


    /**
     * 用户下单
     * @param orders
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();

        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if(shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }

        //查询用户数据
        User user = userService.getById(userId);

        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("请选择收获地址信息");
        }

        long orderId = IdWorker.getId();//订单号

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车数据
        shoppingCartService.remove(wrapper);
    }

    /**
     * 查询每月的订单数量
     *
     * @return
     */
    @Override
    public EcharsDTO ordersCount() {
        EcharsDTO echarsDto = new EcharsDTO();
        Map map = orderMapper.ordersCount();
        echarsDto.setOrdersCount(map);
        List<Echars> list = orderMapper.listEchars();
        echarsDto.setList(list);
        return echarsDto;
    }

    @Override
    public Page<OrdersDTO> getOrdersDtoPage(Integer page, Integer pageSize, HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        Page<Orders> info = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        // 添加查询条件
        lqw.eq(Orders::getUserId, user);
        lqw.orderByDesc(Orders::getOrderTime);
        this.page(info, lqw);
        Page<OrdersDTO> dtoPage = new Page<>();
        // 拷贝数据
        BeanUtils.copyProperties(info,dtoPage,"records");
        List<Orders> records = info.getRecords();
        // 添加订单详情信息
        List<OrdersDTO> ordersDtoList = records.stream().map(item -> {
            OrdersDTO ordersDto = new OrdersDTO();
            BeanUtils.copyProperties(item,ordersDto);
            LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> orderDetails = orderDetailService.list(lambdaQueryWrapper);
            ordersDto.setOrderDetails(orderDetails);
            return ordersDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(ordersDtoList);
        return dtoPage;
    }

    @Override
    public void againOrder(Long id) {
        LambdaQueryWrapper<OrderDetail> lqw = new LambdaQueryWrapper<>();
        lqw.eq(OrderDetail::getOrderId, id);
        List<OrderDetail> detailList = orderDetailService.list(lqw);
        // 将订单中的商品放入购物车
        List<ShoppingCart> collect = detailList.stream().map(item -> {
            Long dishId = item.getDishId();
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setImage(item.getImage());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setAmount(item.getAmount());
            shoppingCart.setDishFlavor(item.getDishFlavor());
            shoppingCart.setName(item.getName());
            if (dishId != null) {
                // 是否已被下架
                Dish byId = dishService.getById(dishId);
                if (byId==null) return null;
                shoppingCart.setDishId(dishId);
            } else {
                // 是否已被下架
                Setmeal byId = setmealService.getById(item.getSetmealId());
                if (byId==null) return null;
                shoppingCart.setSetmealId(item.getSetmealId());
            }
            return shoppingCartService.add(shoppingCart);
        }).collect(Collectors.toList());
    }

    @Override
    public Page<Orders> getOrdersPage(Integer page, Integer pageSize, String number, String beginTime, String endTime) {
        Page<Orders> info = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(number), Orders::getNumber, number);
        lqw.ge(beginTime !=null, Orders::getOrderTime, beginTime);
        lqw.le(endTime !=null, Orders::getOrderTime, endTime);
        lqw.orderByDesc(Orders::getOrderTime);
        this.page(info, lqw);
        return info;
    }

    @Override
    public void updateOrderStatus(Orders orders) {
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.eq(orders.getId()!=null,Orders::getId, orders.getId());
        this.updateById(orders);
    }
}
