package com.maidou.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maidou.reggie.dto.SetmealDto;
import com.maidou.reggie.entity.Employee;
import com.maidou.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:58
 **/
public interface SetMealService extends IService<Setmeal> {

    /**
     * 新增套餐
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐
     * @param ids
     */
    void removeWithDish(List<Long> ids);

    /**
     * 根据套餐id查询
     * @param id
     * @return
     */
    SetmealDto selectWithDish(Long id);

    /**
     * 保存套餐的修改
     * @param setmealDto
     */
    void updateWithDish(SetmealDto setmealDto);
}
