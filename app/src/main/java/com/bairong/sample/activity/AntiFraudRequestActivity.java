package com.bairong.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bairong.sample.BrConstants;
import com.bairong.sample.R;
import com.bairong.sample.net.HttpEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.bairong.sample.R.id.MAC;

/**
 * Created by zhangwei on 17/5/11.
 */

public class AntiFraudRequestActivity extends AppCompatActivity {
    private EditText mac;
    private EditText gid;
    private EditText device_id;
    private EditText uuid;
    private Button post;
    private TextView textView;

    private final String url="http://das-test.bairong.cn/getGid.do";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_fraud_request);
        mac= (EditText) findViewById(MAC);
        gid= (EditText) findViewById(R.id.gid);
        device_id= (EditText) findViewById(R.id.device_id);
        uuid= (EditText) findViewById(R.id.UUID);
        post= (Button) findViewById(R.id.post);
        textView= (TextView) findViewById(R.id.response);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JSONObject jsonObject = new JSONObject();
                JSONObject afRequest = new JSONObject();
                try {
                    afRequest.put(BrConstants.GID,"123&456");
                    afRequest.put(BrConstants.MAC,mac.getText().toString());
                    afRequest.put(BrConstants.UUID,uuid.getText().toString());
                    afRequest.put(BrConstants.DEVICE_ID,device_id.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    //jsonObject.put(BrConstants.PLAT_TYPE,"android");
                    jsonObject.put(BrConstants.TYPE, BrConstants.ANDROID);
                    jsonObject.put(BrConstants.APICODE, "444333");
                    jsonObject.put(BrConstants.GID, gid.getText().toString());
                    jsonObject.put(BrConstants.DATA, afRequest);
                    //getDeviceInfo(context);
            /*jsonObject.put("IMEI", device_id);
            jsonObject.put(BrConstants.MAC, MAC);
            jsonObject.put(BrConstants.GID, gid);*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("request",jsonObject.toString());
                        String data = null;
                        try {
                            data = "paramStr=" + URLEncoder.encode(jsonObject.toString(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        final String result=HttpEngine.getInstance().postForm(url,data,null);
                        if (result!=null){
                            Log.e("result",result);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(result);
                                }
                            });
                        }
                    }
                }).start();
            }
        });
//44433300030014944984900795796716

    }
}
