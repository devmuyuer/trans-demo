package com.example.demo.enums;

/**
 * Http返回信息枚举
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2018-12-17 20:07
 */
public enum REnum {

    SUCCESS(0,"成功"),
    ERROR(1,"失败");

    private Integer code;

    private String message;
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    REnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

