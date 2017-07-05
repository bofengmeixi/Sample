package com.bairong.sample.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bairong.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangwei on 17/6/15.
 */

public class CustomComponents extends FrameLayout {

    private Button button;
    @BindView(R.id.tv)
    public TextView textView;
    @BindView(R.id.iv)
    public ImageView imageView;

    public CustomComponents(@NonNull Context context) {
        super(context);
    }

    public CustomComponents(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomComponents(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,@Nullable AttributeSet attrs){
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);

        LayoutInflater.from(context).inflate(R.layout.custom_components,this);
        button= (Button) findViewById(R.id.btn);
        ButterKnife.bind(this);

    }

}
