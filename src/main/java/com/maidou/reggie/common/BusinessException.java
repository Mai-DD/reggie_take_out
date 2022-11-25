package com.maidou.reggie.common;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/8 13:00
 * 自定义业务异常
 **/
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
