package com.zues.ruiyu;

import android.app.Application;

import com.zues.sdk.yq.MDAdSdk;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MDAdSdk.init("621119","f8d054557046425fbc5dc2385be8f5c5",this);
    }
}
