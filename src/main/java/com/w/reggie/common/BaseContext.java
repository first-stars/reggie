package com.w.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登陆用户id
 * @author xin
 * @date 2022-07-12-10:52
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
