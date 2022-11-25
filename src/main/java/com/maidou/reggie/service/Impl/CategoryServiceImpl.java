package com.maidou.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maidou.reggie.common.BusinessException;
import com.maidou.reggie.entity.Category;
import com.maidou.reggie.entity.Dish;
import com.maidou.reggie.entity.Employee;
import com.maidou.reggie.entity.Setmeal;
import com.maidou.reggie.mapper.CategoryMapper;
import com.maidou.reggie.mapper.EmployeeMapper;
import com.maidou.reggie.service.CategoryService;
import com.maidou.reggie.service.DishService;
import com.maidou.reggie.service.EmployeeService;
import com.maidou.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:59
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;

    /**
     * 删除菜品分类
     * @param id
     */
    @Override
    public void remove(Long id) {
        //判断是否关联dish表菜品
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishQueryWrapper);
        if(count > 0){
            //抛出业务异常
            throw new BusinessException("当前分类关联了菜品信息,无法删除");
        }


        //判断是否关联setMeal表菜品
        LambdaQueryWrapper<Setmeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setMealService.count(setMealLambdaQueryWrapper);
        //判断是否关联dish表菜品
        if(count1 > 0){
            //抛出业务异常
            throw new BusinessException("当前分类关联了套餐信息,无法删除");
        }


        //执行删除
        super.removeById(id);
    }
}
