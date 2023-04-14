package com.slj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.slj.domain.Category;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-29
 */
public interface CategoryService extends IService<Category> {
    void remove(Long id);

    List<Category> getCategories(Category category);

    Page<Category> getCategoryPage(Integer page, Integer pageSize);
}
