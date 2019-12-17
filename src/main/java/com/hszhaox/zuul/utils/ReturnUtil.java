package com.hszhaox.zuul.utils;

import com.hszhaox.zuul.common.R;

import java.io.Serializable;

public class ReturnUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static R create(Integer code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R create(Integer code, String msg, Object data) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static R error(Object data) {
        return ReturnUtil.create(-1, "操作异常！", data);
    }

    public static R error() {
        return ReturnUtil.create(-1, "操作异常！");
    }

    public static R repeat(Object data) {
        return ReturnUtil.create(-2, "实体重复！", data);
    }

    public static R repeat() {
        return ReturnUtil.create(-2, "实体重复！");
    }

    public static R empty(Object data) {
        return ReturnUtil.create(0, "没有数据！", data);
    }

    public static R empty() {
        return ReturnUtil.create(0, "没有数据！");
    }

    public static R success(Object data) {
        return ReturnUtil.create(1, "操作成功！", data);
    }

    public static R success() {
        return ReturnUtil.create(1, "操作成功！");
    }
}
