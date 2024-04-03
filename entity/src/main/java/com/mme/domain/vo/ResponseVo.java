package com.mme.domain.vo;

import lombok.Data;

/**
 * @author lxin
 * @description 统一响应
 * @Date 2024/3/27 19:09
 */
@Data
public class ResponseVo<T> {

    private Integer code;
    private String msg;
    private T data;

    public ResponseVo() {
    }

    public ResponseVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseVo success(){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(0);
        responseVo.setMsg("success");
        return responseVo;
    }

    public static ResponseVo success(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(0);
        responseVo.setMsg(msg);
        return responseVo;
    }

    public static <T>  ResponseVo<T> success(T data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(0);
        responseVo.setMsg("success");
        responseVo.setData(data);
        return responseVo;
    }

    public static <T>  ResponseVo<T> success(String msg, T data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(0);
        responseVo.setMsg(msg);
        responseVo.setData(data);
        return responseVo;
    }

    public static ResponseVo failed(){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(1);
        responseVo.setMsg("服务出现异常");
        return responseVo;
    }

    public static ResponseVo failed(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(1);
        responseVo.setMsg(msg);
        return responseVo;
    }

    /**
     * 异常响应
     * @param code
     * @param msg
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseVo<T> backException(int code, String msg, T data) {
        return new ResponseVo<>(code, msg, data);
    }

}
