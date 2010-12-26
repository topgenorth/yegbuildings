package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
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

        Resources appResources = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        // Create an Intent to launch an activity for the "Map" tab
        intent = new Intent().setClass(this, BuildingMap.class);
        spec = tabHost.newTabSpec("artists").setIndicator("Map",
                appResources.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
        tabHost.addTab(spec);

        // Create an Intent to launch an activity for the "List" tab
        intent = new Intent().setClass(this, BuildingList.class);
        spec = tabHost.newTabSpec("albums").setIndicator("List",
                appResources.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}