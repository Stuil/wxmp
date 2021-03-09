package com.stuil.wx.mp.utils;

/**
 * @title: LayuiResp
 * @description:
 * @date: 2020/12/21
 * @author: stuil
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
public class LayuiResp<T> {
    private int code;
    private long count;
    private String msg;
    private T data;
    /**
     *  结果非空构造方法(有数据构造方法)
     * @param code
     * @param count
     * @param data
     */
    public LayuiResp(int code, long count, T data) {
        super();
        this.code = code;
        this.count = count;
        this.data = data;
    }



    /**
     * 返回空值构造方法(无数据构造方法)
     * @param code
     * @param count
     * @param msg
     */
    public LayuiResp(int code, long count, String msg) {
        super();
        this.code = code;
        this.count = count;
        this.msg = msg;
    }


    /**
     * getter、setter方法
     * @return
     */
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public long getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }


    /**
     * 返回有数据信息
     * @param count
     * @param data
     * @return
     */
    public static <T> LayuiResp<T> createBySuccess(long count, T data){
        return new LayuiResp<T>(ResponseLayCode.SUCCESS.getCode(),count,data);
    }

    /**
     * 返回空数据信息
     * @param count
     * @param msg
     * @return
     */
    public static <T> LayuiResp<T> createByNull(long count, String msg){
        return new LayuiResp<T>(ResponseLayCode.NULL.getCode(),count,msg);
    }
    /**
     * 返回空数据信息(count为0)
     * @param msg
     * @return
     */
    public static <T> LayuiResp<T> createByNull(String msg){
        return new LayuiResp<T>(ResponseLayCode.NULL.getCode(),ResponseLayCode.SUCCESS.getCode(),msg);
    }
}
