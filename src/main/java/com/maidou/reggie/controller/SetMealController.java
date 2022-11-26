package com.maidou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maidou.reggie.common.BusinessException;
import com.maidou.reggie.common.R;
import com.maidou.reggie.dto.SetmealDto;
import com.maidou.reggie.entity.Category;
import com.maidou.reggie.entity.Setmeal;
import com.maidou.reggie.entity.SetmealDish;
import com.maidou.reggie.service.CategoryService;
import com.maidou.reggie.service.SetMealDishService;
import com.maidou.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/8 10:55
 **/
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @Autowired
    private SetMealDishService setMealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 保存新增套餐
     *
     * @param setmealDto
     * @return
     */
    @CacheEvict(value = "setMealCache",allEntries = true)
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("保存新增套餐: {}", setmealDto.toString());
        setMealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setMealService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item, setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    @CacheEvict(value = "setMealCache",allEntries = true)
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids: {}", ids);
        setMealService.removeWithDish(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 批量修改状态
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        log.info("修改套餐状态为: {} ids: {}", status, ids);
        //判断状态是否一致sql: select count(*) from setmeal where in ? and status = ?
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, status);
        int count = setMealService.count(queryWrapper);
        if (count != 0) {
            throw new BusinessException("套餐状态不一致~无法批量修改!");
        }

        //修改状态 sql:update setmeal set status = 0 where id = ?
        ArrayList<Setmeal> setMeals = new ArrayList<>();
        for (Long id : ids) {
            Setmeal s = new Setmeal();
            s.setStatus(status);
            s.setId(id);
            setMeals.add(s);
        }
        setMealService.updateBatchById(setMeals);
        return R.success("修改状态成功");
    }

    /**
     * 根据id回显关系表数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> selectById(@PathVariable Long id) {
        //select * from setmeal_dish where id = ?
        log.info("根据setMealId: {}回显菜品", id);
        SetmealDto setmealDto = setMealService.selectWithDish(id);
        return R.success(setmealDto);
    }

    /**
     * 保存套餐的修改
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info("保存套餐修改: {}", setmealDto.toString());
        setMealService.updateWithDish(setmealDto);
        return R.success("套餐修改成功");
    }

    /**
     * 根据id查询套餐
     *
     * @param setmeal
     * @return
     */
    @Cacheable(value = "setMealCache",key = "#setmeal.categoryId + '_' + #setmeal.status")
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setMealService.list(queryWrapper);
        return R.success(list);
    }
}
