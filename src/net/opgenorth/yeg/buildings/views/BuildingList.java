package net.opgenorth.yeg.buildings.views;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.data.IBuildingDataService;
import net.opgenorth.yeg.buildings.data.SlowBuildingDataService;
import net.opgenorth.yeg.buildings.data.SqliteContentProvider;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.RelativeBuildingLocation;
import net.opgenorth.yeg.buildings.model.SortByDistanceFromLocation;
import net.opgenorth.yeg.buildings.util.BuildingContentValuesTransmorgifier;
import net.opgenorth.yeg.buildings.util.ITransmorgifier;
import net.opgenorth.yeg.buildings.util.LocationManagerBuilder;
import net.opgenorth.yeg.buildings.widget.BuildingListAdapter;
import net.opgenorth.yeg.buildings.widget.GoogleMapPin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BuildingList extends ListActivity {
    private ProgressDialog _progressDialog;
    private TextView _foundHistoricalBuildingsTextView;
    private LocationManager _locationManager;
    private LocationListener _onLocationChange = new MyLocationListener();
    private Location _currentLocation = null;
    private List<RelativeBuildingLocation> _buildingList = new ArrayList<RelativeBuildingLocation>(76);
    BuildingListAdapter _buildingListAdapter;

    private void updateWithNewLocation() {
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

        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(SqliteContentProvider.Columns.CONTENT_URI);
        }

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
        Intent intent;
        Uri uri = getIntent().getData();
        if (uri == null) {
            intent = new Intent(BuildingList.this, BuildingMap.class);
        } else {
            intent = new Intent(Intent.ACTION_VIEW, uri);
        }
        GoogleMapPin mapPin = new GoogleMapPin(relativeBuilding.getBuilding());
        mapPin.putExtra(intent);
        startActivity(intent);
    }

    private void loadYegOpenData() {
        if (!hasRecords()) {
            _progressDialog = ProgressDialog.show(BuildingList.this, "Please wait...", "Retrieving data...", true);
            new FetchBuildingsFromYegOpenData(_currentLocation).execute();
        }
        else {
            _buildingList.clear();
            Cursor c = getContentResolver().query(SqliteContentProvider.Columns.CONTENT_URI, SqliteContentProvider.Columns.ALL_COLUMNS, null, null, null);
            c.moveToFirst();
            do {
                Building building = new Building();
                building.setId(c.getLong(0));
                building.setName(c.getString(1));
                building.setRowKey(UUID.fromString(c.getString(2)));
                building.setAddress(c.getString(3));
                building.setNeighbourHood(c.getString(4));
                building.setUrl(c.getString(5) );
                building.setConstructionDate(c.getString(5));

                double latitude = Double.parseDouble(c.getString(6));
                double longitude = Double.parseDouble(c.getString(7));
                building.setLocation(latitude, longitude);

                RelativeBuildingLocation  buildingLocation= new RelativeBuildingLocation(building, _currentLocation );
                _buildingList.add(buildingLocation);
            } while (c.moveToNext());

            c.close();
            _buildingListAdapter = new BuildingListAdapter(BuildingList.this, _buildingList);
            setListAdapter(_buildingListAdapter); 
        }
    }

    private boolean hasRecords() {
        String[] columnsToReturn = new String[1];
        columnsToReturn[0] = SqliteContentProvider.Columns.ROW_KEY;
        Cursor c = getContentResolver().query(SqliteContentProvider.Columns.CONTENT_URI, columnsToReturn, null, null, null);
        c.moveToLast();
        int numberOfRows = c.getCount();
        c.close();

        return numberOfRows > 0;
    }

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

    private class FetchBuildingsFromYegOpenData extends AsyncTask<Void, Void, List<Building>> {
        private Location _myLocation;
        private boolean _hadDataBeenLoaded = false;
        private ITransmorgifier<Building, ContentValues> _toContentValues = new BuildingContentValuesTransmorgifier();

        FetchBuildingsFromYegOpenData(Location myLocation) {
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
            _buildingList.clear();
            for (Building building : buildings) {
                _buildingList.add(new RelativeBuildingLocation(building, _currentLocation));
                addBuilding(building);
            }
            _buildingListAdapter = new BuildingListAdapter(BuildingList.this, _buildingList);
            setListAdapter(_buildingListAdapter);

            _progressDialog.dismiss();
            _foundHistoricalBuildingsTextView.setText("Found " + buildings.size() + " buildings.");
        }

        protected void addBuilding(Building building) {
            ContentValues contentValues = _toContentValues.transmorgify(building);
            getContentResolver().insert(SqliteContentProvider.Columns.CONTENT_URI, contentValues);
        }
    }
}
