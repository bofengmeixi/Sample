package com.bairong.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bairong.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwei on 17/2/28.
 */

public class BrBottomSheetsDialogActivity extends AppCompatActivity {
    BottomSheetDialog bottomSheetDialog;

    List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheets_dialog);
        bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheets_content, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(null);
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.lv);

        list = new ArrayList();
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("bbb");
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(new MyAdapter());
        /*listView.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(getLayoutInflater().inflate(R.layout.item_bottom_sheet,null));
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.tv.setText(list.get(position));
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });*/
        //listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
    }

    public void bottom(View view) {
        //if (bottomSheetDialog.isShowing())
            bottomSheetDialog.dismiss();
        //else
            bottomSheetDialog.show();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(
                    BrBottomSheetsDialogActivity.this).inflate(R.layout.item_bottom_sheet, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }


}
