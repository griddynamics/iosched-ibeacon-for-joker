package com.google.samples.apps.ibeaconapp.lightbluebean;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.google.samples.apps.ibeaconapp.beaconinterface.Ibeacon;
import com.google.samples.apps.ibeaconapp.beaconinterface.IbeaconInerface;
import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanDiscoveryListener;
import nl.littlerobots.bean.BeanManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class LightBlueBeanManager implements IbeaconInerface {

    private List<Ibeacon> ibeaconList = new ArrayList<Ibeacon>();

    BeanDiscoveryListener listener;

    List<String> beanString = new ArrayList<String>();

    Object syncKey = new Object();

    @Override
    public List<Ibeacon> getBeaconsList() {
        return ibeaconList;
    }

    public List<Ibeacon> putInAdapter(final ArrayAdapter adapter, Activity activity) {

        AsynkTaskBeanSearching asynkTaskBeanSearching = new AsynkTaskBeanSearching(adapter, ibeaconList, activity);
        asynkTaskBeanSearching.execute();

        listener = new BeanDiscoveryListener() {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi, List<UUID> uuids) {

                String s = "Name: \"" + bean.getDevice().getName() + "\"\nAddress: \"" + bean.getDevice().getAddress() + "\""
                        + "\nRSSI: " + rssi + "dBm";
                Log.v("Bean discovered!", s);
                System.out.println("startDiscovery(listener): \n" + bean.getDevice().getName() + ": " + bean.getDevice().getName());
                ibeaconList.add(new LightBlueBeanBeacon(bean));
                beanString.add(s);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onDiscoveryComplete() {
                adapter.notifyDataSetChanged();
            }
        };
        BeanManager.getInstance().startDiscovery(listener);
        return ibeaconList;
    }

    @Override
    public List<String> getBeaconsNameAndAddress() {

        List<String> beaconNamesAndAddresses = new ArrayList<String>();

        for (Ibeacon ibeacon : ibeaconList) {
            beaconNamesAndAddresses.add(ibeacon.getName() + ": " + ibeacon.getName());
        }

        return beaconNamesAndAddresses;
    }

    class AsynkTaskBeanSearching extends AsyncTask<Void, Void, Void> {

        Activity activity;

        ArrayAdapter adapter;
        List<Ibeacon> ibeaconsList;

        AsynkTaskBeanSearching(ArrayAdapter adapter, List<Ibeacon> ibeaconsList, Activity activity) {
            this.adapter = adapter;
            this.ibeaconsList = ibeaconsList;
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                try {
                    Thread.sleep(2000);
                    System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Collections.sort(beanString);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            System.out.println("Thread@.currentThread().getName() = " + Thread.currentThread().getName());
                            adapter.clear();
                            adapter.addAll(beanString);
                            adapter.notifyDataSetChanged();
                            beanString.clear();
                            BeanManager.getInstance().startDiscovery(listener);
                    }

                });
            }
        }
    }
}