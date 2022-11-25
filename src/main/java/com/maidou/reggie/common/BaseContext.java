package com.maidou.reggie.common;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/7 22:45
 * 基于ThreadLocal获取session里的id的工具类
 **/
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
