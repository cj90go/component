package com.zhiliag.componmet.bean;

/**
 * @author:lizhi
 * @Date: 2020/9/2
 * @des:
 **/
public class BaseResponse<T> {

    public int code;

    public String message;

    public T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse(200, "成功", data);
    }

    public static BaseResponse fail(int code, String message) {

        return new BaseResponse(code, message);
    }

}
