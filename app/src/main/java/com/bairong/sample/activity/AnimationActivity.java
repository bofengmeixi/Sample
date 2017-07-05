package com.bairong.sample.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bairong.sample.R;
import com.bairong.sample.custom.FrameAnimation;

/**
 * Created by zhangwei on 17/5/4.
 */

public class AnimationActivity extends AppCompatActivity {
    private ImageView imageView;
    private FrameAnimation frameAnimation;
    private int[] drawables = {R.mipmap.robot01, R.mipmap.robot02, R.mipmap.robot03
            , R.mipmap.robot04, R.mipmap.robot05, R.mipmap.robot06
            , R.mipmap.robot07, R.mipmap.robot08};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        imageView = (ImageView) findViewById(R.id.robot);
        frameAnimation = (FrameAnimation) findViewById(R.id.robot2);
        final AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationDrawable.start();
            }
        });
        frameAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameAnimation.setBitmapResoursID(drawables);
                frameAnimation.start();
            }
        });
    }
}
