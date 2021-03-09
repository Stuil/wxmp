package com.stuil.wx.mp.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @description: 压缩包 工具类
 */
public class ZipUtils {

    public static void multiFileZipDownload(HttpServletResponse response, String downloadName, Map<String,String> map) {
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ZipOutputStream zos=new ZipOutputStream(outputStream);
        try {
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(downloadName, "UTF-8"));
            map.forEach((name,path)->{
                InputStream is = null;
                BufferedInputStream in = null;
                byte[] buffer = new byte[1024];
                int len;
                //创建zip实体（一个文件对应一个ZipEntry）
                //name --->压缩包的层级路径
                ZipEntry entry = new ZipEntry(name);
                try {
                    //获取需要下载的文件流
                    URL file=new URL(path);
                    is =file.openStream();
                    in = new BufferedInputStream(is);
                    zos.putNextEntry(entry);
                    //文件流循环写入ZipOutputStream
                    while ((len = in.read(buffer)) != -1 ) {
                        zos.write(buffer, 0, len);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        zos.closeEntry();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(in != null) {
                        try {
                            in.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(is != null) {
                        try {
                            is.close();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                zos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
       /* Sys_role sys_unit=new Sys_role();
        List<Sys_role> list=new ArrayList<>();
        list.add(sys_unit);
        System.out.println(JSONObject.toJSONString(Result.success().addData(list), SerializerFeature.WriteNullStringAsEmpty));
        System.out.println(Json.toJson(Result.success().addData(list), new JsonFormat().setNullStringAsEmpty(true)));
*/
    }
}
