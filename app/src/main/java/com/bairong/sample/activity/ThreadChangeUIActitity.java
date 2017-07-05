package com.bairong.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bairong.sample.R;

/**
 * Created by zhangwei on 17/3/27.
 */

public class ThreadChangeUIActitity extends AppCompatActivity {
    private TextView textView;
    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_thread_change_ui);
        textView= (TextView) findViewById(R.id.tv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                textView.setText("create");
            }
        }).start();
    }*/

    public void change(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                textView.setText("change");
            }
        }).start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_change_ui);
        textView= (TextView) findViewById(R.id.tv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //SystemClock.sleep(3000);
                textView.setText("create");
            }
        }).start();
    }
    /*@Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                textView.setText("start");
            }
        }).start();
    }*/

    /*@Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                textView.setText("resume");
            }
        }).start();
    }*/
}
