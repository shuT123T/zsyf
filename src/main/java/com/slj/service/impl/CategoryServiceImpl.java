package com.slj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.common.CustomException;
import com.slj.domain.Category;
import com.slj.domain.Dish;
import com.slj.domain.Setmeal;
import com.slj.mapper.CategoryMapper;
import com.slj.service.CategoryService;
import com.slj.service.DishService;
import com.slj.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);

        // 查询当前分类是否关联了菜品，如果已关联，判处一个业务异常
        if (count > 0) {
            //已关联菜品，抛出一个业务异常
            throw  new CustomException("当前分类下关联有菜品，不能删除");
        }
        // 查询当前分类是否关联了套餐，如果已关联，判处一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 0) {
            // 已关联套裁，抛出一个业务异常
            throw  new CustomException("当前分类下关联有套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);

    }

    @Override
    public List<Category> getCategories(Category category) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(category.getType() != null,Category::getType, category.getType());
        // 添加排序条件
        lqw.orderByAsc(Category::getSort).orderByAsc(Category::getCreateTime);
        List<Category> list = this.list(lqw);
        return list;
    }

    @Override
    public Page<Category> getCategoryPage(Integer page, Integer pageSize) {
        Page<Category> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        // 升序排序
        lqw.orderByAsc(Category::getSort);
        this.page(page1,lqw);
        return page1;
    }
}
