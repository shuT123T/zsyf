package com.slj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slj.domain.SetmealDish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.slj.dto.DishDTO;
import com.slj.dto.SetmealDTO;

import java.util.List;

/**
* @author Mr.shu
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service
* @createDate 2022-11-17 14:20:44
*/
public interface SetmealDishService extends IService<SetmealDish> {

    // 根据套餐id查询其下面的菜品
    List<DishDTO> getDishBySetmealId(Long setmealDishId);

    //获取页面信息
    Page<SetmealDTO> getSetmealDtoPage(Integer page, Integer pageSize, String name);
}
