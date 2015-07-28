package com.google.samples.apps.ibeaconapp.beaconinterface;

import android.bluetooth.BluetoothDevice;

public interface IBeacon {

    String getName();

    String getAddress();

    BluetoothDevice getDevice();

}
