package com.stuil.wx.mp.config.wxmp;

import org.springframework.stereotype.Component;

/**
 * @title: AccessToken
 * @description: 保存微信返回的access_token值
 * @date: 2021/3/9
 * @author: stuil
 * @copyright: Copyright (c) 2021
 * @version: 1.0
 */

@Component
public class AccessToken {
    /**
     * 获取到的凭证
     */
    private String tokenName;
    /**
     * 凭证有效时间  单位:秒
     */
    private int expireSecond;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public int getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(int expireSecond) {
        this.expireSecond = expireSecond;
    }
}
