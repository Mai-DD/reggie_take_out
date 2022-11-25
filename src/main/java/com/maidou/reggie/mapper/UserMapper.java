package com.maidou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maidou.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/12 17:03
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
