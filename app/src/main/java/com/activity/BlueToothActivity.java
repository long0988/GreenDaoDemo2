package com.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.utils.ToastUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;

public class BlueToothActivity extends BaseActivity {
    private static final int REQUEST_ENABLE_BT = 0x001;
    BluetoothAdapter mBluetoothAdapter = null;
    @BindView(R.id.listview)
    ListView listview;
    ArrayAdapter mArrayAdapter;
    private List<BluetoothDevice> mClassName2=new ArrayList<>();
    private List<String> mClassName=new ArrayList<>();
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,mClassName);
        listview.setAdapter(mArrayAdapter);
        //蓝牙模糊定位权限
        initPermission();
        initDiscoverAble();
        // 发现设备广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                connect(position);
            }
        });
    }

    private void initPermission() {
        XXPermissions.with(this)
                .permission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION})
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
    }

    //设置本机设备可以永久检测到
    private void initDiscoverAble() {
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 2000);
        startActivity(discoverableIntent);
    }

    //打开蓝牙
    public void openBlueTooth(View view) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            ToastUtil.show(this, "该设备不支持蓝牙功能");
            return;
        }
        //没开启蓝牙请求开启蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    //发现设备
    public void scanBlueTooth(View view) {
        boolean b = mBluetoothAdapter.startDiscovery();
//        if(!b){
//            ToastUtil.show(this, "蓝牙设备未开启");
//            return;
//        }
        //注册广播监听搜索的设备
    }

    //配对过的设备
    public void scanEd(View view) {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.e("eeeeeeeeeee","配对过的设备:"+device.getName() + "\n" + device.getAddress());
                mArrayAdapter.add("配对过的设备名称:"+device.getName() + "\n" + device.getAddress());
                mClassName2.add(device);
            }
            mArrayAdapter.notifyDataSetChanged();
        }
    }
    //蓝牙连接
    public void connect(int position){
        Log.e("eeeeeeeeee","position--------");
        mBluetoothAdapter.cancelDiscovery();
        BluetoothDevice bluetoothDevice = mClassName2.get(position);
        new ConnectThread2(bluetoothDevice).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                ToastUtil.show(this, "蓝牙已开启");
            } else {
                ToastUtil.show(this, "蓝牙无法打开");
            }
        }
    }
    //开启检索式监听系统返回的蓝牙设备
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add("发现设备名称:"+device.getName() + "\n" + device.getAddress());
                mClassName2.add(device);
                Log.e("eeeeeeeeeee","发现设备:"+device.getName() + "\n" + device.getAddress());
                mArrayAdapter.notifyDataSetChanged();
            }
        }
    };
    //客户端发起连接到其他设备进行数据传输、放在子线程中进行，否则阻塞主线程
    //放在子线程连接还是报错，故从此连接
    private class ConnectThread2 extends Thread {
        private BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        private ConnectThread2(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                UUID MY_UUID = UUID.randomUUID();
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e("eeeeeeeeee","连接失败"+e.getMessage());
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            super.run();
            // Cancel discovery because it will slow down the connection
            //mBluetoothAdapter.cancelDiscovery();
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    Method m = mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                    mmSocket = (BluetoothSocket) m.invoke(mmDevice, 1);
                    mmSocket.connect();
                } catch (Exception e) {
                    Log.e("eeeeeeeeee",e.toString());
                    try{
                        mmSocket.close();
                    }catch (IOException ie){
                        Log.e("eeeeeeeeee",ie.getMessage());
                    }
                }
                return;
            }
            // Do work to manage the connection (in a separate thread)
            // 连接后执行数据传输工作
            //manageConnectedSocket(mmSocket);
            try {
                Log.e("eeeeeeeeee","1111111111"+mmSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("eeeeeeeeee","00000000000"+e.getMessage());
            }
        }
        /** Will cancel an in-progress connection, and close the socket
         * 将取消正在进行的连接，并关闭套接字。
         * */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }

    }
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        private ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {

                Log.e("eeeeeeeeee","uuid-"+device.getUuids());
                // MY_UUID is the app's UUID string, also used by the server code
                UUID MY_UUID = UUID.randomUUID();
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(String.valueOf(MY_UUID)));
                Log.e("eeeeeeeeee","开始连接");
            } catch (IOException e) {
                Toast.makeText(BlueToothActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            super.run();
            // Cancel discovery because it will slow down the connection
            //mBluetoothAdapter.cancelDiscovery();
            // Toast.makeText(BlueToothActivity.this,"连接失败2",Toast.LENGTH_SHORT).show();
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            new Thread(){
                @Override
                public void run() {
                    try {
                        Log.e("eeeeeeeeee","开始等待用户同意");
                        mmSocket.connect();
                        Log.e("eeeeeeeeee","链接结束");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("eeeeeeeeee","111111111111"+e.getMessage());
                        Log.e("eeeeeeeeee","链接结束");
                    }
                }
            }.start();
            // Do work to manage the connection (in a separate thread)
            // 连接后执行数据传输工作
            //manageConnectedSocket(mmSocket);
            try {
                Log.e("eeeeeeeeee","00000000000"+mmSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("eeeeeeeeee","00000000000"+e.getMessage());
            }
        }
        /** Will cancel an in-progress connection, and close the socket
         * 将取消正在进行的连接，并关闭套接字。
         * */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }

    }


        @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        if(mBluetoothAdapter!=null){
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothAdapter=null;
        }
    }
}
