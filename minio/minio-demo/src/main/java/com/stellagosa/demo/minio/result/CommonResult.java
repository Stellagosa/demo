package com.stellagosa.demo.minio.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    public CommonResult() {
    }

    public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}