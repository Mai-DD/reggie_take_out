package com.maidou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maidou.reggie.common.R;
import com.maidou.reggie.entity.Employee;
import com.maidou.reggie.service.EmployeeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 20:19
 **/
@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "员工相关接口")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request  前端请求
     * @param employee 前端请求携带的employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1.对传过来的密码MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());


        //2.对用户名进行校验
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);


        //3.没有查询到返回登陆失败
        if (emp == null) {
            return R.error("登陆失败");
        }


        //4.密码进行校验
        if (!password.equals(emp.getPassword())) {
            return R.error("登陆失败");
        }


        //5.对账号状态进行校验
        if (emp.getStatus() == 0) {
            return R.error("账号被禁用");
        }


        //6.登录成功,把员工id放入session返回前端
        request.getSession().setAttribute("emp", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出方法
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清除session
        request.getSession().removeAttribute("emp");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping()
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工:{}", employee.toString());
        //设置初始密码,加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));


        //设置创建时间,公共字段设置MP自动填充
        /*employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());*/


        //设置创建人
        /*Long empID = (Long) request.getSession().getAttribute("emp");
        employee.setCreateUser(empID);
        employee.setUpdateUser(empID);*/


        employeeService.save(employee);
        return R.success("添加员工成功");
    }

    /**
     *  分页查询
     */
    @GetMapping("/page")
    public R<Page> page(HttpServletRequest request, Integer page, Integer pageSize, String name){
        log.info("员工{} page: {} pageSize:{}",(Long)request.getSession().getAttribute("emp"),page,pageSize);

        //创建分页构造器
        Page pageInfo = new Page(page,pageSize);
        //如果查询条件name不为空,则添加过滤条件 like
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        if(name != null){
            queryWrapper.like(Employee::getName,name);
        }
        //排序条件orderBy
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询(查询之后page里面就封装了此次查询的所有结果包括总记录数等等...前端可以自取)
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 修改员工信息(包括状态和表单信息)
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info("修改用户{}",employee.toString());
        if(employee.getId() == 1L){
            log.info("已禁止修改管理员信息");
            return R.error("禁止修改管理员信息!");
        }

        //修改 修改记录
        Long updateUserId = (Long) request.getSession().getAttribute("emp");
        /*employee.setUpdateUser(updateUserId);
        employee.setUpdateTime(LocalDateTime.now());*/

        //修改信息
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    /**
     * 修改员工信息时,数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("用户id:{}数据已回显",id);
        Employee employee = employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("未查询到员工信息");
    }
}
