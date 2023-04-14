package com.slj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.slj.dto.DishDTO;
import com.slj.domain.Dish;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-29
 */
public interface DishService extends IService<Dish> {
    // 新增菜品，同时插入菜品对应的口味数据，需要同时操作两张表： dish、dish_flavor
    void saveWithFlavor(DishDTO dishDto);

    // 根据id查询菜品和对应的口味信息
    DishDTO getByIdWithFlavor(Long id);

    // 跟新菜品信息，同时跟新口味信息
    void updateWithFlavor(DishDTO dishDto);

    // 分页查询菜品
    Page<DishDTO> getDishDtoPage(Integer page, Integer pageSize, String name);

    // 查询菜品
    List<DishDTO> getDishDtos(Dish dish);

    // 更新菜品的状态
    void updateStatus(List<Long> ids, Integer type);

    // 删除菜品
    void deleteDish(List<Long> ids);
}
