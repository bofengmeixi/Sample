package com.bairong.sample.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.bairong.sample.R;

import java.util.List;

/**
 * Created by zhangwei on 17/4/13.
 */

public class PackageListActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);
        textView= (TextView) findViewById(R.id.tv);
        getPackageList();
    }

    private void getPackageList(){
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);

        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            /*AppInfo tmpInfo =new AppInfo();
            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            tmpInfo.packageName = packageInfo.packageName;
            tmpInfo.versionName = packageInfo.versionName;
            tmpInfo.versionCode = packageInfo.versionCode;
            tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
            appList.add(tmpInfo);*/
            Log.e("packageInfo",packageInfo.packageName);
            Log.e("packageInfo..name",packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0){
                textView.append(packageInfo.packageName+"&");
                textView.append(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
                textView.append("\r\n");
            }


        }
//好啦 这下手机上安装的应用数据都存在appList里了。
    }
}
