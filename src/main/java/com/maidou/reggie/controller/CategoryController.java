package com.maidou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maidou.reggie.common.R;
import com.maidou.reggie.entity.Category;
import com.maidou.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/8 9:47
 * 菜品分类管理
 **/
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("Category:{}", category.toString());
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * category分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize) {
        log.info("category 分页查询 page:{} pageSize{}", page, pageSize);
        Page<Category> pageInfo = new Page<>(page, pageSize);

        //查询条件
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 删除菜品分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除菜品分类id:{}", ids);
//        categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("分类菜品删除成功");
    }

    /**
     * 根据ID修改菜品分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改菜品分类id:{}", category.getId());
        categoryService.updateById(category);
        return R.success("修改菜品分类信息成功");
    }

    /**
     * 根据条件查询菜品分类
     *
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        log.info("下拉列表查询菜品分类: {}",category.getType());
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        if(category.getType() != null){
            queryWrapper.eq(Category::getType, category.getType());
        }
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list();
        return R.success(list);
    }
}
