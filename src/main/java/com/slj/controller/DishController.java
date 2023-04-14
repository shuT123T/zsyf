package com.slj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slj.common.Result;
import com.slj.domain.Dish;
import com.slj.dto.DishDTO;
import com.slj.service.CategoryService;
import com.slj.service.DishFlavorService;
import com.slj.service.DishService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-29
 */

/**
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
@Api(tags = "菜品管理接口")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page<DishDTO>> page(Integer page, Integer pageSize, String name) {
        Page<DishDTO> dishDtoPage = dishService.getDishDtoPage(page, pageSize, name);

        return Result.success(dishDtoPage);
    }


    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "dishCache",allEntries = true)
    public Result<String> save(@RequestBody DishDTO dishDto) {
        dishService.saveWithFlavor(dishDto);
        return Result.success("新增菜品成功");
    }

    /**
     * 根据id查询菜品信息，和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDTO> edit(@PathVariable Long id){
        DishDTO dishDto = dishService.getByIdWithFlavor(id);
        return Result.success(dishDto);
    }

    /**
     * 更新菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    @CacheEvict(value = "dishCache",allEntries = true)
    public Result<String> update(@RequestBody DishDTO dishDto) {
        dishService.updateWithFlavor(dishDto);
        return Result.success("修改成功");
    }

    /**
     * 根据条件查询菜品
     * @param dish
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "dishCache",key = "#dish.categoryId+'_'+#dish.status")
    public Result<List<DishDTO>> list(Dish dish){
        List<DishDTO> dishDtos = dishService.getDishDtos(dish);
        return Result.success(dishDtos);
    }


    /**
     * 修改菜品状态 起售 || 停售
     * @param ids
     * @param type
     * @return
     */
    @PostMapping("/status/{type}")
    @CacheEvict(value = "dishCache",allEntries = true)
    public Result<String> status(@RequestParam("ids") List<Long> ids, @PathVariable Integer type){
        dishService.updateStatus(ids, type);
        return Result.success("菜品状态更改成功！");
    }


    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam("ids") List<Long >ids){
        dishService.deleteDish(ids);
        return Result.success("删除成功");
    }

}
