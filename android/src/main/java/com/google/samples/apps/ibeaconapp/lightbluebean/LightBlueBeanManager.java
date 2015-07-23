package com.google.samples.apps.ibeaconapp.lightbluebean;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import com.google.samples.apps.ibeaconapp.beaconinterface.Ibeacon;
import com.google.samples.apps.ibeaconapp.beaconinterface.IbeaconInerface;
import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanDiscoveryListener;
import nl.littlerobots.bean.BeanManager;

import java.util.*;

public class LightBlueBeanManager implements IbeaconInerface {

    private Timer mTimer;
    private BeanUpdateTimerTask mTimerTask;
    private Activity iBeaconActivity;
    private ArrayAdapter listViewAdapter;
    private BeanDiscoveryListener beanDiscoveryListener;
    private final long scanDelay = 4000L;
    private final long timerDelay = 1000L;
    private List<String> beaconRssiList = new ArrayList<String>();

    @Override
    public List<Ibeacon> getBeaconsList() {
        return null;
    }

    @Override
    public List<String> getBeaconsNameAndAddress() {
        return null;
    }

    public void showIbeacons(final ArrayAdapter adapter, final Activity iBeaconActivity) {

        beanDiscoveryListener = new BeanDiscoveryListener() {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi, List<UUID> list) {
                beaconRssiList.add("Name: " + bean.getDevice().getName() + "\nAddress: "
                        + bean.getDevice().getAddress() + "\nRSSI: " + rssi + "dBm");
            }

            @Override
            public void onDiscoveryComplete() {
                System.out.println("COMPLETE!");
            }
        };

        mTimer = new Timer();
        mTimerTask = new BeanUpdateTimerTask();
        listViewAdapter = adapter;
        this.iBeaconActivity = iBeaconActivity;
        BeanManager.setScanTimeout(scanDelay);
        BeanManager.setScanTimeout(scanDelay);
        mTimer.schedule(mTimerTask, 0, scanDelay + timerDelay);
    }

    class BeanUpdateTimerTask extends TimerTask {
        @Override
        public void run() {

            iBeaconActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (iBeaconActivity.hasWindowFocus()) {

                        BeanManager.getInstance().cancelDiscovery();
                        listViewAdapter.clear();
                        Collections.sort(beaconRssiList);
                        listViewAdapter.addAll(beaconRssiList);
                        beaconRssiList.clear();
                        listViewAdapter.notifyDataSetChanged();
                        BeanManager.getInstance().startDiscovery(beanDiscoveryListener);

                    }

                }
            });

        }
    }

}
