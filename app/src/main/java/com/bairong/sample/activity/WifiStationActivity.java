package com.bairong.sample.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bairong.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwei on 17/4/20.
 */

public class WifiStationActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_station);
        textView = (TextView) findViewById(R.id.tv);
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Log.e("network",manager.getNetworkOperator());
        Log.e("sim",manager.getSimOperator());
        //Toast.makeText(this,location.getPsc(),Toast.LENGTH_SHORT).show();
        stationInfo();
        wifiInfo();
    }

    private void wifiInfo() {
        @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        ArrayList<ScanResult> list = (ArrayList<ScanResult>) wifiManager.getScanResults();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        textView.append("wifi" + "\r\n");
        if (list != null)
            for (int i = 0; i < list.size(); i++) {
                textView.append(list.get(i).BSSID + "&");
                textView.append(list.get(i).level + "&");
                textView.append(list.get(i).SSID + "\r\n");
            }
    }

    private void stationInfo() {
        textView.append("基站\r\n");
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        // 返回值MCC + MNC
        /*String operator = mTelephonyManager.getNetworkOperator();
        int mcc = Integer.parseInt(operator.substring(0, 3));
        int mnc = Integer.parseInt(operator.substring(3));

        // 中国移动和中国联通获取LAC、CID的方式
        GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
        int lac = location.getLac();
        int cellId = location.getCid();

        Log.i("zz", " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId);*/

        // 中国电信获取LAC、CID的方式
                /*CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                lac = location1.getNetworkId();
                cellId = location1.getBaseStationId();
                cellId /= 16;*/

        // 获取邻区基站信息
        List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
        StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
        for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
            sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
            sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
            sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
        }
        textView.append(sb.toString());

        Log.d("zz", " 获取邻区基站信息:" + sb.toString());

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        List<CellInfo> infoLists = telephonyManager.getAllCellInfo();
        if (infoLists != null) {
            textView.append("total:" + infoLists.size() + "\r\n");
            for (CellInfo info : infoLists) {
                if (info.getClass().equals(CellInfoWcdma.class)) {
                    int mnc = ((CellInfoWcdma) info).getCellIdentity().getMnc();
                    int mcc = ((CellInfoWcdma) info).getCellIdentity().getMcc();
                    int lac = ((CellInfoWcdma) info).getCellIdentity().getLac();
                    int cid = ((CellInfoWcdma) info).getCellIdentity().getCid();
                    int strength = ((CellInfoWcdma) info).getCellSignalStrength().getDbm();
                    if (mnc==Integer.MAX_VALUE&&mcc==Integer.MAX_VALUE&&lac==Integer.MAX_VALUE&&cid==Integer.MAX_VALUE)
                        continue;
                    textView.append("mnc:" + mnc + "&");
                    textView.append("mcc:" + mcc + "&");
                    textView.append("lac:" + lac + "&");
                    textView.append("cid:" + cid + "&");
                    textView.append("strength:" + strength + "\r\n");
                    //Toast.makeText(this, "CellInfoWcdma", Toast.LENGTH_SHORT).show();
                } else if (info.getClass().equals(CellInfoCdma.class)) {
                    /*int longitude = ((CellInfoCdma) info).getCellIdentity().getLongitude();
                    int latitude = ((CellInfoCdma) info).getCellIdentity().getLatitude();
                    int strength = ((CellInfoCdma) info).getCellSignalStrength().getDbm();

                    textView.append("longitude:" + longitude + "&");
                    textView.append("latitude:" + latitude + "&");
                    textView.append("strength:" + strength + "\r\n");*/
                    //Toast.makeText(this, "CellInfoCdma", Toast.LENGTH_SHORT).show();
                    int mnc = ((CellInfoCdma) info).getCellIdentity().getSystemId();
                    String mcc = telephonyManager.getNetworkOperator().substring(0, 3);
                    int lac = ((CellInfoCdma) info).getCellIdentity().getNetworkId();
                    int cid = ((CellInfoCdma) info).getCellIdentity().getBasestationId();
                    int strength = ((CellInfoCdma) info).getCellSignalStrength().getDbm();
                    if (mnc==Integer.MAX_VALUE&&lac==Integer.MAX_VALUE&&cid==Integer.MAX_VALUE)
                        continue;
                    textView.append("mnc:" + mnc + "&");
                    textView.append("mcc:" + mcc + "&");
                    textView.append("lac:" + lac + "&");
                    textView.append("cid:" + cid + "&");
                    textView.append("strength:" + strength + "\r\n");
                } else if (info.getClass().equals(CellInfoGsm.class)) {
                    int mnc = ((CellInfoGsm) info).getCellIdentity().getMnc();
                    int mcc = ((CellInfoGsm) info).getCellIdentity().getMcc();
                    int lac = ((CellInfoGsm) info).getCellIdentity().getLac();
                    int cid = ((CellInfoGsm) info).getCellIdentity().getCid();
                    int strength = ((CellInfoGsm) info).getCellSignalStrength().getDbm();
                    if (mnc==Integer.MAX_VALUE&&mcc==Integer.MAX_VALUE&&lac==Integer.MAX_VALUE&&cid==Integer.MAX_VALUE)
                        continue;
                    textView.append("mnc:" + mnc + "&");
                    textView.append("mcc:" + mcc + "&");
                    textView.append("lac:" + lac + "&");
                    textView.append("cid:" + cid + "&");
                    textView.append("strength:" + strength + "\r\n");
                    //Toast.makeText(this, "CellInfoGsm", Toast.LENGTH_SHORT).show();
                } else if (info.getClass().equals(CellInfoLte.class)) {
                    int mnc = ((CellInfoLte) info).getCellIdentity().getMnc();
                    int mcc = ((CellInfoLte) info).getCellIdentity().getMcc();
                    int lac = ((CellInfoLte) info).getCellIdentity().getTac();
                    int cid = ((CellInfoLte) info).getCellIdentity().getCi();
                    int strength = ((CellInfoLte) info).getCellSignalStrength().getDbm();
                    if (mnc==Integer.MAX_VALUE&&mcc==Integer.MAX_VALUE&&lac==Integer.MAX_VALUE&&cid==Integer.MAX_VALUE)
                        continue;
                    textView.append("mnc:" + mnc + "&");
                    textView.append("mcc:" + mcc + "&");
                    textView.append("lac:" + lac + "&");
                    textView.append("cid:" + cid + "&");
                    textView.append("strength:" + strength + "\r\n");
                    //Toast.makeText(this, "CellInfoLte", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "CellInfo", Toast.LENGTH_SHORT).show();
                }
                /*CellInfoWcdma cellInfoCdma = (CellInfoWcdma) info;
                CellIdentityWcdma cellIdentityCdma = cellInfoCdma.getCellIdentity();
                CellSignalStrengthWcdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
                int strength = cellSignalStrengthCdma.getDbm();
                int cid = cellIdentityCdma.getCid();

                textView.append("strength:"+strength+"&");
                textView.append("cid:"+cid+"\r\n");*/
                //textView.append("describeContents:" + info.describeContents() + "&");
                //textView.append("tostring:" + info.toString() + "\r\n");
                /*textView.append("纬度:"+cellIdentityCdma.g+"&");
                textView.append("经度:"+cellIdentityCdma.getLongitude()+"\r\n");*/
                // 处理 strength和id数据
            }
        } else {
            textView.append("infolist null");
        }
    }
}
