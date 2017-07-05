package com.bairong.sample.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bairong.sample.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by zhangwei on 17/5/18.
 */

public class GzipActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzip);
        editText= (EditText) findViewById(R.id.edit);
        button= (Button) findViewById(R.id.btn);
        textView= (TextView) findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et = editText.getText().toString();
                if (TextUtils.isEmpty(et))
                    et="Perhaps you are an average student with average intelligence.You do well enough in school,but you probably think you will never be a top student.This is not necessarily the case,however.You can receive better grades if you want to.Yes,even students of average intelligence can be top students without additional work.Here's how";
                String content = new String(gzip(et));
                textView.setText(content);
            }
        });
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
        return Base64.encode(bytes,Base64.DEFAULT);
    }
}
