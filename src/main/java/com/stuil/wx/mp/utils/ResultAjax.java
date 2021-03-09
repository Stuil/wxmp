package com.stuil.wx.mp.utils;

import lombok.Data;

/**
 * @title: ResultAjax
 * @description: 公共返回
 * @author: stuil
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
@Data
public class ResultAjax {
    int code;
    String msg;
    Object object;

   public static ResultAjax success(Object object){
        ResultAjax resultAjax=new ResultAjax();
        resultAjax.setCode(0);
        resultAjax.setMsg("操作成功");
        resultAjax.setObject(object);
        return resultAjax;
    }
   public static ResultAjax success(){
        ResultAjax resultAjax=new ResultAjax();
        resultAjax.setCode(0);
        resultAjax.setMsg("操作成功");
        return resultAjax;
    }

    public static   ResultAjax fail(int code,String msg){
        ResultAjax resultAjax=new ResultAjax();
        resultAjax.setCode(code);
        resultAjax.setMsg(msg);
        return resultAjax;
    }
}
