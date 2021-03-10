package com.stuil.wx.mp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stuil.wx.mp.config.wxmp.AccessTokenInfo;
import com.stuil.wx.mp.service.WxMpService;
import com.stuil.wx.mp.utils.MapToXmlParamUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.nutz.lang.Encoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.reader.StreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @title: WxMpController
 * @description: 公众号
 * @date: 2021/3/9
 * @author: stuil
 * @copyright: Copyright (c) 2021
 * @version: 1.0
 */
@Controller
public class WxMpController {

    @Autowired
    WxMpService wxMpService;

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

    /**
     * @description: 获取用户信息
     * @date: 2021/3/10
     * @author: stuil
     */
    @RequestMapping("/users")
    @ResponseBody
    public String users(){
        String userList = wxMpService.getUserList(AccessTokenInfo.accessToken.getTokenName(), "");
        Map<String,Object> mapObj = JSONObject.parseObject(userList,Map.class);
        String data = JSON.toJSONString(mapObj.get("data"));
        Map<String,Object> openidList = JSONObject.parseObject(data ,Map.class);
        List<String> list = JSONObject.parseArray(((JSONArray) openidList.get("openid")).toJSONString(), String.class);
        list.forEach(item->{
            String unserInfo = wxMpService.getUnserInfo(AccessTokenInfo.accessToken.getTokenName(), (String)item);
            Map<String,Object> mapObj1 = JSONObject.parseObject(unserInfo,Map.class);
        });
        return "success";
    }

    /**
     * @description: 推送消息解析
     * @date: 2021/3/10
     * @author: stuil
     */
    @PostMapping("/mpInit")
    public String getMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        BufferedReader br = request.getReader();
        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        Map<String, Object> map = MapToXmlParamUtil.multilayerXmlToMap(wholeStr);
        System.out.println(wholeStr);
        return "";
    }

    /**
     * @param request
     * @param msgXml
     * @param remoteIp
     * @return result
     */
    public String processMsg(HttpServletRequest request, String msgXml, String remoteIp) {
        return "";
    }
}
