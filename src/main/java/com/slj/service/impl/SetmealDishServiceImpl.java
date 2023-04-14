package com.slj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.domain.Category;
import com.slj.domain.Dish;
import com.slj.domain.Setmeal;
import com.slj.domain.SetmealDish;
import com.slj.dto.DishDTO;
import com.slj.dto.SetmealDTO;
import com.slj.service.CategoryService;
import com.slj.service.DishService;
import com.slj.service.SetmealDishService;
import com.slj.mapper.SetmealDishMapper;
import com.slj.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Mr.shu
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service实现
* @createDate 2022-11-17 14:20:44
*/
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish>
    implements SetmealDishService{

    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealService setmealService;
    @Override
    public List<DishDTO> getDishBySetmealId(Long setmealDishId) {
        // 根据套餐id找到所有下面菜品的id
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,setmealDishId);
        List<SetmealDish> list = setmealDishService.list(wrapper);

        // 获取菜品信息
        List<DishDTO> collect = list.stream().map(item -> {
            // 给值
            String id = item.getDishId();
            Long dishId = Long.valueOf(id);
            // 拷贝
            DishDTO dishDto = new DishDTO();
            Dish dish = dishService.getById(dishId);
            BeanUtils.copyProperties(dish,dishDto);

            dishDto.setCopies(item.getCopies());
            // 根据分类id获取分类名
            String catgoryName = categoryService.getById(dishDto.getCategoryId()).getName();
            dishDto.setCategoryName(catgoryName);
            return dishDto;
        }).collect(Collectors.toList());

        return collect;
    }

    /**
     * 获取页面信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<SetmealDTO> getSetmealDtoPage(Integer page, Integer pageSize, String name) {
        Page<Setmeal> info = new Page<>(page, pageSize);
        Page<SetmealDTO> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);

        lqw.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(info, lqw);

        //对象拷贝
        BeanUtils.copyProperties(info, dtoPage);
        List<Setmeal> records = info.getRecords();

        List<SetmealDTO> list = records.stream().map(item -> {
            SetmealDTO setmealDto = new SetmealDTO();
            BeanUtils.copyProperties(item, setmealDto);
            // 分类id
            Long categoryId = item.getCategoryId();
            // 根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                // 分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return dtoPage;
    }
}




