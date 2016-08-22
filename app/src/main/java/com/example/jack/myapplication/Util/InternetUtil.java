package com.example.jack.myapplication.Util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 专门处理网络请求的工具类
 */
public class InternetUtil {
    public static String root = "http://155o554j78.iok.la:49817/Flash-buy/";
    public static String args1 = "cart?cartNumber=9&userId=9";
    public static String cartUrl = root + args1;

    public static String searchUrl =  root + "search?name=";
    /**
     *  post json数据给服务器端，如果接收成功并返回正确结果，那么该函数返回true
     * @param json
     * @param args
     * @return
     */
    public static boolean postInfo(String json,String args){
        boolean flag = false;
        //url拼接
        String urlStr = root + args;
        //post数据
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);        //设置连接超时时间
            conn.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            conn.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            conn.setRequestMethod("POST");     //设置以Post方式提交数据
            conn.setUseCaches(false);               //使用Post方式不能使用缓存
            // 设置contentType
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();

            os.write(json.getBytes());
            os.close();
            //服务器返回的响应码
            int code = conn.getResponseCode();
            if (code == 200) {
                // 等于200了,下面呢我们就可以获取服务器的数据了
                InputStream is = conn.getInputStream();
                String result = Util.readStream(is);
                //如果服务器传回了结果，表示服务器接收成功
                if(result != null)
                    flag = true;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return flag;
    }
}