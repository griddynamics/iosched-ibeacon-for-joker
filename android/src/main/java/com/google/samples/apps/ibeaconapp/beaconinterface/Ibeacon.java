package com.google.samples.apps.ibeaconapp.beaconinterface;

import android.bluetooth.BluetoothDevice;

public interface Ibeacon {

    String getName();

    String getAddress();

    BluetoothDevice getDevice();

}
