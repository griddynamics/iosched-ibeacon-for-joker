package com.google.samples.apps.ibeaconapp.lightbluebean;

import android.bluetooth.BluetoothDevice;
import com.google.samples.apps.ibeaconapp.beaconinterface.Ibeacon;
import nl.littlerobots.bean.Bean;

public class LightBlueBeanBeacon implements Ibeacon {

    private Bean bean;

    public LightBlueBeanBeacon(Bean bean) {

        this.bean = bean;

    }

    @Override
    public String getName() {
        return bean.getDevice().getName();
    }

    @Override
    public String getAddress() {
        return bean.getDevice().getAddress();
    }

    @Override
    public BluetoothDevice getDevice() {
        return bean.getDevice();
    }

}
