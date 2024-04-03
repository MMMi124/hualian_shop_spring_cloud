package com.mme.exception;

import java.io.Serializable;

/**
 * @author lxin
 * @description
 * @Date 2024/3/31 23:59
 */
public class SysException extends Exception implements Serializable {

    private static final long serialVersionUID = -7410700977252991012L;

    private String msg;
    private int code;


    public SysException() {
    }

    public SysException(ExceptionEnum exceptionEnum) {
        super();
        this.msg = exceptionEnum.getMsg();
        this.code = exceptionEnum.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}