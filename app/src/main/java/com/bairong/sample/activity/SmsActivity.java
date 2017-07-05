package com.bairong.sample.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bairong.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangwei on 17/6/19.
 */

public class SmsActivity extends AppCompatActivity {
    @BindView(R.id.text)
    TextView textView;

    private final Uri SMS_URI=Uri.parse("content://sms/");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
        getSms();
    }

    private void getSms(){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(SMS_URI,null,null,null,"date desc");
        if (cursor==null)
            return;
        while (cursor.moveToNext()){
            String phoneNum=cursor.getString(cursor.getColumnIndex("address"));
            String person=cursor.getString(cursor.getColumnIndex("person"));
            String body=cursor.getString(cursor.getColumnIndex("body"));
            String type=cursor.getString(cursor.getColumnIndex("type"));
            textView.append("联系人:"+person+";号码:"+phoneNum+";内容:"+body+";类型:");
            if ("1".equals(type)){
                textView.append("收件");
            }else if ("2".equals(type)){
                textView.append("发件");
            }else {
                textView.append("未知");
            }
            textView.append("\r\n");
        }
    }
}
