package com.stuil.wx.mp.utils;

import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

/**
 * 获取地址的坐标
 */
public class LbsMapUtil {
    //请求地址
    public static String URL = "https://apis.map.qq.com/ws/geocoder/v1/?address=%s&key=%s";
    /*KEY*/
    public static String KEY = "AVTBZ-HJUWG-XTWQ3-IOZDL-LLVQS-V2BCJ";


    /**
     * 获取腾讯地图中地址的坐标
     *
     * @param address
     * @return
     */
    public static NutMap getLngAndLat(String address) {
        NutMap map = NutMap.NEW();
        try {
            NutMap params = NutMap.NEW();
            params.put("key", KEY);
            params.put("address", address);
            String url = String.format(URL, address, KEY);
            Response response = Http.get(url);
            if (response.isOK()) {
                String res = response.getContent();
                NutMap resNutMap = Json.fromJson(NutMap.class, res);
                //成功
                if (resNutMap.getInt("status") == 0) {
                    NutMap addreess = resNutMap.getAs("result", NutMap.class);
                    if (Lang.isNotEmpty(addreess)) {
                        map.put("code", 0);
                        map.put("lng", addreess.getAs("location", NutMap.class).getString("lng"));
                        map.put("lat", addreess.getAs("location", NutMap.class).getString("lat"));
                    }
                } else {
                    map.put("code", -1);
                }
            } else {
                map.put("code", -1);
            }
        } catch (Exception e) {
            map.put("code", -1);
        }
        return map;
    }
}
