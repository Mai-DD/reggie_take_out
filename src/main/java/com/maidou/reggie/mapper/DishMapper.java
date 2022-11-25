package com.maidou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maidou.reggie.entity.Category;
import com.maidou.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:23
 **/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
