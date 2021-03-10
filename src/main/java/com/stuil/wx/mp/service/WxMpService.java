package com.stuil.wx.mp.service;

/**
 * @description: 微信公众号接口
 * @date: 2021/3/10
 * @author: stuil
 * @version: 1.0
 */

public interface WxMpService {
    /**
     * @description: 获取关注者列表
     * @date: 2021/3/10
     * @author: stuil
     */
    String getUserList(String accessToken,String next_openid);
    
    /**
     * @description: 根据openid获取关注者信息
     * @date: 2021/3/10
     * @author: stuil
     */
    String getUnserInfo(String accessToken,String openid);
}
