package com.slj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.common.CustomException;
import com.slj.domain.Category;
import com.slj.dto.DishDTO;
import com.slj.domain.Dish;
import com.slj.domain.DishFlavor;
import com.slj.mapper.DishMapper;
import com.slj.service.CategoryService;
import com.slj.service.DishFlavorService;
import com.slj.service.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @Author shu
 * @Create 2022-10-29
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

//    @Autowired
//    private DishMapper dishMapper;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品，同时保存对应的口味信息
     *
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDto) {
        try {
            // 保存菜品的基本信息到菜品表dish
            this.save(dishDto);

            // MP会生成id
            Long dishId = dishDto.getId();

            // 菜品口味
            List<DishFlavor> flavors = dishDto.getFlavors();
            flavors = flavors.stream().peek(item -> item.setDishId(dishId)).collect(Collectors.toList());
            // 保存菜品口味数据到菜品口味表 dish_flavor
            dishFlavorService.saveBatch(flavors);
        } catch (Exception e) {
            throw new CustomException("菜品名已存在");
        }
    }

    /**
     * 获取根据id查询菜品信息和菜品口味
     *
     * @param id
     * @return
     */
    @Override
    public DishDTO getByIdWithFlavor(Long id) {
        // 根据id查询菜品基本信息，从 dish 表查
        Dish dish = this.getById(id);

        DishDTO dishDto = new DishDTO();
        BeanUtils.copyProperties(dish, dishDto);

        // 查询当前菜品对应的口味，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(lqw);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDto) {
        try {
            // 跟新 dish 表基本信息
            this.updateById(dishDto);
        } catch (Exception e) {
            throw new CustomException("菜品名已存在");
        }

            // 清理当前菜品对应的口味数据 dish_flavor表的 delete 操作
            LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
            lqw.eq(DishFlavor::getDishId, dishDto.getId());
            dishFlavorService.remove(lqw);

            // 添加当前提交过来的口味信息
            List<DishFlavor> flavors = dishDto.getFlavors();
            flavors = flavors.stream().map(item ->
                    {
                        item.setDishId(dishDto.getId());
                        return item;
                    }
            ).collect(Collectors.toList());
            dishFlavorService.saveBatch(flavors);
    }

    @Override
    public Page<DishDTO> getDishDtoPage(Integer page, Integer pageSize, String name) {
        Page<Dish> info = new Page<>(page, pageSize);

        Page<DishDTO> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        // 添加过滤条件
        lqw.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        // 按跟新时间排序
        lqw.orderByDesc(Dish::getUpdateTime);
        // 分页查询
        this.page(info, lqw);

        // 对象拷贝
        BeanUtils.copyProperties(info, dishDtoPage, "records");

        List<Dish> records = info.getRecords();

        List<DishDTO> list = records.stream().map(item -> {
            DishDTO dishDto = new DishDTO();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = dishDto.getCategoryId();

            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return dishDtoPage;
    }

    @Override
    public List<DishDTO> getDishDtos(Dish dish) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        // 构造查询条件
        lqw.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        // 查询状态为1（起售）的菜品
        lqw.eq(dish.getStatus() != null, Dish::getStatus, 1);
        // 添加排序条件
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = this.list(lqw);

        List<DishDTO> dishDtos = list.stream().map(item -> {
            DishDTO dishDto = new DishDTO();

            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId(); // 分类id
            Category category = categoryService.getById(categoryId);

            if (category == null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            // 当前菜品id
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, id);
            List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);

            return dishDto;
        }).collect(Collectors.toList());
        return dishDtos;
    }

    @Override
    public void updateStatus(List<Long> ids, Integer type) {
        LambdaUpdateWrapper<Dish> luw = new LambdaUpdateWrapper<>();
        luw.in(Dish::getId, ids);
        luw.set(Dish::getStatus, type);
        this.update(luw);
    }

    @Override
    public void deleteDish(List<Long> ids) {
        // 若菜品在售卖则抛出异常
        this.listByIds(ids).stream().map(item -> {
            if (item.getStatus()==1) throw new CustomException("商品正在售卖中，不能删除!");
            return null;
        }).collect(Collectors.toList());

        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(lqw);
        this.removeByIds(ids);
    }
}
