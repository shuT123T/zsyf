package com.slj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.common.CustomException;
import com.slj.dto.SetmealDTO;
import com.slj.domain.Setmeal;
import com.slj.domain.SetmealDish;
import com.slj.mapper.SetmealMapper;
import com.slj.service.SetmealDishService;
import com.slj.service.SetmealService;
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
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;


    /**
     * 新增套餐，同时需要保存套餐和菜品的关联信息
     *
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDto) {
        // 保存套餐的基本信息，操作 setmeal，执行insert操作
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().peek(item ->
                item.setSetmealId(setmealDto.getId())
        ).collect(Collectors.toList());

        // 保存套餐和菜品的关联信息
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public SetmealDTO getByIdWithDish(Long id) {
        SetmealDTO setmealDto = new SetmealDTO();
        // 获取套餐基本信息
        Setmeal setmeal = this.getById(id);
        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmeal.getId());
        // 获取套餐和菜品的关联信息
        List<SetmealDish> list = setmealDishService.list(lqw);

        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     *
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // 查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId, ids);
        lqw.eq(Setmeal::getStatus, 1);

        int count = this.count(lqw);
        if (count > 0) {
            // 如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        // 如果可以删除，先删除套餐表中的数据
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

        // 删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);
    }

    /**
     * 更新套餐信息
     *
     * @param setmealDto
     */
    @Override
    @Transactional
    public void updateWithDish(SetmealDTO setmealDto) {
        // 更新套餐的基本信息
//        this.updateById(setmealDto);
//        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
//        setmealDishes = setmealDishes.stream().peek(item -> item.setSetmealId(setmealDto.getId())).collect(Collectors.toList());
//        // 更新套餐和菜品的关联信息
//        // 先删除该套餐下菜品，再添加
//        LambdaQueryWrapper<SetmealDish> wrapper1 = new LambdaQueryWrapper<>();
//        wrapper1.eq(SetmealDish::getSetmealId,setmealDto.getId());
//        setmealDishService.remove(wrapper1);
        // 删除操作
        // 如果可以删除，先删除套餐表中的数据
        this.removeById(setmealDto.getId());

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, setmealDto.getId());

        // 删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);

        // 添加操作
        saveWithDish(setmealDto);
//        List<Boolean> collect = setmealDishes.stream().map(item -> {
//            LambdaUpdateWrapper<SetmealDish> wrapper = new LambdaUpdateWrapper<>();
//            wrapper.eq(SetmealDish::getSetmealId, item.getSetmealId()).eq(SetmealDish::getDishId,item.getDishId());
//            return setmealDishService.update(item, wrapper);
//        }).collect(Collectors.toList());
    }

}
