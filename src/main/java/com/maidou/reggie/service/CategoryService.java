package com.maidou.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maidou.reggie.entity.Category;
import com.maidou.reggie.entity.Employee;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:58
 **/
public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
