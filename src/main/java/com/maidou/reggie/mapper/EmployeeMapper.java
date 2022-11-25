package com.maidou.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maidou.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:23
 **/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
