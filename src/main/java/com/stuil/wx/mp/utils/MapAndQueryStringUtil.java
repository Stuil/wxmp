package com.stuil.wx.mp.utils;

import org.apache.commons.lang3.StringUtils;
import org.nutz.lang.Strings;

import java.util.*;

/**
 * @title: MapAndQueryStringUtil
 * @description: map和queryString字符串互转
 * @date: 2021/3/3
 * @author: zwh
 * @copyright: Copyright (c) 2021
 * @version: 1.0
 */


public class MapAndQueryStringUtil {
    /**
     　　* 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     　　* @param params 需要排序并参与字符拼接的参数组
     　　* @return 拼接后字符串
     　　*/
    public static String mapToQueryString(Map<String, Object> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = (String) params.get(key);
            if(Strings.isBlank(value)){
               value="";
            }
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        if(prestr.substring(prestr.length()-1).equals("&")){
            return prestr.substring(0,prestr.length()-1);
        }
        return prestr;
    }


    /**
     * 将url参数转换成map
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, Object> queryStringToMap(String param) {
        Map<String, Object> map = new HashMap<String, Object>(0);
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String key=params[i].substring(0, params[i].indexOf("="));
            String value=params[i].substring(key.length()+1);
            if(Strings.isNotBlank(value)){
                map.put(key, value);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("appId", "appId");
        params.put("timeStamp", "");
        params.put("nonceStr", "");
        params.put("package", "prepay_id=" + "prepay_id");
        params.put("signType", "RSA");
        params.put("paySign", "sign");
        System.out.println(mapToQueryString(params));
        System.out.println(queryStringToMap(mapToQueryString(params)).get("nonceStr"));
        System.out.println(params.get("nonceStr"));
    }
}
