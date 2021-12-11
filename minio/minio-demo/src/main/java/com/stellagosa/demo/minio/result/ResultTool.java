package com.stellagosa.demo.minio.result;

public class ResultTool {

    public static CommonResult success() {
        return new CommonResult(200, "操作成功！");
    }
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(200, "操作成功！", data);
    }

    public static CommonResult fail() {
        return new CommonResult(300, "操作失败！");
    }
}
