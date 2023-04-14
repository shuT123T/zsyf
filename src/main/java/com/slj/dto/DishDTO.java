package com.slj.dto;

import com.slj.domain.Dish;
import com.slj.domain.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-29
 */
@Data
public class DishDTO extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
