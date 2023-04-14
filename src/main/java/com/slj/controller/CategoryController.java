package com.slj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slj.common.CustomException;
import com.slj.common.Result;
import com.slj.domain.Category;
import com.slj.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-29
 */
@Slf4j
@RestController
@RequestMapping("/category")
@Api(tags = "菜品及套餐分类管理接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Page<Category>> page(Integer page, Integer pageSize){
        log.info("page = {},pageSize = {}",page,pageSize);
        Page<Category> page1 = categoryService.getCategoryPage(page, pageSize);
        return Result.success(page1);
    }


    /**
     * 新增菜品分类
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody Category category){
        try {
            categoryService.save(category);
        } catch (Exception e) {
            throw new CustomException("分类名重复");
        }
        return Result.success("新增成功");
    }

    /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody Category category){
        try {
        categoryService.updateById(category);
        } catch (Exception e) {
            throw new CustomException("分类名重复");
        }
        return Result.success("修改分类信息成功");
    }

    /**
     * 根据id删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam("id") Long ids){
        categoryService.remove(ids);
        return Result.success("分类信息删除成功");
    }

    /**
     * 获取分类列表
     * @param category
     * @return
     */
    @GetMapping("list")
    public Result<List<Category>> list(Category category){
        List<Category> list = categoryService.getCategories(category);
        return Result.success(list);
    }

}
