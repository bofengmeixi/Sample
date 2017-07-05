package com.bairong.sample.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bairong.sample.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhangwei on 17/4/8.
 */

public class TestActivity extends AppCompatActivity {
    private static String url = "http://192.168.22.209:8081/HainaApi/data/getData.action";

    private Snackbar snackbar;
    @OnClick(R.id.btn1)
    public void btn() {
        sharedPrePrivate();
    }

    @OnClick(R.id.btn12)
    public void btn12() {
        sharedPrePrivate2();
    }

    @OnClick(R.id.btn2)
    public void btn2() {
        sharePreAppend();
    }

    @OnClick(R.id.btn22)
    public void btn22() {
        sharePreAppend2();
    }

    @OnClick(R.id.btn3)
    public void btn3() {
        textView.setText(showSharedPre());
    }

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @OnClick(R.id.btn4)
    public void showSnackBar(){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "snackBar", Snackbar.LENGTH_INDEFINITE);
        View view=snackbar.getView();
        view.setBackgroundColor(Color.parseColor("#ff0000"));
        ((TextView)view.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#00ff00"));
        ((Button)view.findViewById(R.id.snackbar_action)).setTextColor(Color.parseColor("#0000ff"));
        /*snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                Toast.makeText(TestActivity.this,"onDismissed",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
                Toast.makeText(TestActivity.this,"onShown",Toast.LENGTH_LONG).show();
            }
        });*/
        snackbar.setAction("message", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TestActivity.this,"haha",Toast.LENGTH_SHORT).show();
            }
        });
        snackbar.show();
    }

    @BindView(R.id.text)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        snackBar();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //loginByPost("");
                //sendLiveness();
            }
        }).start();
    }

    private void snackBar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "snackBar", Snackbar.LENGTH_LONG);
        //snackbar.show();
    }

    private void sharedPrePrivate2() {
        SharedPreferences sharedPreferences = getSharedPreferences("haha", MODE_PRIVATE);
        sharedPreferences.edit().putString("xixi", "xixi").commit();
    }

    private void sharedPrePrivate() {
        SharedPreferences sharedPreferences = getSharedPreferences("haha", MODE_PRIVATE);
        sharedPreferences.edit().putString("hehe", "heihie").commit();
    }

    private void sharePreAppend() {
        SharedPreferences sharedPreferences = getSharedPreferences("haha", MODE_APPEND);
        sharedPreferences.edit().putString("hehe", "aeiehie").commit();
    }

    private void sharePreAppend2() {
        SharedPreferences sharedPreferences = getSharedPreferences("haha", MODE_APPEND);
        sharedPreferences.edit().putString("huhu", "huhu").commit();
    }

    private String showSharedPre() {
        SharedPreferences sharedPreferences = getSharedPreferences("haha", MODE_PRIVATE);
        StringBuilder stringBuilder = new StringBuilder();
        Map map = sharedPreferences.getAll();
        if (map == null || map.isEmpty())
            return "";
        Set<Map.Entry> set = map.entrySet();
        for (Map.Entry entry : set) {
            stringBuilder.append(map.get(entry.getKey()) + ";");
        }
        return stringBuilder.toString();
        //return sharedPreferences.getString("hehe","nil");
    }


    private void sendLiveness() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        //String daily_photo3 = Base64.encodeToString(livenessDetectionFrames.verificationPackageWithFanpaiFull,Base64.NO_WRAP);
        // Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.xiaochen);
        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //  byte[] byteArray = stream.toByteArray();
        //String daily_photo = Base64.encodeToString(byteArray,Base64.NO_WRAP);

        JSONObject merchantBean = new JSONObject();
        try {
            merchantBean.put("meal", "Authentication");
            merchantBean.put("id", "130302198211141423");
            merchantBean.put("name", "张晓晨");
            merchantBean.put("daily_photo3", "3");
            merchantBean.put("daily_photo", "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //RequestBody requestBody = RequestBody.create(JSON,merchantBean.toString());
        //apiCode=555666

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("apiCode", "555666").add("jsonData", merchantBean.toString());
        //builder.add("jsonData",merchantBean.toString());
        RequestBody requestBody = builder.build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Log.e("response", "is null");
        } else {
            /*final Response finalResponse = response;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog=new Dialog(SampleLivenessActivity.this);
                    dialog.setTitle(finalResponse.toString());
                    dialog.show();
                }
            });*/

            Log.e("response", response.toString());

        }
        String body = null;
        try {
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (body != null)
            Log.e("body", body);
    }

    /**
     * POST请求操作
     */
    public void loginByPost(String paramStr) {

        try {

            // 请求的地址
            String spec = "http://das-test.bairong.cn/getGid.do";
            spec = "http://192.168.22.209:8081/HainaApi/data/getData.action";
            // 根据地址创建URL对象
            URL url = new URL(spec);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            // 传递的数据
            String data = "paramStr=" + URLEncoder.encode(paramStr, "UTF-8");

            JSONObject merchantBean = new JSONObject();
            try {
                merchantBean.put("meal", "Authentication");
                merchantBean.put("id", "130302198211141423");
                merchantBean.put("name", "张晓晨");
                merchantBean.put("daily_photo3", "3");
                merchantBean.put("daily_photo", "1");
                //Log.e("daily_photo3",daily_photo3);
                //Log.e("daily_photo",daily_photo);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            data = "apiCode=555666&jsonData=" + URLEncoder.encode(merchantBean.toString(), "utf-8");
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
            Log.e("code", urlConnection.getResponseCode() + "");
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
                // 通过runOnUiThread方法进行修改主线程的控件内容
                TestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里把返回的数据写在控件上 会出现什么情况尼
                        //tv_result.setText(result);
                    }
                });

            } else {
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
