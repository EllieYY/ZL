package com.sk.zl.model.result;


public enum RespCode {
    SUCCESS(0, "请求成功"),
    FAIL(-1, "请求失败"),
    PARAM_ERR(-1, "参数异常"),
    INNER_ERR(-1, "服务器内部错误");

    private int code;
    private String msg;

    RespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
