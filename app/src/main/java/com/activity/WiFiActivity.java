package com.activity;

import android.Manifest;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.popwindow.CustomPopWindow;
import com.receiver.WifiBroadcastReceiver;
import com.utils.ToastUtil;
import com.utils.WifiControlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by qlshi on 2018/12/6.
 */

public class WiFiActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView mListVew;
    private WifiBroadcastReceiver wifiBroadcastReceiver;
    private WifiControlUtils wifiControlUtils;

    private WifiInfo wifiInfo;
    List<ScanResult> wifiList;
    private List<String> mClassName=new ArrayList<>();
    Handler handler;
    View viewPop;
    private CustomPopWindow popupWindow;
    CustomPopWindow.PopupWindowBuilder popupWindowBuilder;
    EditText tvWifiName,tvWifiPSW;
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        //动态注册wifi状态广播
        wifiBroadcastReceiver = new WifiBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        registerReceiver(wifiBroadcastReceiver, intentFilter);

        wifiControlUtils = new WifiControlUtils(this);
        wifiControlUtils.openWifi();
        wifiInfo = wifiControlUtils.getWifiInfo();
         handler = new Handler();
    }

    @Override
    protected void initData() {
        super.initData();
        XXPermissions.with(this)
                .permission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.ACCESS_NETWORK_STATE})
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        handler.postDelayed(myRun,2000);
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
    }
    Runnable myRun=new Runnable() {
        @Override
        public void run() {
            initWifi();
        }
    };
    private void initWifi() {
        wifiList = wifiControlUtils.getWifiList();
        int size = wifiList.size();
        mClassName.clear();
        for(int i=0;i<size;i++){
            Log.e("eeeeee--size",size+"-size-"+wifiList.get(i).SSID+"-SSID-"+wifiList.get(i).SSID);
            mClassName.add(wifiList.get(i).SSID);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mClassName);
        mListVew.setAdapter(arrayAdapter);
        handler.postDelayed(myRun,3000);
        mListVew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initPopview(view,position);
            }
        });
    }

    private void initPopview(View view ,int position) {
        if(viewPop==null){
            viewPop = getLayoutInflater().inflate(R.layout.item_wifi, null);
            tvWifiName= (EditText) viewPop.findViewById(R.id.wifiName);
            tvWifiPSW= (EditText) viewPop.findViewById(R.id.wifiPSW);
             popupWindowBuilder = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(viewPop)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .enableOutsideTouchableDissmiss(true)
                    .setFocusable(true)
                    .setAnimationStyle(R.style.view_slide_anim);
        }
        popupWindow=popupWindowBuilder.setBgDarkAlpha(0.5f).create();
        tvWifiName.setText(wifiList.get(position).SSID);
//        popupWindow.showAsDropDown(view);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(myRun);
        handler=null;
        unregisterReceiver(wifiBroadcastReceiver);
    }

    public void connect(View view) {
        wifiControlUtils.addNetWork(tvWifiName.getText().toString(), tvWifiPSW.getText().toString(), WifiControlUtils.WIFI_CIPHER_WAP);
    }

    public void disConnect(View view) {
        wifiControlUtils.disconnectWifi(tvWifiName.getText().toString());
    }

    public void delete(View view) {
        if (!wifiControlUtils.removeWifi(tvWifiName.getText().toString())) {
            ToastUtil.show(this,R.string.unable_remove);
        }
    }
}
