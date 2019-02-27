package com.sk.zl.utils;

import com.sk.zl.model.result.RespCode;
import com.sk.zl.model.result.RespEntity;

/**
 * @Description : 封装成result bean工具
 * @Author : Ellie
 * @Date : 2019/2/25
 */
public class RespUtil {
    private final static String SUCCESS = "success";

    public static <T> RespEntity<T> makeOkResp() {
        return new RespEntity<T>(RespCode.SUCCESS);
    }

    public static <T> RespEntity<T> makeOkResp(T data) {
        return new RespEntity<T>(RespCode.SUCCESS, data);
    }

    public static <T> RespEntity<T> makeInnerErrResp() {
        return new RespEntity<T>(RespCode.INNER_ERR);
    }

    public static <T> RespEntity<T> makeParamErrResp() {
        return new RespEntity<T>(RespCode.PARAM_ERR);
    }

    public static <T> RespEntity<T> makeCustomErrResp(String msg) {
        RespCode.FAIL.setMsg(msg);
        return new RespEntity<T>(RespCode.FAIL);
    }

    public static <T> RespEntity<T> makeResp(int code, String msg) {
        RespEntity<T> respEntity = new RespEntity<T>();
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    public static <T> RespEntity<T> makeResp(int code, String msg, T data) {
        RespEntity<T> respEntity = new RespEntity<T>();
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(data);
        return respEntity;
    }
}
