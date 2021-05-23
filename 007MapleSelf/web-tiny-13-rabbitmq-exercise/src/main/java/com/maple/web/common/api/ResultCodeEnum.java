package com.maple.web.common.api;

import lombok.Getter;

@Getter
public enum ResultCodeEnum implements ErrorCodeInterface{
    SUCCESS(200, "操作成功"),
    FAIL(500,"操作失败"),
    UNAUTHENTICATED(401, "未登录或token过期"),
    FORBIDDEN(403, "没有相关权限"),
    VALIDATE_FAIL(404, "参数校验失败");
    private long code;
    private String message;
    ResultCodeEnum(long code, String message){
        this.code = code;
        this.message = message;
    }
}
