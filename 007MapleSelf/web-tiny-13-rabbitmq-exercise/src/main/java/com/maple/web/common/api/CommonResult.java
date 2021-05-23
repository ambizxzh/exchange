package com.maple.web.common.api;

import lombok.Getter;
import lombok.Setter;

/**
 *通用的返回对象
 *@author zxzh
 *@date 2021/5/16
 */
@Getter
@Setter
public class CommonResult<T> {
    long code;
    String message;
    T data;
    CommonResult(){}
    CommonResult(long code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T>CommonResult<T> success(T data){
        return new CommonResult<T>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(),data);
    }
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T>CommonResult<T> success(String message, T data){
        return new CommonResult<T>(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }
    /**
     * 失败返回结果
     * @param errorCodeInterface 错误码
     */
    public static <T>CommonResult<T> fail(ErrorCodeInterface errorCodeInterface){
        return new CommonResult<T>(errorCodeInterface.getCode(), errorCodeInterface.getMessage(), null);
    }
    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T>CommonResult<T> fail(String message){
        return new CommonResult<T>(ResultCodeEnum.FAIL.getCode(), message, null);
    }
    /**
     * 失败返回结果
     */
    public static <T>CommonResult<T> fail(){
        return fail(ResultCodeEnum.FAIL);
    }
    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T>CommonResult<T> validateFail(String message){
        return new CommonResult<T>(ResultCodeEnum.VALIDATE_FAIL.getCode(), message, null);
    }
    /**
     * 参数验证失败返回结果
     */
    public static <T>CommonResult<T> validateFail(){
        return fail(ResultCodeEnum.VALIDATE_FAIL);
    }
    /**
     * 未登录返回结果
     */
    public static <T>CommonResult<T> unauthenticatedFail(T data){
        return new CommonResult<T>(ResultCodeEnum.UNAUTHENTICATED.getCode(), ResultCodeEnum.UNAUTHENTICATED.getMessage(), data);
    }
    /**
     * 未授权返回结果
     */
    public static <T>CommonResult<T> forbiddenFail(T data){
        return new CommonResult<>(ResultCodeEnum.FORBIDDEN.getCode(), ResultCodeEnum.FORBIDDEN.getMessage(), data);
    }
}
