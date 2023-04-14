package com.slj.dto;

import com.slj.domain.OrderDetail;
import com.slj.domain.Orders;
import lombok.Data;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-11-15
 */
@Data
public class OrdersDTO extends Orders {
    private List<OrderDetail> orderDetails;
}
