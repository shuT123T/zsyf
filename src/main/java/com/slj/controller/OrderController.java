package com.slj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slj.common.Result;
import com.slj.domain.*;
import com.slj.dto.OrdersDTO;
import com.slj.dto.EcharsDTO;
import com.slj.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理接口")
public class OrderController {
    @Autowired
    private OrdersService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("订单保存接口")
    public Result<String> submit(@RequestBody Orders orders) {
        log.info("订单数据：{}", orders);
        orderService.submit(orders);
        return Result.success("下单成功");
    }

    /**
     * 用户订单查询
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/userPage")
    public Result<Page<OrdersDTO>> page(Integer page, Integer pageSize, HttpServletRequest request) {
        Page<OrdersDTO> dtoPage = orderService.getOrdersDtoPage(page, pageSize, request);
        return Result.success(dtoPage);

    }


    /**
     * 管理员订单查询条件
     * @param page
     * @param pageSize
     * @param number 订单号
     * @return
     */
    @GetMapping("/page")
    public Result<Page<Orders>> getPage(Integer page,
                                        Integer pageSize,
                                        String number,
                                        String beginTime,
                                        String endTime) {
        Page<Orders> info = orderService.getOrdersPage(page, pageSize, number, beginTime, endTime);
        return Result.success(info);
    }


    /**
     * 修改订单状态
     * @param orders
     * @return
     */
    @PutMapping
    public Result<String> updateStatus(@RequestBody Orders orders){
        log.info("orders = {}", orders.toString());
        orderService.updateOrderStatus(orders);
        return Result.success("修改成功");
    }


    /**
     * 回购
     *
     * @param id
     * @return
     */
    @PostMapping("/again")
    public Result<ShoppingCart> again(@RequestParam("id") Long id){
        orderService.againOrder(id);
        return Result.success(null);
    }


    /**
     *  订单统计
     * @return 返回每个月的订单
     */
    @GetMapping("/ordersCount")
    public Result dataMessage(){
        EcharsDTO echarsDto = orderService.ordersCount();
        return Result.success(echarsDto);
    }

}