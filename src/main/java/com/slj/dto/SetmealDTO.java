package com.slj.dto;

import com.slj.domain.Setmeal;
import com.slj.domain.SetmealDish;
import lombok.Data;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-30
 */
@Data
public class SetmealDTO extends Setmeal {
    private List<SetmealDish> setmealDishes;
    private String categoryName;
}
