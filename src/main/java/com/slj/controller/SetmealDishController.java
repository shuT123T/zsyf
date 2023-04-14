package com.slj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slj.common.Result;
import com.slj.domain.Setmeal;
import com.slj.dto.DishDTO;
import com.slj.dto.SetmealDTO;
import com.slj.service.SetmealDishService;
import com.slj.service.SetmealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-30
 */

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
@Api(tags = "套餐管理接口")
public class SetmealDishController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 分页查询套餐信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page<SetmealDTO>> page(Integer page, Integer pageSize, String name) {
        Page<SetmealDTO> dtoPage = setmealDishService.getSetmealDtoPage(page, pageSize, name);
        return Result.success(dtoPage);
    }


    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody SetmealDTO setmealDto) {
        log.info("套餐保存 = {}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return Result.success("新增套餐成功");
    }

    /**
     * 根据套餐id获取套餐内的菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealDTO> qeruyById(@PathVariable Long id) {
        SetmealDTO setmealDto = setmealService.getByIdWithDish(id);
        return Result.success(setmealDto);
    }

    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public Result<String> delete(@RequestParam List<Long> ids) {
        setmealService.removeWithDish(ids);
        return Result.success("套餐数据删除成功");
    }

    /**
     * 修改套餐状态
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(value = "setmealCache",allEntries = true)
    public Result<String> update(@PathVariable Integer status, @RequestParam List<Long> ids) {

        LambdaUpdateWrapper<Setmeal> luw = new LambdaUpdateWrapper<>();
        luw.in(Setmeal::getId, ids);
        luw.set(Setmeal::getStatus, status);
        setmealService.update(luw);
        return Result.success(status == 0 ? "商品停售" : "商品起售");
    }

    /**
     * 条件查询套餐
     *
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#categoryId+ '_' + #status")
    public Result<List<Setmeal>> list(Long categoryId, Integer status) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getCategoryId, categoryId).eq(Setmeal::getStatus, status);
        lqw.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(lqw);
        return Result.success(list);
    }

    /**
     * 根据套餐id获取信息（用户端）
     *
     * @param setmealDishId
     * @return
     */
    @GetMapping("/dish/{setmealDishId}")
    public Result<List<DishDTO>> list(@PathVariable Long setmealDishId){
        List<DishDTO> dishes = setmealDishService.getDishBySetmealId(setmealDishId);
        return Result.success(dishes);
    }

    /**
     * 套餐更新
     * @param setmealDto
     * @return
     */
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDto){
        setmealService.updateWithDish(setmealDto);
        return Result.success("更新成功");
    }

}
