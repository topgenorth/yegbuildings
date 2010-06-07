package net.opgenorth.yeg.historicalbuildings.views;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.opgenorth.yeg.historicalbuildings.Constants;
import net.opgenorth.yeg.historicalbuildings.R;
import net.opgenorth.yeg.historicalbuildings.data.IBuildingDataService;
import net.opgenorth.yeg.historicalbuildings.data.SlowBuildingDataService;
import net.opgenorth.yeg.historicalbuildings.model.Building;
import net.opgenorth.yeg.historicalbuildings.model.RelativeBuildingLocation;
import net.opgenorth.yeg.historicalbuildings.model.SortByDistanceFromLocation;
import net.opgenorth.yeg.historicalbuildings.util.LocationManagerBuilder;
import net.opgenorth.yeg.historicalbuildings.widget.BuildingListAdapter;
import net.opgenorth.yeg.historicalbuildings.widget.GoogleMapPin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildingListView extends ListActivity {
    private ProgressDialog _progressDialog;
    private TextView _foundHistoricalBuildingsTextView;
    private LocationManager _locationManager;
    private LocationListener _onLocationChange = new MyLocationListener();
    private Location _currentLocation = null;
    private List<RelativeBuildingLocation> _buildingList = new ArrayList<RelativeBuildingLocation>(0);
    BuildingListAdapter _buildingListAdapter;

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            _currentLocation = location;
            if (location == null) {
                Log.w(Constants.LOG_TAG, "We have a NULL location for some reason.");
                return;
            }
            Log.d(Constants.LOG_TAG, "new location " + location.getLongitude() + " " + location.getLatitude());

            updateWithNewLocation();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.i(Constants.LOG_TAG, "GPS Provider status changed.");
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.i(Constants.LOG_TAG, "GPS Provider is enabled.");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.i(Constants.LOG_TAG, "GPS Provider is disabled.");
        }
    }

    private void updateWithNewLocation()  {
        for (RelativeBuildingLocation relativeBuildingLocation : _buildingList) {
            relativeBuildingLocation.setRelativeLocation(_currentLocation);
        }
        Collections.sort(_buildingList);
        _buildingListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        _foundHistoricalBuildingsTextView = (TextView) findViewById(R.id.info);

        _locationManager = LocationManagerBuilder.createLocationManager()
                .with(this)
                .listeningWith(_onLocationChange)
                .build();


        loadYegOpenData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _locationManager.removeUpdates(_onLocationChange);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _locationManager.removeUpdates(_onLocationChange);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(getApplication()).inflate(R.menu.main, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (R.id.configureSettings == itemId) {
            Toast.makeText(this, "Settings: Coming in a future version.", Toast.LENGTH_SHORT).show();
        } else if (R.id.refreshData == itemId) {
            loadYegOpenData();
        } else if (R.id.showAllOnMap == itemId) {
            Toast.makeText(this, "Map All: Coming in a future version.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        RelativeBuildingLocation relativeBuilding = (RelativeBuildingLocation) l.getItemAtPosition(position);
        Intent intent = new Intent(BuildingListView.this, BuildingMap.class);
        GoogleMapPin mapPin = new GoogleMapPin(relativeBuilding.getBuilding());
        mapPin.putExtra(intent);

        startActivity(intent);
    }

    private void loadYegOpenData() {
        _progressDialog = ProgressDialog
                .show(BuildingListView.this, "Please wait...", "Retrieving data...", true);
        new HistoricalBuildingFetcher(_currentLocation).execute();
    }

    private class HistoricalBuildingFetcher extends AsyncTask<Void, Void, List<Building>> {
        private Location _myLocation;

        HistoricalBuildingFetcher(Location myLocation) {
            _myLocation = myLocation;
        }

        @Override
        protected List<Building> doInBackground(Void... voids) {
            IBuildingDataService dataService = new SlowBuildingDataService();
            if (_myLocation == null) {
                Log.w(Constants.LOG_TAG, "_myLocation is NULL - won't be able to sort by distance to me.");
            }
            SortByDistanceFromLocation sortByDistanceFromMyLocation = new SortByDistanceFromLocation(_myLocation);
            return dataService.fetchAll(sortByDistanceFromMyLocation);
        }

        @Override
        protected void onPostExecute(List<Building> buildings) {
            _buildingList = new ArrayList<RelativeBuildingLocation>(buildings.size());
            for (Building building : buildings) {
                _buildingList.add(new RelativeBuildingLocation(building, _currentLocation));
            }
            _buildingListAdapter = new BuildingListAdapter(BuildingListView.this, _buildingList);
            setListAdapter(_buildingListAdapter);

            _progressDialog.dismiss();
            _foundHistoricalBuildingsTextView.setText("Found " + buildings.size() + " buildings.");
        }
    }
}
