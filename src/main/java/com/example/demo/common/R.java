package com.example.demo.common;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Http返回的对象模型
 * @author muyuer 182443947@qq.com
 * @date 2018-12-17 17:24
 */
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code = 0;

    @Getter
    @Setter
    private int msgCode = 0;

    @Getter
    @Setter
    private String msg = "success";


    @Getter
    @Setter
    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }
    public R(T data, int msgCode, String msg) {
        super();
        this.data = data;
        this.msgCode = msgCode;
        this.msg = msg;
    }

    public R(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = -1;
    }
}

//@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class R<T> implements Serializable {
//
//    /**
//     * 状态码
//     */
//    private Integer code;
//
//    /**
//     * 提示信息
//     */
//    private String msg;
//
//    /**
//     * 具体数据
//     */
//    private T data;
//}
