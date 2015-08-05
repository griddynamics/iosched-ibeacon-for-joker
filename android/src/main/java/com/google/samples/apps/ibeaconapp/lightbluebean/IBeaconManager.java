package com.google.samples.apps.ibeaconapp.lightbluebean;

import android.app.Activity;
import android.database.Cursor;
import android.hardware.camera2.CameraManager;
import com.google.samples.apps.ibeaconapp.IBeaconActivity;
import com.google.samples.apps.ibeaconapp.beaconinterface.IBeacon;
import com.google.samples.apps.ibeaconapp.beaconinterface.IBeaconInterface;
import com.google.samples.apps.iosched.provider.ScheduleContract;
import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanDiscoveryListener;
import nl.littlerobots.bean.BeanManager;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class IBeaconManager implements IBeaconInterface {

    private static volatile IBeaconManager instance;
    private static BeanDiscoveryListener beanDiscoveryListener;
    private List<IBeacon> iBeacons = new ArrayList<IBeacon>();
    private static final long SCAN_DELAY = 4000L;
    private static final long TIMER_DELAY = 1000L;
    private Timer mTimer;
    Activity activity;

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

    public void startScanning(final Activity activity) {
        android.util.Log.w("test", "in start THREAD:" + Thread.currentThread().getName());

        beanDiscoveryListener = new BeanDiscoveryListener() {
            private List<IBeacon> tempIBeacon = new ArrayList<IBeacon>();

            @Override
            public void onBeanDiscovered(Bean bean, int rssi, List<UUID> list) {
                String roomName = null;
                String roomId = null;
                String selection = ScheduleContract.Rooms.ROOM_BEACON_MAC_ADDRESS + "=?";
                final Cursor c = activity.getContentResolver().query(
                        ScheduleContract.Rooms.CONTENT_URI,
                        new String[]{
                                ScheduleContract.Rooms.ROOM_NAME,
                                ScheduleContract.Rooms.ROOM_ID
                        },
                        selection,
                        new String[]{(bean.getDevice().getAddress())},
                        null);
                if (c != null && c.moveToFirst()) {
                    roomName = c.getString(c.getColumnIndex(ScheduleContract.Rooms.ROOM_NAME));
                    roomId = c.getString(c.getColumnIndex(ScheduleContract.Rooms.ROOM_ID));
                }
                c.close();
                tempIBeacon.add(new Beacon(bean, rssi, roomName, roomId));
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