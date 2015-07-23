package com.google.samples.apps.ibeaconapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.samples.apps.ibeaconapp.lightbluebean.LightBlueBeanManager;
import com.google.samples.apps.iosched.R;
import com.google.samples.apps.iosched.ui.BaseActivity;
import com.google.samples.apps.iosched.util.AnalyticsManager;

import java.util.ArrayList;
import java.util.List;

import static com.google.samples.apps.iosched.util.LogUtils.LOGD;
import static com.google.samples.apps.iosched.util.LogUtils.makeLogTag;

public class IbeaconActivity extends BaseActivity {
    private static final String TAG = makeLogTag(IbeaconActivity.class);
    private static final String SCREEN_LABEL = "Ibeacon";
    private List<String> beansStrings = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFinishing()) {
            return;
        }

        setContentView(R.layout.activity_ibeacon);

        LightBlueBeanManager lightBlueBeanManager = new LightBlueBeanManager();

        ListView listView = (ListView) findViewById(R.id.IbeaconListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, beansStrings);
        listView.setAdapter(adapter);

        lightBlueBeanManager.showIbeacons(adapter, IbeaconActivity.this);

        adapter.notifyDataSetChanged();

        AnalyticsManager.sendScreenView(SCREEN_LABEL);
        LOGD("Tracker", SCREEN_LABEL);

        overridePendingTransition(0, 0);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_IBEACON;
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }



}
