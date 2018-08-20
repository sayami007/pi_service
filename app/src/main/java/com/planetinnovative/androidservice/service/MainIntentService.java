package com.planetinnovative.androidservice.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class MainIntentService extends IntentService {

    public MainIntentService() {
        super("MainIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("Handle Intent");
    }
}
