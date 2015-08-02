package com.google.samples.apps.ibeaconapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.samples.apps.ibeaconapp.beaconinterface.IBeacon;
import com.google.samples.apps.ibeaconapp.lightbluebean.IBeaconManager;
import com.google.samples.apps.iosched.R;
import com.google.samples.apps.iosched.ui.BaseActivity;

import java.util.*;

import static com.google.samples.apps.iosched.util.LogUtils.makeLogTag;

public class IBeaconActivity extends BaseActivity {
    private static final String TAG = makeLogTag(IBeaconActivity.class);
    private static final String SCREEN_LABEL = "IBeacon";
    private List<String> beansStrings = new ArrayList<String>();
    private ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFinishing()) {
            return;
        }
        setContentView(R.layout.activity_ibeacon);
        ListView listView = (ListView) findViewById(R.id.IbeaconListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, beansStrings);
        listView.setAdapter(adapter);
        updateAdapter();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_IBEACON;
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        updateAdapter();
    }

    private void updateAdapter() {
        List<IBeacon> iBeacons = IBeaconManager.getInstance().getIBeacons();
        adapter.clear();
        for (IBeacon iBeacon : iBeacons) {
            adapter.add("Name: " + iBeacon.getName() + "\nAddress: "
                    + iBeacon.getAddress() + "\nRSSI: " + iBeacon.getRssi() + "dBm");
        }
        adapter.notifyDataSetChanged();
    }
}
