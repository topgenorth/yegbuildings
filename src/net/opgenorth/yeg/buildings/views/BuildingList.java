package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.SQLiteBuildingDataService;
import net.opgenorth.yeg.buildings.data.IBuildingDataService;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.RelativeBuildingLocation;
import net.opgenorth.yeg.buildings.widget.BuildingListAdapter;

import java.util.ArrayList;
import java.util.List;

public class BuildingList extends ListActivity {
    private TextView _myGpsLocation;
    private TextView _buildingCount;
    private List<RelativeBuildingLocation> _buildingList = new ArrayList<RelativeBuildingLocation>(76);
    private BuildingListAdapter _buildingListAdapter;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initializeView();
        displayGpsLocation();
        getBuildingList();
        displayBuildingList();
    }

    private void initializeView() {
        setContentView(R.layout.buildinglist);
        _myGpsLocation = (TextView) findViewById(R.id.buildinglist_my_gps);
        _buildingCount = (TextView) findViewById(R.id.buildinglist_building_count);
    }

    private void displayGpsLocation() {
        _myGpsLocation.setText("This is where the user's location would be");
    }

    private void displayBuildingList() {
        _buildingCount.setText("Found " + _buildingList.size()+ " buildings.");
        _buildingListAdapter = new BuildingListAdapter(BuildingList.this, _buildingList);
        setListAdapter(_buildingListAdapter);
    }

    private void getBuildingList()  {
        IBuildingDataService svc = new SQLiteBuildingDataService(this);

        for (Building building : svc.fetchAll()) {
            _buildingList.add(new RelativeBuildingLocation(building, null));
        }
    }
}