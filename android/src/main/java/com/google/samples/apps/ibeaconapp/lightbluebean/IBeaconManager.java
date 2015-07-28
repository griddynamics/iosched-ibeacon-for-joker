package com.google.samples.apps.ibeaconapp.lightbluebean;

import com.google.samples.apps.ibeaconapp.beaconinterface.IBeaconInterface;
import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanDiscoveryListener;
import nl.littlerobots.bean.BeanManager;

import java.util.*;

public class IBeaconManager implements IBeaconInterface{

    private static volatile IBeaconManager instance;
    private BeanDiscoveryListener beanDiscoveryListener;
    private volatile HashMap<Bean, Integer> tempBeansAndRssi = new HashMap<Bean, Integer>();
    private volatile HashMap<Bean, Integer> beansAndRssi = new HashMap<Bean, Integer>();
    private long scanDelay = 4000L;
    private final long timerDelay = 1000L;
    private Timer mTimer;
    private boolean isStarted = false;

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

    public void startScanning(){
        android.util.Log.w("test", "in start THREAD:" + Thread.currentThread().getName());
        beanDiscoveryListener = new BeanDiscoveryListener() {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi, List<UUID> list) {
                tempBeansAndRssi.put(bean, rssi);
            }

            @Override
            public void onDiscoveryComplete() {
                beansAndRssi.clear();
                beansAndRssi.putAll(tempBeansAndRssi);
                tempBeansAndRssi.clear();
            }
        };

        mTimer = new Timer();
        BeanUpdateTimerTask mTimerTask = new BeanUpdateTimerTask();
        BeanManager.setScanTimeout(scanDelay);
        mTimer.schedule(mTimerTask, 0, scanDelay + timerDelay);
    }

    class BeanUpdateTimerTask extends TimerTask {
        @Override
        public void run() {
            BeanManager.getInstance().startDiscovery(beanDiscoveryListener);
        }
    }

    public HashMap<Bean, Integer> getBeansAndRssi(){
        beansAndRssi.put(null,0);
        return beansAndRssi;
    }

    public void stopScanning() {
        android.util.Log.w("test", "in stop THREAD:" + Thread.currentThread().getName());
        mTimer.cancel();
        mTimer.purge();
        BeanManager.getInstance().cancelDiscovery();
    }
}