package com.bairong.sample.net;

import android.os.Build;
import android.util.Log;

import com.bairong.mobile.utils.CallBack;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by zhangwei on 16/7/19.
 */
public class HttpEngine {
    private static HttpEngine instance = null;
    //private static final String SECOND_SALT_PART = "ikiquu2zifm4yzatarvyijh@qkqussrz";

    private HttpEngine() {

    }

    public static HttpEngine getInstance() {
        if (instance == null) {
            instance = new HttpEngine();
        }
        return instance;
    }



    /*public void requestWithoutCA(String urlPath,String params,CallBack callBack) {
        try {

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new MyTrustManager() },
                    new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new MyHostnameVerifier());

            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();
            // 取得输入流，并使用Reader读取
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            System.out.println("=============================");
            System.out.println("Contents of get request");
            System.out.println("=============================");
            String lines;
            while ((lines = reader.readLine()) != null) {
                System.out.println(lines);
            }
            reader.close();
            // 断开连接
            urlConnection.disconnect();
            System.out.println("=============================");
            System.out.println("Contents of get request ends");
            System.out.println("=============================");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/


    private class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            return true;
        }

    }

    private class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    public String postForm(String urlPath, String data, CallBack callBack) {

        try {

            // 根据地址创建URL对象
            URL url = new URL(urlPath);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(3000);
            // 传递的数据
            //String data = "paramStr=" + URLEncoder.encode(paramStr, "UTF-8");
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));
            // 设置请求的头
            urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());
                Log.e("result", result);

                return result;
            } else {
                if (callBack != null)
                    callBack.message(null);
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            if (callBack != null)
                callBack.message(null);
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public String post(String urlPath, String params, CallBack callBack) {
        //requestWithoutCA(urlPath,params,callBack);
        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        byte[] data = null;
        if (params != null) {
            data = gzip(params);
            /*if (HttpUrl.UNION_ID.equals(urlPath))
                try {
                    data=params.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
        }


        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new MyTrustManager()},
                    new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new MyHostnameVerifier());

            URL url = new URL(urlPath);
            //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //获得URL对象
            //URL url = new URL(urlPath);
            //获得HttpURLCnnection对象
            connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为post
            connection.setRequestMethod("POST");
            //不使用缓存
            connection.setUseCaches(false);

            //connection.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            //设置读取超时时间
            connection.setReadTimeout(10000);
            //设置是否从httpUrlConnection读入，默认情况下是true;
            connection.setDoInput(true);
            connection.setDoOutput(true);

            /*String auth = null;
            if (data != null) {
                auth = Md5Util.md5(data, Configuration.FIRST_SALT_PART.getBytes(),
                        SECOND_SALT_PART.getBytes());
            } else {
                auth = Md5Util.md5(Configuration.FIRST_SALT_PART.getBytes(),
                        SECOND_SALT_PART.getBytes());
            }*/

            //connection.header
            connection.setRequestProperty("Accept", "application/json");

            //设置为true后才能写入参数
            connection.setRequestProperty("Content-Type", "application/json");
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.setRequestProperty("Authorization", auth);
            connection.setRequestProperty("Content-Encoding", "gzip");
            os = connection.getOutputStream();
            if (data != null)
                os.write(data);
            /*Map heads=connection.getHeaderFields();
            CommonUtil.log("heads",heads.toString());*/

            //写入参数
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //相应码是否为200
                is = connection.getInputStream();
                //获得输入流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                //包装字节流为字符流
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            if (callBack != null)
                callBack.message(null);
            e.printStackTrace();
        } finally {
            //关闭
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
                connection = null;
            }
        }
        return null;
    }

    private byte[] gzip(String content) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            bytes = content.getBytes("UTF-8");
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                GZIPOutputStream zos = new GZIPOutputStream(
                        new BufferedOutputStream(os), false);
                // Log.v(BfdAgent.TAG, "write bytes: " + bytes.length);
                zos.write(bytes);
                zos.finish();
                zos.flush();
            } else {
                GZIPOutputStream zos = new GZIPOutputStream(
                        new BufferedOutputStream(os));
                // Log.v(BfdAgent.TAG, "write bytes: " + bytes.length);
                zos.write(bytes);
                zos.finish();
                zos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (os.size() > 0)
            bytes = os.toByteArray();
        return bytes;
    }
}
