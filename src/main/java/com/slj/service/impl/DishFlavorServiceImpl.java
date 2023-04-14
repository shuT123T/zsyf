package com.slj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.domain.DishFlavor;
import com.slj.service.DishFlavorService;
import com.slj.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author Mr.shu
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-11-17 14:20:44
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




