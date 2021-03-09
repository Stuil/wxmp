package com.stuil.wx.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @title: WxMpController
 * @description: 公众号
 * @date: 2021/3/9
 * @author: zwh
 * @copyright: Copyright (c) 2021
 * @version: 1.0
 */
@Controller
public class WxMpController {
    //切记：这里是自定义的token，需和你微信配置界面提交的token完全一致
    private final String TOKEN = "TOKEN001";

    final static String ACC_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @GetMapping("/mpInit")
    public void mpInit(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        System.out.println("校验签名start");

        /**
         * 接收微信服务器发送请求时传递过来的参数
         */
        //签名
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        /**
         * 将token、timestamp、nonce三个参数进行字典序排序
         * 并拼接为一个字符串
         */
        String sortStr = this.sort(TOKEN, timestamp, nonce);
        /**
         * 对排序后的sortStr进行shal加密
         */
        String mySignature = shal(sortStr);
        /**
         * 校验"微信服务器传递过来的签名"和"加密后的字符串"是否一致, 如果一致则签名通过，否则不通过
         */
        if (!"".equals(signature) && !"".equals(mySignature) && signature.equals(mySignature)) {
            System.out.println("校验结果：" + "签名校验通过");
            try {
                //必须响应给微信，不然会提示"token校验失败"
                response.getWriter().write(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("校验结果：" + "校验签名失败");
        }
    }

    /**
     * 参数排序
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 字符串进行shal加密
     *
     * @param str
     * @return
     */
    public String shal(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
