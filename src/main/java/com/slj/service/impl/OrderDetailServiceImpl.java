package com.slj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.domain.OrderDetail;
import com.slj.service.OrderDetailService;
import com.slj.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author Mr.shu
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-11-17 14:20:44
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




