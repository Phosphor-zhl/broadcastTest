package com.example.broadcast.broadcasttest;

import android.app.Activity;

import android.os.Bundle;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class MainActivity extends Activity  {

    private String action="com.example.chinaautoid";
    private MyReceiver receiver;

    TextView txtReceive;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = (Button)findViewById(R.id.btnSend);
        txtReceive = (TextView)findViewById(R.id.txtReceive);
        //initBroadcastReciever();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time=System.currentTimeMillis();
                Intent intent = new Intent();
                intent.setAction(action);
                intent.putExtra("scannerdata", "Sample data:" + time);
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter(action);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    /**
     * 初始化广播接收器，AUTOID系列安卓产品上的系统软件扫描工具相对应
     */
    private void initBroadcastReciever() {
        Intent intent = new Intent();
        intent.putExtra("barcode_send_mode", "BROADCAST");
        intent.putExtra("endchar", "NONE");
        sendBroadcast(intent);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            txtReceive.setText(intent.getStringExtra("scannerdata") );
        }
    }
}


