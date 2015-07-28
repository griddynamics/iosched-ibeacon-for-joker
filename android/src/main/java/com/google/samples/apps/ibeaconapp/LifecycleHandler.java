package com.google.samples.apps.ibeaconapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.google.samples.apps.ibeaconapp.lightbluebean.IBeaconManager;

public class LifecycleHandler implements Application.ActivityLifecycleCallbacks {
    private int resumed;
    private int paused;
    private int started;
    private int stopped;
    private boolean isStopped = true;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        android.util.Log.w("test", "onActivityCreated");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        android.util.Log.w("test", "onActivityDestroyed");

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
        android.util.Log.w("test", "onActivityResumed" + "resumed:" + resumed + " paused:" + paused);
        if (resumed > paused && isStopped) {
            IBeaconManager.getInstance().startScanning();
            android.util.Log.w("test", "onActivityResumed" + " START SCANNING!");
            isStopped = false;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
        android.util.Log.w("test", "onActivityPaused" + "resumed:" + resumed + " paused:" + paused);
        android.util.Log.w("test", "application is in foreground: " + (resumed > paused));
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        android.util.Log.w("test", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
        android.util.Log.w("test", "onActivityStarted" + " started:" + started + " paused:" + stopped);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        android.util.Log.w("test", "onActivityStopped" + " started:" + started + " paused:" + stopped);
        android.util.Log.w("test", "application is visible: " + (started > stopped));
        if (!(started > stopped)) {
            IBeaconManager.getInstance().stopScanning();
            android.util.Log.w("test", "onActivityStopped" + " STOP SCANNING!");
            isStopped = true;
        }

    }

    // If you want a static function you can use to check if your application is
    // foreground/background, you can use the following:
    /*
    // Replace the four variables above with these four
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    // And these two public static functions
    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForeground() {
        return resumed > paused;
    }
    */
}