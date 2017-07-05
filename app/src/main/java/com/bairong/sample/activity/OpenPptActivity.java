package com.bairong.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bairong.sample.R;
import com.bairong.sample.utils.OpenFiles;

/**
 * Created by zhangwei on 17/4/12.
 */

public class OpenPptActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_ppt);
    }
    public void ppt(View view) {
        try {
            //   sdcard/test4.ppt为本地apk文件的路径
            Intent intent = OpenFiles.getPPTFileIntent("sdcard/Android/test1.ppt");
            startActivity(intent);
        } catch (Exception e) {
            //没有安装第三方的软件会提示
            Toast toast = Toast.makeText(OpenPptActivity.this, "没有找到打开该文件的应用程序", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    }
