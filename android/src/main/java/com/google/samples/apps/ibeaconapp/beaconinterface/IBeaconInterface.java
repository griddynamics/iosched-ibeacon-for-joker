package com.google.samples.apps.ibeaconapp.beaconinterface;

import android.app.Activity;
import android.widget.ArrayAdapter;
import nl.littlerobots.bean.Bean;

import java.util.HashMap;
import java.util.List;

public interface IBeaconInterface {

    void startScanning();

    void stopScanning();

    List<IBeacon> getIBeacons();

}
