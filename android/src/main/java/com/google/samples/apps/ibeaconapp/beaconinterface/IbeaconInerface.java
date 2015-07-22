package com.google.samples.apps.ibeaconapp.beaconinterface;

import java.util.List;

public interface IbeaconInerface {

    List<Ibeacon> getBeaconsList();

    List<String> getBeaconsNameAndAddress();



}
