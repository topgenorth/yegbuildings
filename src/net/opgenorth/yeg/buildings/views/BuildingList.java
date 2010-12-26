package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BuildingList extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
        tv.setText("This will show a list of buildings");
        setContentView(tv);

    }
}