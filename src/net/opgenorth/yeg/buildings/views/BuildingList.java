package net.opgenorth.yeg.buildings.views;

import android.app.ListActivity;
import android.location.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.data.IBuildingDataService;
import net.opgenorth.yeg.buildings.data.SQLiteBuildingDataService;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.RelativeBuildingLocation;
import net.opgenorth.yeg.buildings.util.LocationManagerBuilder;
import net.opgenorth.yeg.buildings.widget.BuildingListAdapter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BuildingList extends ListActivity {
	private List<RelativeBuildingLocation> _buildingList             = new ArrayList<RelativeBuildingLocation>(76);
	private LocationListener               _onLocationChangeListener = new MyLocationListener();
	private LocationManager     _locationManager;
	private TextView            _myGpsLocation;
	private TextView            _buildingCount;
	private BuildingListAdapter _buildingListAdapter;
	private Location            _currentLocation;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		initializeView();

		_locationManager = LocationManagerBuilder.createLocationManager()
				.with(this)
				.listeningWith(_onLocationChangeListener)
				.build();

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
		_buildingCount.setText("Found " + _buildingList.size() + " buildings.");
		_buildingListAdapter = new BuildingListAdapter(BuildingList.this, _buildingList);
		setListAdapter(_buildingListAdapter);
	}

	private void getBuildingList() {
		IBuildingDataService svc = new SQLiteBuildingDataService(this);

		for (Building building : svc.fetchAll()) {
			_buildingList.add(new RelativeBuildingLocation(building, null));
		}
	}

	private void updateWithNewLocation() {
		for (RelativeBuildingLocation relativeBuildingLocation : _buildingList) {
			relativeBuildingLocation.setRelativeLocation(_currentLocation);
		}
		Collections.sort(_buildingList);
		if (_buildingListAdapter == null) {
			Log.e(Constants.LOG_TAG, "Why isn't there a _buildingListAdapter?");
		}
		else {
			_buildingListAdapter.notifyDataSetChanged();
		}
		showMyGpsLocation();
	}

	private void showMyGpsLocation() {
		if (_myGpsLocation == null) {
			return;
		}
		String myLocation = "I'm not to sure where you are.";
		if (_currentLocation != null) {
			DecimalFormat formatter = new DecimalFormat("###.######");
			List myList;
			String lat = formatter.format(_currentLocation.getLatitude());
			String lon = formatter.format(_currentLocation.getLongitude());
			String loc = lat + "," + lon;
			myLocation = "My location: " + loc;
			Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
			try {
				myList = geoCoder.getFromLocation(_currentLocation.getLatitude(), _currentLocation.getLongitude(), 1);
				if (myList.size() > 0) {
					Address address = (Address) myList.get(0);
					myLocation = "My location: " + address.getAddressLine(0) + ", " + address.getLocality();
				}
				else {
					Log.d(Constants.LOG_TAG, "Couldn't translate the location " + loc + " into an address.");
				}
			}
			catch (IOException e) {
				myLocation = "My location: " + lat + ", " + lon;
			}
		}
		_myGpsLocation.setText(myLocation);
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
}