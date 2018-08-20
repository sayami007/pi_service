package com.planetinnovative.androidservice.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

public class MainBoundService extends Service {

    int randomNumber = 0;
    boolean isServiceOn = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("UN BIND");
        isServiceOn=false;
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isServiceOn = true;
        randomNumberGenerator();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isServiceOn = false;
        super.onDestroy();
    }

    void randomNumberGenerator() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isServiceOn) {
                        Thread.sleep(1000);
                        randomNumber = new Random().nextInt();
                        System.out.println(randomNumber);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    private MyBinder iBinder = new MyBinder();

    public class MyBinder extends Binder {
        public MainBoundService getService() {
            return MainBoundService.this;
        }
    }

}
