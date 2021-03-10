package com.stuil.wx.mp.config.wxmp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @title: InitConfig
 * @description: 默认启动项目的时候就启动该类，用来向微信后台定期获取access_token值
 * 继承ApplicationRunner接口的话，项目启动时就会执行里边的run方法
 * @date: 2021/3/9
 * @author: stuil
 * @copyright: Copyright (c) 2021
 * @version: 1.0
 */

@Component
@Order(value = 1)
public class InitConfig implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("开始获取微信里的access_token");
        final String appId = "wx679e6f7d304eb564";
        final String appSecret = "4068f92fe5006a1a59cfba6831e0d3e7";
     //   final String appId = "wx794006a4e0786358";
     //   final String appSecret = "1b0af029ef071311b41c57c11e44ae72";
        //获取accessToken
        AccessTokenInfo.accessToken = getAccessToken(appId, appSecret);
    }

    private AccessToken getAccessToken(String appId, String appSecret) {
        WxHttpNetWork netHelper = new WxHttpNetWork();
        /**
         * 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET，
         * 其中grant_type固定写为client_credential即可。
         */
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                appId, appSecret);
        //此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
        String result = netHelper.getHttpsResponse(Url, "");
        System.out.println("获取到的access_token="+result);

        //使用FastJson将Json字符串解析成Json对象
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setTokenName(json.getString("access_token"));
        token.setExpireSecond(json.getInteger("expires_in"));
        return token;
    }
}
