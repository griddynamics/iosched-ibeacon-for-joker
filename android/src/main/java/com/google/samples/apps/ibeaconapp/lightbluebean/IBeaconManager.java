package com.google.samples.apps.ibeaconapp.lightbluebean;

import com.google.samples.apps.ibeaconapp.beaconinterface.IBeacon;
import com.google.samples.apps.ibeaconapp.beaconinterface.IBeaconInterface;
import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanDiscoveryListener;
import nl.littlerobots.bean.BeanManager;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class IBeaconManager implements IBeaconInterface {

    private static volatile IBeaconManager instance;
    private static BeanDiscoveryListener beanDiscoveryListener;
    private List<IBeacon> iBeacons = new CopyOnWriteArrayList<IBeacon>();
    private static final long SCAN_DELAY = 4000L;
    private static final long TIMER_DELAY = 1000L;
    private Timer mTimer;

    public static IBeaconManager getInstance() {
        IBeaconManager localInstance = instance;
        if (localInstance == null) {
            synchronized (IBeaconManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new IBeaconManager();
                }
            }
        }
        return localInstance;
    }

    public void startScanning() {
        android.util.Log.w("test", "in start THREAD:" + Thread.currentThread().getName());

        beanDiscoveryListener = new BeanDiscoveryListener() {

            private List<IBeacon> tempIBeacon = new ArrayList<IBeacon>();

            @Override
            public void onBeanDiscovered(Bean bean, int rssi, List<UUID> list) {
                tempIBeacon.add(new Beacon(bean, rssi));
            }

            @Override
            public void onDiscoveryComplete() {
                iBeacons.clear();
                iBeacons.addAll(tempIBeacon);
                tempIBeacon.clear();
            }
        };

        mTimer = new Timer();
        BeanUpdateTimerTask mTimerTask = new BeanUpdateTimerTask();
        BeanManager.setScanTimeout(SCAN_DELAY);
        mTimer.schedule(mTimerTask, 0, SCAN_DELAY + TIMER_DELAY);
    }

    class BeanUpdateTimerTask extends TimerTask {
        @Override
        public void run() {
            BeanManager.getInstance().startDiscovery(beanDiscoveryListener);
        }
    }

    public List<IBeacon> getIBeacons() {
        Collections.sort(iBeacons);
        return iBeacons;
    }

    public void stopScanning() {
        android.util.Log.w("test", "in stop THREAD:" + Thread.currentThread().getName());
        mTimer.cancel();
        mTimer.purge();
        BeanManager.getInstance().cancelDiscovery();
    }
}