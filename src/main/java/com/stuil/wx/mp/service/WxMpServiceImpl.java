package com.stuil.wx.mp.service;

import com.stuil.wx.mp.config.wxmp.WxHttpNetWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title: WxMpServiceImpl
 * @description: 微信公众号
 * @date: 2021/3/10
 * @author: stuil
 * @copyright: Copyright (c) 2021
 * @version: 1.0
 */

@Service
public class WxMpServiceImpl implements WxMpService {
    @Autowired
    WxHttpNetWork wxHttpNetWork;
    @Override
    public String getUserList(String accessToken, String next_openid) {
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s",
                accessToken);
        String response = wxHttpNetWork.getHttpsResponse(Url, null);
        return response;
    }

    @Override
    public String getUnserInfo(String accessToken, String openid) {
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN",
                accessToken, openid);
        String response = wxHttpNetWork.getHttpsResponse(Url,"");
        return response;
    }
}
