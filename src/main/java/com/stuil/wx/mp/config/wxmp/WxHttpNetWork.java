package com.stuil.wx.mp.config.wxmp;

import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @title: WxHttpNetWork
 * @description: 向微信发送请求  用于发送http请求的工具类(向微信发送http请求，获取access_token)
 * @date: 2021/3/9
 * @author: stuil
 * @copyright: Copyright (c) 2021
 * @version: 1.0
 */

@Component
public class WxHttpNetWork {
    /**
     * 发起HTTPS请求
     * @param reqUrl
     * @param requestMethod 请求方式，传null的话默认是get请求
     * @return 相应字符串
     */
    public String getHttpsResponse(String reqUrl, String requestMethod) {
        URL url;
        InputStream is;
        String result ="";

        try {
            url = new URL(reqUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            TrustManager[] tm = {xtm};
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            //允许输入流，即允许下载
            con.setDoInput(true);

            //在android中必须将此项设置为false，允许输出流，即允许上传
            con.setDoOutput(false);
            //不使用缓冲
            con.setUseCaches(false);
            if (null != requestMethod && !requestMethod.equals("")) {
                //使用指定的方式
                con.setRequestMethod(requestMethod);
            } else {
                //使用get请求
                con.setRequestMethod("GET");
            }
            //获取输入流，此时才真正建立链接
            is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = bufferReader.readLine()) != null) {
                result += inputLine + "\n";
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    X509TrustManager xtm = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }
    };
}
