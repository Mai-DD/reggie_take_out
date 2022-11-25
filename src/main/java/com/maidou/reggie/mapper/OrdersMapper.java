package com.maidou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maidou.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/13 18:58
 **/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
