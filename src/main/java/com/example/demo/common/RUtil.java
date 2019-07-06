package com.example.demo.common;


import com.example.demo.enums.REnum;

/**
 * 对象模型工具类
 * @author muyuer 182443947@qq.com
 * @date 2018-12-18 17:58
 */
public class RUtil {

    public static R success(Object object){
        R r = new R();
        r.setCode(CommonConstants.SUCCESS);
        r.setMsg("ok");
        r.setData(object);
        return r;
    }
    public static R success(REnum rMsg){
        R r = new R();
        r.setCode(CommonConstants.SUCCESS);
        r.setMsgCode(rMsg.getCode());
        r.setMsg(rMsg.getMessage());
        return r;
    }

    public static R error(REnum rMsg){
        R r = new R();
        r.setCode(CommonConstants.FAIL);
        r.setMsgCode(rMsg.getCode());
        r.setMsg(rMsg.getMessage());
        return r;
    }

    public static R error(Integer code, String msg){
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
    public static R error(String msg){
        R r = new R();
        r.setCode(CommonConstants.FAIL);
        r.setMsg(msg);
        return r;
    }
}
