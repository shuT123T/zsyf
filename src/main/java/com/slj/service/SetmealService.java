package com.slj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.slj.dto.SetmealDTO;
import com.slj.domain.Setmeal;

import java.util.List;

/*
 * @Author shu
 * @Create 2022-10-29
 */
public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDTO setmealDto);

    SetmealDTO getByIdWithDish(Long id);

    void removeWithDish(List<Long> ids);

    void updateWithDish(SetmealDTO setmealDto);
}
