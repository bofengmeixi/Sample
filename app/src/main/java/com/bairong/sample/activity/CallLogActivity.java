package com.bairong.sample.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.bairong.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangwei on 17/6/21.
 */

public class CallLogActivity extends AppCompatActivity {
    @BindView(R.id.text)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);
        ButterKnife.bind(this);
        if (requestPerssions())
        getCallLog();

    }

    private boolean requestPerssions(){
        int checkselfPerssion = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG);
        if (checkselfPerssion!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG)) {
                shouldShowRationale(this, 0, Manifest.permission.READ_CALL_LOG);

            }else
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG},0);
            return false;
        }
        return true;
    }

    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //TODO
        //String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        showMessageOKCancel(activity, "Rationale: " + "需要通话记录权限。。", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);
                Log.d("CallLog", "showMessageOKCancel requestPermissions:" + requestPermission);
            }
        });
    }
    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    getCallLog();
                else{
                    new AlertDialog.Builder(CallLogActivity.this)
                            .setTitle("打开设置界面")
                            .setMessage("this is message")
                            .setPositiveButton("可以", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    openSetting();
                                }
                            })
                            .setNegativeButton("不ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create()
                            .show();
                }
                break;
        }
    }
    private void openSetting(){
        Intent intent=new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void getCallLog() {
        ContentResolver contentResolver = getContentResolver();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor=contentResolver.query(CallLog.Calls.CONTENT_URI,
                new String[]{
                        CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.TYPE,
                        CallLog.Calls.DATE,
                        CallLog.Calls.DURATION
                },
                null,
                null,
                CallLog.Calls.DEFAULT_SORT_ORDER);
        if (cursor==null||cursor.getCount()<=0)
            return;
        while (cursor.moveToNext()){
            textView.append("姓名:"+cursor.getString(0)+";");
            textView.append("号码:"+cursor.getString(1)+";");
            textView.append("类型:"+cursor.getString(2)+";");
            textView.append("日期:"+cursor.getString(3)+";");
            textView.append("时长:"+cursor.getString(4)+"\r\n");
        }
    }
}
