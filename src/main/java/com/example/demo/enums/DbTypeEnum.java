package com.example.demo.enums;

/**
 * 数据源枚举
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2018-12-17 20:07
 */
public enum DbTypeEnum {

    MONGO(0,"Mongo库"),
    PRIMARY(1,"主库"),
    SLAVE(1,"从库");

    private Integer code;

    private String message;
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    DbTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

