package com.planetinnovative.androidservice.activity;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.planetinnovative.androidservice.R;
import com.planetinnovative.androidservice.service.MainBoundService;
import com.planetinnovative.androidservice.service.MainService;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection mServerConn;
    private MainBoundService mainBoundService;
    Intent serIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnMainService = findViewById(R.id.btnMainService);
        Button btnIntentService = findViewById(R.id.btnIntentService);
        Button btnBoundService = findViewById(R.id.btnBoundService);
        Button btnUnbind = findViewById(R.id.btnUnbind);
        Button btnGetNumber = findViewById(R.id.btnGetNumber);
        Button btnBoundServiceStop = findViewById(R.id.btnBoundServiceStop);
        //Server Connection
        mServerConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                System.out.println("CONECTION");
                System.out.println(name);
                System.out.println(service);
                MainBoundService.MyBinder binder = (MainBoundService.MyBinder) service;
                mainBoundService = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                System.out.println("Disconnect");
                System.out.println(name);
            }
        };

        //Click Listeners
        //Start Main Service
        btnMainService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(Thread.currentThread().getName());
                serIntent = new Intent(getApplicationContext(), MainService.class);
                startService(serIntent);
            }
        });
        //Start BoundService
        btnBoundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serIntent = new Intent(getApplicationContext(), MainBoundService.class);
                bindService(serIntent, mServerConn, Context.BIND_ABOVE_CLIENT);
                startService(serIntent);
            }
        });

        btnUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mServerConn);
            }
        });
        btnBoundServiceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serIntent);

            }
        });
        //Get Data
        btnGetNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("SELECTED NUMBER IS" + mainBoundService.getRandomNumber());
            }
        });


    }
}
