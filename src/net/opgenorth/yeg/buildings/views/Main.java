package net.opgenorth.yeg.buildings.views;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import net.opgenorth.yeg.buildings.R;

public class Main extends TabActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setupTabs();
    }

    private void setupTabs() {
        Resources appResources = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        // Create an Intent to launch an activity for the "Map" tab
        intent = new Intent().setClass(this, BuildingMap.class);
        spec = tabHost.newTabSpec("map").setIndicator(
                appResources.getString(R.string.buildingmap_tabtext),
                appResources.getDrawable(R.drawable.ic_tab_map))
                .setContent(intent);
        tabHost.addTab(spec);

        // Create an Intent to launch an activity for the "List" tab
        intent = new Intent().setClass(this, BuildingList.class);
        spec = tabHost.newTabSpec("list").setIndicator(
                appResources.getString(R.string.buildinglist_tabtext),
                appResources.getDrawable(R.drawable.ic_tab_list))
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(1);
    }
}