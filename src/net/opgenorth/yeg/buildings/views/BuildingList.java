package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import net.opgenorth.yeg.buildings.R;

public class BuildingList extends Activity {
    private TextView _myGpsLocation;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildinglist);
        _myGpsLocation = (TextView) findViewById(R.id.building_list_my_gps);


        _myGpsLocation.setText("This is where the user's location would be");

    }
}