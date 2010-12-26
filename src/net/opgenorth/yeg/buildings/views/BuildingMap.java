package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BuildingMap extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("This will show a buildings on a map.");
        setContentView(tv);

    }
}