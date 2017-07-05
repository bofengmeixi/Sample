package com.bairong.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.bairong.mobile.BrAgent;
import com.bairong.mobile.bean.LoginInfo;
import com.bairong.mobile.utils.CallBack;
import com.bairong.sample.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zhangwei on 17/3/15.
 */

public class JsCallJavaActivity extends AppCompatActivity {
    private WebView contentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java);
        BrAgent.brInit(JsCallJavaActivity.this, "444333");
        contentWebView = (WebView) findViewById(R.id.webview);
        // 启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        //contentWebView.loadUrl("file:///android_asset/web.html");
        contentWebView.loadUrl("file:///android_asset/index2.html");
        contentWebView.addJavascriptInterface(JsCallJavaActivity.this, "android");


        //Button按钮 无参调用HTML js方法
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 无参数调用 JS的方法
                contentWebView.loadUrl("javascript:javacalljs()");

            }
        });
        //Button按钮 有参调用HTML js方法
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传递参数调用JS的方法
                contentWebView.loadUrl("javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'" + ")");
            }
        });


    }

    //由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public void startFunction() {

        BrAgent.onFraud(JsCallJavaActivity.this, new LoginInfo(), new CallBack() {
            @Override
            public void message(final JSONObject jsonObject) {
                //contentWebView.loadUrl("javascript:javacalljswith(" + jsonObject.toString() + ")");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String swiftNumber = "";
                        try {
                            JSONArray jsonArray = jsonObject.optJSONArray("response");
                            JSONObject joSwiftNumber = jsonArray.optJSONObject(0);
                            swiftNumber = joSwiftNumber.optString("af_swift_number");
                            //tvResponse.setText(swiftNumber);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        contentWebView.loadUrl("javascript:javacalljswith(" + jsonObject.toString() + ")");
                        Log.e("wv jo", jsonObject.toString());
                    }
                });
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(JsCallJavaActivity.this, "show", Toast.LENGTH_LONG).show();

            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
                Toast.makeText(JsCallJavaActivity.this, "show:" + text, Toast.LENGTH_LONG).show();

            }
        });


    }
}
