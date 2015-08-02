package com.google.samples.apps.ibeaconapp;

import android.support.multidex.MultiDexApplication;

public class IBeaconApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new LifecycleHandler());
    }

}

