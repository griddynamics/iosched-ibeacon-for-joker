package com.google.samples.apps.ibeaconapp;

import android.support.multidex.MultiDexApplication;
import com.google.samples.apps.ibeaconapp.lightbluebean.IBeaconManager;

public class IBeaconApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        IBeaconManager.getInstance().startScanning();
    }

    protected void initSingletons()
    {
        // Initialize the instance of MySingleton
    }

    public void customAppMethod()
    {
        // Custom application method
    }
}

