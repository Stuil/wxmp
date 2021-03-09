package com.stuil.wx.mp.utils;

import com.alibaba.fastjson.annotation.JSONField;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wizzer(wizzer@qq.com) on 2016/12/21.
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;
    @JSONField(ordinal = 1)
    private int code;
    @JSONField(ordinal = 2)
    private String msg;
    @JSONField(ordinal = 3)
    private Object data;

    public Result() {

    }

    public Result(Enum src) {
        Mirror<?> mi = Mirror.me(src);
        try {
            this.code = (int) mi.invoke(src, "value");
        } catch (Exception e1) {
            try {
                this.code = (int) mi.invoke(src, "getValue");
            } catch (Exception e2) {
                this.code = src.ordinal();
            }
        }
        try {
            this.msg = Strings.sNull(mi.invoke(src, "text"));
        } catch (Exception e1) {
            try {
                this.msg = Strings.sNull(mi.invoke(src, "getText"));
            } catch (Exception e2) {
                this.msg = src.name();
            }
        }
    }

    public static Result NEW() {
        return new Result();
    }

    public Result addCode(int code) {
        this.code = code;
        return this;
    }

    public Result addMsg(String msg) {
        if (Strings.isBlank(msg) || Mvcs.getActionContext() == null || Mvcs.getActionContext().getRequest() == null || Mvcs.getMessage(Mvcs.getActionContext().getRequest(), msg) == null) {
            this.msg = Strings.sNull(msg);
        } else {
            this.msg = Mvcs.getMessage(Mvcs.getActionContext().getRequest(), msg);
        }
        return this;
    }

    public Result addData(Object data) {
        this.data = data;
        return this;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        if (Strings.isBlank(msg) || Mvcs.getActionContext() == null || Mvcs.getActionContext().getRequest() == null || Mvcs.getMessage(Mvcs.getActionContext().getRequest(), msg) == null) {
            this.msg = Strings.sNull(msg);
        } else {
            this.msg = Mvcs.getMessage(Mvcs.getActionContext().getRequest(), msg);
        }
        this.data = data;
    }

    public static Result success(String content) {
        return new Result(0, content, null);
    }

    public static Result success(String content, Object data) {
        return new Result(0, content, data);
    }

    public static Result success(Object data) {
        return new Result(0, "system.success", data);
    }

    public static Result error(int code, String content) {
        return new Result(code, content, null);
    }

    public static Result error(String content) {
        return new Result(500101, content, null);
    }

    public static Result success() {
        return new Result(0, "system.success", null);
    }

    public static Result error() {
        return new Result(500100, "system.error", null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public String toJsonString() {
        return Json.toJson(this, JsonFormat.compact());
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                atomicInteger.getAndIncrement();
            }).start();
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicInteger.get());
        }
    }
}
