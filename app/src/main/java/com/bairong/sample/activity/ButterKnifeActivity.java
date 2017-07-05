package com.bairong.sample.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bairong.sample.R;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by zhangwei on 17/5/25.
 */

public class ButterKnifeActivity extends AppCompatActivity {
     @Nullable @BindView(R.id.text)
    TextView textView;

    @BindString(R.string.text_content) String text;

    @Optional
    @OnClick(R.id.confirm)
    public void confirm(View view){
        textView.setText(text);
        //confirm();
    }

    @BindViews({R.id.text2,R.id.text3})
    List<TextView> list;

    @OnClick(R.id.confirm2)
    public void confirm(){
        ButterKnife.apply(list, new ButterKnife.Setter<TextView, Object>() {
            @Override
            public void set(@NonNull TextView view, Object value, int index) {
                if (index==0)
                view.setText(value.toString());
                else
                    view.setText("asdf");
                confirm(view);
            }
        },"pl");
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        //ButterKnife.bind(this);
    }
}
