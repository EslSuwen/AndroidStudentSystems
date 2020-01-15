package com.cqju.studentsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 与服务端进行数据交互
 */
public class HttpUtils {


    public static String BASE_URL = "http://192.168.43.254:8080/TestJee_war_exploded/";

    /*
     * urlStr:网址
     * parms：提交数据
     * return:网页源码
     * */
    public static String getContextByHttp(String urlStr, Map<String, String> parms) {
        StringBuilder result = new StringBuilder();//StringBuilder用于单线程多字符串拼接
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getStringFromOutput(parms));

            writer.flush();
            writer.close();
            outputStream.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String temp;
                while ((temp = reader.readLine()) != null) {
                    result.append(temp);
                    reader.close();
                }
            } else {
                return "connection error:" + connection.getResponseCode();
            }
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();

    }

    /**
     * 将map转换成key1=value1&key2=value2的字符串形式
     *
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getStringFromOutput(Map<String, String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();//StringBuilder用于单线程多字符串拼接
        boolean isFirst = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (isFirst)
                isFirst = false;
            else
                sb.append("&");
            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return sb.toString();
    }

}
