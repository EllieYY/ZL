package com.sk.zl.aop.excption;

/**
 * @Description : 数据库操作异常
 * @Author : Ellie
 * @Date : 2019/3/4
 */
public class DataDoException extends RuntimeException {
    public DataDoException(String msg) {
        super(msg);
    }
}
