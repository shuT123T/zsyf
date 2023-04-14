package com.slj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.common.BaseContext;
import com.slj.domain.Dish;
import com.slj.domain.ShoppingCart;
import com.slj.service.DishService;
import com.slj.service.ShoppingCartService;
import com.slj.mapper.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.shu
 * @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
 * @createDate 2022-11-17 14:20:44
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
        implements ShoppingCartService {

    @Autowired
    private DishService dishService;

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {

        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);

        } else {
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        //查询当前菜品或者套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = this.getOne(queryWrapper);

        if (cartServiceOne != null) {
            //如果已经存在，就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            this.updateById(cartServiceOne);
        } else {
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            this.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return cartServiceOne;
    }

    @Override
    public ShoppingCart sub(ShoppingCart shoppingCart) {
        // 设置当前购物车的用户id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        // 获取菜品或套餐的数量
        lqw.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId());
        lqw.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

        ShoppingCart one = this.getOne(lqw);

        if (one.getNumber() == 1) { // 若只有一件则将其在购物车删除
            this.removeById(one.getId());
            one.setNumber(0);
        } else { // 数量减一
            one.setNumber(one.getNumber() - 1);
            this.updateById(one);
        }

        return one;
    }

    @Override
    public List<ShoppingCart> getShoppingCarts() {
        // 查询未停售商品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
        List<Dish> list1 = dishService.list(dishLambdaQueryWrapper);
        ArrayList<Long> dishIdlist = new ArrayList<>();
        for (Dish dish : list1) {
            dishIdlist.add(dish.getId());
        }
        // 删除以过期的菜品
        LambdaUpdateWrapper<ShoppingCart> cartLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        cartLambdaUpdateWrapper.notIn(ShoppingCart::getDishId,dishIdlist);
        this.remove(cartLambdaUpdateWrapper);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = this.list(queryWrapper);
        return list;
    }
}




