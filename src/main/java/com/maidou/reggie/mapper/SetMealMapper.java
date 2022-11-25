package com.maidou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maidou.reggie.entity.Category;
import com.maidou.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:23
 **/
@Mapper
public interface SetMealMapper extends BaseMapper<Setmeal> {
}
