package com.bairong.sample.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.bairong.sample.R;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.os.Build.BOARD;

/**
 * Created by zhangwei on 17/4/19.
 */

public class AdaptActivity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapt);
        tv= (TextView) findViewById(R.id.tv);
        tv.append(BOARD+":获取设备基板名称");
        tv.append("\r\n"+android.os.Build.BOOTLOADER+":获取设备引导程序版本号");
        tv.append("\r\n"+android.os.Build.BRAND+":获取设备品牌");
        tv.append("\r\n"+android.os.Build.CPU_ABI+":获取设备指令集名称（CPU的类型）");
        //tv.append("\r\n"+android.os.Build.CPU_ABI2+":获取第二个指令集名称");
        tv.append("\r\n"+android.os.Build.DEVICE+":获取设备驱动名称");
        tv.append("\r\n"+android.os.Build.DISPLAY+":获取设备显示的版本包（在系统设置中显示为版本号）和ID一样");
        tv.append("\r\n"+android.os.Build.FINGERPRINT+":设备的唯一标识。由设备的多个信息拼接合成。");
        tv.append("\r\n"+android.os.Build.HARDWARE+":设备硬件名称,一般和基板名称一样（BOARD）");
        tv.append("\r\n"+android.os.Build.HOST+":设备主机地址");
        //tv.append("\r\n"+android.os.Build.ID+":设备版本号。");
        //tv.append("\r\n"+android.os.Build.MODEL +":获取手机的型号 设备名称。");
        tv.append("\r\n"+android.os.Build.MANUFACTURER+":获取设备制造商");
        tv.append("\r\n"+android.os.Build.PRODUCT+":整个产品的名称");
        //tv.append("\r\n"+android.os.Build.RADIO+":无线电固件版本号，通常是不可用的 显示unknown");
        tv.append("\r\n"+android.os.Build.TAGS+":设备标签。如release-keys 或测试的 test-keys ");
        //tv.append("\r\n"+android.os.Build.TIME+":时间");
        tv.append("\r\n"+android.os.Build.TYPE+":设备版本类型  主要为'user' 或'eng'.");
        //tv.append("\r\n"+android.os.Build.USER+":设备用户名 基本上都为android-build");
        tv.append("\r\n"+android.os.Build.VERSION.RELEASE+":获取系统版本字符串。如4.1.2 或2.2 或2.3等");
        //tv.append("\r\n"+android.os.Build.VERSION.CODENAME+":设备当前的系统开发代号，一般使用REL代替");
        tv.append("\r\n"+android.os.Build.VERSION.INCREMENTAL+":系统源代码控制值，一个数字或者git hash值");
        //tv.append("\r\n"+android.os.Build.VERSION.SDK+":系统的API级别 一般使用下面大的SDK_INT 来查看");
        //tv.append("\r\n"+android.os.Build.VERSION.SDK_INT+":系统的API级别 数字表示");

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        tv.append("\r\n"+w_screen+"x"+h_screen);
        tv.append("\r\n"+ Build.VERSION.RELEASE+":操作系统版本");
        getMemory();
        tv.append("\r\n"+getLocalIpAddress()+":ip地址");
        tv.append("\r\n");
        tv.append("\r\n");
        tv.append("\r\n");
        tv.append("\r\n");

    }

    public String getLocalIpAddress()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex)
        {
            //Log.e("WifiPreference IpAddress", ex.toString());
            ex.printStackTrace();
        }
        return null;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    private void getMemory(){
        File dataFileDir= Environment.getDataDirectory();
        String data =getMemoryInfo(dataFileDir);
        String []d= data.split("##");
        tv.append("内部存储总大小：" + d[0]+"\r\n");
        tv.append("内部存储可用空间是:" + getAvailableInternalMemorySize()/1024/1024/1024.0+"\r\n");

        File SdFileDir=Environment.getExternalStorageDirectory();
        //判断Sd卡是否可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String sdMemory =getMemoryInfo(SdFileDir);
            String []sd=sdMemory.split("##");
            tv.append("内部存储总大小：" + sd[0]+"\r\n");
            tv.append("内部存储可用空间是:" + sd[1]+"\r\n");

        }
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }


    private String getMemoryInfo(File path) {
        long blockSize;
        long totalBlockCount;
        long avaiLabelCount;
        // TODO Auto-generated method stub
        StatFs stat = new StatFs(path.getPath());

        //检测系统版本
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.JELLY_BEAN_MR2){

            //获取每个扇区的大小
            blockSize = stat.getBlockSizeLong();

            //获取总共有多少扇区
            totalBlockCount=stat.getBlockCountLong();

            //获取可用扇区数量
            avaiLabelCount=stat.getAvailableBlocksLong();
        }else{
            blockSize=stat.getBlockSize();
            totalBlockCount=stat.getBlockCount();
            avaiLabelCount=stat.getAvailableBlocks();

        }




        // 磁盘总大小
        String totalMemory = Formatter.formatFileSize(this, blockSize*totalBlockCount);
        // 可用大小
        String availabelMemory=Formatter.formatFileSize(this, blockSize*avaiLabelCount);

        return totalMemory+"##"+availabelMemory;
    }
}
