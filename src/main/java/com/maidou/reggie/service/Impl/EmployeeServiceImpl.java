package com.maidou.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maidou.reggie.entity.Employee;
import com.maidou.reggie.mapper.EmployeeMapper;
import com.maidou.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 19:59
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{
}
