package com.maidou.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/7 12:33
 * 全局异常处理
 * ControllerAdvice控制器通知(AOP)
 * @RequestBody和@ControllerAdvice
 **/
@Slf4j
@RestControllerAdvice(annotations = {Controller.class, RestController.class})
public class GlobalExceptionHandler {

    /**
     * 处理用户输入数据库表唯一字段的异常
     * @param exception
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.info(exception.getMessage());
        if(exception.getMessage().contains("Duplicate entry")){
            String[] s = exception.getMessage().split(" ");
            String msg = s[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知的错误");
    }

    /**
     * 处理删除菜品分类时已经关联菜品或套餐的异常
     * @param exception
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public R<String> exceptionHandler(BusinessException exception){
        log.info(exception.getMessage());
        return R.error(exception.getMessage());
    }
}
