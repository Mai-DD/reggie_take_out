package com.maidou.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maidou.reggie.dto.DishDto;
import com.maidou.reggie.entity.Category;
import com.maidou.reggie.entity.Dish;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:58
 **/
public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
    DishDto getByIdWithFlavor(Long id);
    void updateWithFlavor(DishDto dishDto);
}
