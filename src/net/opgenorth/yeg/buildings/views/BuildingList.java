package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.model.RelativeBuildingLocation;

import java.util.ArrayList;
import java.util.List;

public class BuildingList extends Activity {
    private TextView _myGpsLocation;
    private TextView _buildingCount;
    private List<RelativeBuildingLocation> _buildingList = new ArrayList<RelativeBuildingLocation>(76);

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildinglist);
        _myGpsLocation = (TextView) findViewById(R.id.buildinglist_my_gps);
        _buildingCount = (TextView) findViewById(R.id.buildinglist_building_count);


        displayGpsLocation();

        _buildingCount.setText("Found 0 buildings.");

    }

    private void displayGpsLocation() {
        _myGpsLocation.setText("This is where the user's location would be");
    }


}