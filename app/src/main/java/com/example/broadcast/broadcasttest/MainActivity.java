package com.example.broadcast.broadcasttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String action="com.android.server.scannerservice.broadcast";
    private MyReceiver receiver;

    Button send;
    TextView txtReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //获取程序界面中的按钮
        send = (Button)findViewById(R.id.btnSend);
        txtReceive = (TextView)findViewById(R.id.txtReceive);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time=System.currentTimeMillis();

                //创建Intent
                Intent intent = new Intent();
                intent.setAction(action);
                intent.putExtra("msg", "简单的消息" + time);
                //发送广播
                sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter(action);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String  barcode = intent.getStringExtra("scannerdata");
            //barcode 扫描到的条码数据
            txtReceive.setText(intent.getStringExtra("msg"));

            Toast.makeText(context,
                    "接收到的Intent的Action为：" + intent.getAction() + "\n 消息内容是：" + intent.getStringExtra("msg"),
                    Toast.LENGTH_LONG).show();
        }
    }
}


