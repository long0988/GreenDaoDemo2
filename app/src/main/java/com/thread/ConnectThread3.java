package com.thread;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread3 extends Thread{
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    public ConnectThread3(BluetoothDevice device) {
        BluetoothSocket tmp = null;
        mmDevice = device;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {

            // MY_UUID is the app's UUID string, also used by the server code
            UUID MY_UUID = UUID.randomUUID();
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(String.valueOf(MY_UUID)));
            Log.e("eeeeeeeeee","开始连接");
        } catch (IOException e) {
            Log.e("eeeeeeeeee",e.getMessage());
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
                    Log.e("eeeeeeeeee","链接失败--"+e.getMessage());
                }
                try {
                    Log.e("eeeeeeeeee","InputStream--"+mmSocket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("eeeeeeeeee","00000000000"+e.getMessage());
                }
            }
        }.start();
        // Do work to manage the connection (in a separate thread)
        // 连接后执行数据传输工作
        //manageConnectedSocket(mmSocket);
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
