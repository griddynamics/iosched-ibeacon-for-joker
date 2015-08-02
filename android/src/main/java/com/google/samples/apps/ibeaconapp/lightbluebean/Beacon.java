package com.google.samples.apps.ibeaconapp.lightbluebean;

import android.bluetooth.BluetoothDevice;
import com.google.samples.apps.ibeaconapp.beaconinterface.IBeacon;
import nl.littlerobots.bean.Bean;

public class Beacon implements IBeacon {

    private Bean bean;
    private int rssi;

    public Beacon(Bean bean, int rssi) {
        this.bean = bean;
        this.rssi = rssi;
    }

    @Override
    public String getName() {
        return bean.getDevice().getName();
    }

    @Override
    public String getAddress() {
        return bean.getDevice().getName();
    }

    @Override
    public BluetoothDevice getDevice() {
        return bean.getDevice();
    }

    @Override
    public Bean getBean() {
        return bean;
    }

    @Override
    public int getRssi() {
        return rssi;
    }

    @Override
    public int compareTo(IBeacon another) {
        return rssi < another.getRssi() ? -1 : (rssi == another.getRssi() ? 0 : 1);
    }
}