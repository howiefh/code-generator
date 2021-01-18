package io.github.howiefh.generator.spring.controller;

/**
 * @author fenghao on 2021/1/16
 * @version 1.0
 * @since 1.0
 */
public class Response<T> {
    private int code;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Response<T> newSuccessResponse(T data) {
        Response<T> response = new Response<T>();
        response.setCode(200);
        response.setData(data);
        return response;
    }
}
