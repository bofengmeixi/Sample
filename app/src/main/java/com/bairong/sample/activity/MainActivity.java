package com.bairong.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bairong.sample.R;
import com.bairong.sample.utils.AESOperator;
import com.juniperphoton.flipperviewlib.FlipperView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FlipperView mFlipperView;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    List list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String encryption= AESOperator.getInstance().encrypt("asdf");
            String decryption=AESOperator.getInstance().decrypt(encryption);
            /*Log.e("enc",encryption);
            Log.e("dec",decryption);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //startActivity(new Intent(this,WifiStationActivity.class));
        //startActivity(new Intent(this,AdaptActivity.class));
        startActivity(new Intent(this,PackageListActivity.class));
        //startActivity(new Intent(this,VideoViewActivity.class));
        //startActivity(new Intent(this,OpenPptActivity.class));
        // startActivity(new Intent(this,TestActivity.class));
        //startActivity(new Intent(this,ThreadChangeUIActitity.class));
        //startActivity(new Intent(this,RxjavaTest.class));
        //startActivity(new Intent(this,JsCallJavaActivity.class));
        finish();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources()
                        .getDisplayMetrics()));
        listView = (ListView) findViewById(R.id.lv);
        initList();
        //swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_dark,android.R.color.holo_red_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("add");
                        if (arrayAdapter!=null){
                            arrayAdapter.notifyDataSetChanged();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
        });
        /*mFlipperView = (FlipperView) findViewById(R.id.flipper_view);
        View prevView = findViewById(R.id.prev_btn);
        View nextView = findViewById(R.id.next_btn);

        prevView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlipperView.previous();
            }
        });
        nextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlipperView.next();
            }
        });*/
    }
    private void initList(){
        list.add("one");
        list.add("two");
        list.add("three");
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        listView.setAdapter(arrayAdapter);
    }
}
