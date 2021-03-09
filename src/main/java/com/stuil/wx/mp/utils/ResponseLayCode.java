package com.stuil.wx.mp.utils;

/**
 * @description:
 * @author: stuil
 * @version: 1.0
 */

public enum ResponseLayCode {
    SUCCESS(0),
    NULL(1);

    private int code;

    private ResponseLayCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
