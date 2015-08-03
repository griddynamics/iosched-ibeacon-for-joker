package com.google.samples.apps.ibeaconapp.beaconinterface;

import android.bluetooth.BluetoothDevice;
import nl.littlerobots.bean.Bean;

public interface IBeacon extends Comparable<IBeacon> {

    String getName();

    String getAddress();

    BluetoothDevice getDevice();

    Bean getBean();

    int getRssi();

    String getRoomName();

}
