package com.google.samples.apps.ibeaconapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.samples.apps.ibeaconapp.lightbluebean.IBeaconManager;
import com.google.samples.apps.iosched.R;
import com.google.samples.apps.iosched.ui.BaseActivity;
import nl.littlerobots.bean.Bean;

import java.util.*;
import static com.google.samples.apps.iosched.util.LogUtils.makeLogTag;

public class IBeaconActivity extends BaseActivity {
    private static final String TAG = makeLogTag(IBeaconActivity.class);
    private static final String SCREEN_LABEL = "IBeacon";
    private List<String> beansStrings = new ArrayList<String>();
    private ArrayAdapter<String> adapter = null;
    private Activity activity = IBeaconActivity.this;
    Application mApp = (IBeaconApp) getApplication();

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

        HashMap<Bean, Integer> beanIntegerHashMap = IBeaconManager.getInstance().getBeansAndRssi();
        adapter.clear();
        for (Map.Entry<Bean, Integer> beanIntegerEntry : beanIntegerHashMap.entrySet()) {
            Bean bean = beanIntegerEntry.getKey();
            int rssi = beanIntegerEntry.getValue();
            if (!(bean == null)) {
                adapter.add("Name: " + bean.getDevice().getName() + "\nAddress: "
                        + bean.getDevice().getAddress() + "\nRSSI: " + rssi + "dBm");
            } else {
                adapter.add("No beacons found CODE:" + rssi);
            }
        }
        adapter.notifyDataSetChanged();

    }

}
