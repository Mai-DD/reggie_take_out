package com.maidou.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maidou.reggie.common.BusinessException;
import com.maidou.reggie.common.R;
import com.maidou.reggie.dto.SetmealDto;
import com.maidou.reggie.entity.Dish;
import com.maidou.reggie.entity.Employee;
import com.maidou.reggie.entity.Setmeal;
import com.maidou.reggie.entity.SetmealDish;
import com.maidou.reggie.mapper.EmployeeMapper;
import com.maidou.reggie.mapper.SetMealMapper;
import com.maidou.reggie.service.EmployeeService;
import com.maidou.reggie.service.SetMealDishService;
import com.maidou.reggie.service.SetMealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:59
 **/
@Service
@Transactional
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {

    @Autowired
    private SetMealDishService setMealDishService;

    /**
     * 新增套餐以及套餐关联的菜品
     * @param setmealDto
     */
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> dishList = setmealDto.getSetmealDishes();
        dishList.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setMealDishService.saveBatch(dishList);
    }

    /**
     * 删除套餐
     * @param ids
     */
    @Override
    public void removeWithDish(List<Long> ids) {
        //select count(*) from setmeal where id in (1,2,3) and status = 1;
        //判断是否停售(停售抛出业务异常)
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if(count > 0){
            throw new BusinessException("套餐正在售卖中~无法删除");
        }

        //删除setmeal表里的 注意这里removeByIds中id字段必须为主键
        this.removeByIds(ids);
        //删除setmeal_dish表里的 这里不能用removeByIds
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setMealDishService.remove(lambdaQueryWrapper);
    }

    /**
     * 根据id查询套餐(回显)
     * @param id
     * @return
     */
    @Override
    public SetmealDto selectWithDish(Long id) {
        //查询该id关联的菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setMealDishService.list(queryWrapper);

        //查询基本信息
        Setmeal setmeal = this.getById(id);

        //封装成dto
        SetmealDto setmealDto = new SetmealDto();
        setmealDto.setSetmealDishes(list);
        BeanUtils.copyProperties(setmeal,setmealDto);
        return setmealDto;
    }

    /**
     * 保存套餐的修改
     * @param setmealDto
     */
    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        //1.保存基本套餐信息的修改
        this.updateById(setmealDto);

        //2.删除以前存在关联表的菜品 delete from setmeal_dish where setmeal_id = ?
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setMealDishService.remove(queryWrapper);

        //3.插入现在传入关联表的菜品
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setMealDishService.saveBatch(setmealDishes);
    }
}
