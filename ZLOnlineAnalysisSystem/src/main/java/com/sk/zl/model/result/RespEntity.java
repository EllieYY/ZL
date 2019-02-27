package com.sk.zl.model.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description : 返回对象实体
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
@NoArgsConstructor
public class RespEntity<T> {
    @JsonProperty("iret")
    private int code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("data")
    private T data;

    public RespEntity(RespCode respCode) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public RespEntity(RespCode respCode, T data) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.data = data;
    }

    public  RespEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
