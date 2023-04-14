package com.slj.mapper;

import com.slj.domain.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slj.domain.Echars;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @author Mr.shu
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-11-17 14:20:44
* @Entity com.slj.domain.Orders
*/
public interface OrdersMapper extends BaseMapper<Orders> {

    Map ordersCount();

    List<Echars> listEchars();
}




