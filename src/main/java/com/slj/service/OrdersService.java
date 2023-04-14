package com.slj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.slj.domain.Orders;
import com.slj.dto.EcharsDTO;
import com.slj.dto.OrdersDTO;

import javax.servlet.http.HttpServletRequest;

/*
 * @Author shu
 * @Create 2022-10-28
 */
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);

    EcharsDTO ordersCount();

    Page<OrdersDTO> getOrdersDtoPage(Integer page, Integer pageSize, HttpServletRequest request);

    void againOrder(Long id);

    Page<Orders> getOrdersPage(Integer page, Integer pageSize, String number, String beginTime, String endTime);

    void updateOrderStatus(Orders orders);
}
