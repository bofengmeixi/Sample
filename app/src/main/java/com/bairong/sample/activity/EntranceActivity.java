package com.bairong.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bairong.sample.R;
import com.bairong.sample.RxjavaTest;

/**
 * Created by zhangwei on 17/5/4.
 */


public class EntranceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String[] titles = new String[]{"JsCallJavaActivity", "RxjavaTest", "ThreadChangeUIActitity"
            , "TestActivity", "OpenPptActivity", "VideoViewActivity", "PackageListActivity"
            , "AdaptActivity", "WifiStationActivity", "AnimationActivity", "AntiFraudRequestActivity", "GzipActivity"
            , "ButterKnifeActivity", "CustomView", "Sms", "CallLog"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        recyclerView = (RecyclerView) findViewById(R.id.lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoler> {
        @Override
        public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHoler(LayoutInflater.from(EntranceActivity.this).inflate(R.layout.item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHoler holder, int position) {
            holder.textView.setText(titles[position]);
        }

        @Override
        public int getItemCount() {
            return titles == null ? 0 : titles.length;
        }

        class MyViewHoler extends RecyclerView.ViewHolder {
            private TextView textView;

            public MyViewHoler(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (getAdapterPosition()) {
                            case 0:
                                startActivity(new Intent(EntranceActivity.this, JsCallJavaActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(EntranceActivity.this, RxjavaTest.class));
                                break;
                            case 2:
                                startActivity(new Intent(EntranceActivity.this, ThreadChangeUIActitity.class));
                                break;
                            case 3:
                                startActivity(new Intent(EntranceActivity.this, TestActivity.class));
                                break;
                            case 4:
                                startActivity(new Intent(EntranceActivity.this, OpenPptActivity.class));
                                break;
                            case 5:
                                startActivity(new Intent(EntranceActivity.this, VideoViewActivity.class));
                                break;
                            case 6:
                                startActivity(new Intent(EntranceActivity.this, PackageListActivity.class));
                                break;
                            case 7:
                                startActivity(new Intent(EntranceActivity.this, AdaptActivity.class));
                                break;
                            case 8:
                                startActivity(new Intent(EntranceActivity.this, WifiStationActivity.class));
                                break;
                            case 9:
                                startActivity(new Intent(EntranceActivity.this, AnimationActivity.class));
                                break;
                            case 10:
                                startActivity(new Intent(EntranceActivity.this, AntiFraudRequestActivity.class));
                                break;
                            case 11:
                                startActivity(new Intent(EntranceActivity.this, GzipActivity.class));
                                break;
                            case 12:
                                startActivity(new Intent(EntranceActivity.this, ButterKnifeActivity.class));
                                break;
                            case 13:
                                startActivity(new Intent(EntranceActivity.this, CustomViewActivity.class));
                                break;
                            case 14:
                                startActivity(new Intent(EntranceActivity.this, SmsActivity.class));
                                break;
                            case 15:
                                startActivity(new Intent(EntranceActivity.this, CallLogActivity.class));
                                break;
                        }
                    }
                });
            }
        }
    }
}
