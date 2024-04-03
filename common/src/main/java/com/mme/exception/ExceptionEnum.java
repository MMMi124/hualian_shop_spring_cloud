package com.mme.exception;

/**
 * @author lxin
 * @description
 * @Date 2024/3/31 23:58
 */
public enum ExceptionEnum {

    SYS_EXCEPTION(101,"系统异常"),

    SERVICE_LOST(102,"服务已丢失"),

    /**
     * 购物车是空的
     */
    CART_IS_NULL(201,"购物车是空的"),

    ;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
