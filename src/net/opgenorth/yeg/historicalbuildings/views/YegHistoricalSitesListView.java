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
import net.opgenorth.yeg.historicalbuildings.data.IReadOnlyBuildingRepository;
import net.opgenorth.yeg.historicalbuildings.data.YegOpenDataHistoricalBuildingRepository;
import net.opgenorth.yeg.historicalbuildings.model.Building;
import net.opgenorth.yeg.historicalbuildings.model.RelativeBuildingLocation;
import net.opgenorth.yeg.historicalbuildings.model.SortByDistanceFromLocation;
import net.opgenorth.yeg.historicalbuildings.util.LocationManagerBuilder;
import net.opgenorth.yeg.historicalbuildings.widget.GoogleMapPin;
import net.opgenorth.yeg.historicalbuildings.widget.HistoricalBuildingListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YegHistoricalSitesListView extends ListActivity {
	private ProgressDialog _progressDialog;
	private TextView _foundHistoricalBuildingsTextView;
	private LocationManager _locationManager;
	private List<RelativeBuildingLocation> _relativeBuildings;

	LocationListener _onLocationChange = new LocationListener() {
		public void onLocationChanged(Location location) {
			if (location == null) {
				Log.w(Constants.LOG_TAG, "We have a NULL location for some reason.");
				return;
			}
			Log.d(Constants.LOG_TAG, "new location " + location.getLongitude() + " " + location.getLatitude());
			if (_relativeBuildings != null) {
				for (RelativeBuildingLocation relativeBuilding : _relativeBuildings) {
					relativeBuilding.setRelativeLocation(location);
				}
				Collections.sort(_relativeBuildings);
				displayYegData(_relativeBuildings);
			}
		}

		public void onProviderDisabled(String provider) {
			Log.i(Constants.LOG_TAG, "GPS Provider is disabled");
		}

		public void onProviderEnabled(String provider) {
			Log.i(Constants.LOG_TAG, "GPS Provider is enabled");
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

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
		}
		else if (R.id.refreshData == itemId) {
			loadYegOpenData();
		}
		else if (R.id.showAllOnMap == itemId) {
			Toast.makeText(this, "Map All: Coming in a future version.", Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}

	private void displayYegData(List<RelativeBuildingLocation> relativeBuildings) {
		HistoricalBuildingListAdapter adapter = new HistoricalBuildingListAdapter(this, relativeBuildings);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		RelativeBuildingLocation relativeBuilding = (RelativeBuildingLocation) l.getItemAtPosition(position);
		Intent intent = new Intent(YegHistoricalSitesListView.this, BuildingMap.class);
		GoogleMapPin mapPin = new GoogleMapPin(relativeBuilding.getHistoricalBuilding());
		mapPin.putExtra(intent);

		startActivity(intent);
	}

	private void loadYegOpenData() {
		_progressDialog = ProgressDialog
				.show(YegHistoricalSitesListView.this, "Please wait...", "Retrieving data...", true);
		Location myLocation = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		new HistoricalBuildingFetcher(myLocation).execute();
	}

	private class HistoricalBuildingFetcher extends AsyncTask<Void, Void, List<Building>> {
		private IReadOnlyBuildingRepository _repository = new YegOpenDataHistoricalBuildingRepository();
		private Location _myLocation;

		HistoricalBuildingFetcher(Location myLocation) {
			_myLocation = myLocation;
		}

		@Override
		protected List<Building> doInBackground(Void... voids) {
			List<Building> buildings = _repository.get();

			if ((_myLocation != null)) {
				SortByDistanceFromLocation sorter = new SortByDistanceFromLocation(_myLocation);
				return sorter.sortList(buildings);
			}
			return buildings;
		}

		@Override
		protected void onPostExecute(List<Building> buildings) {
			_progressDialog.dismiss();
			_foundHistoricalBuildingsTextView.setText("Found " + buildings.size() + " buildings.");
			_relativeBuildings = new ArrayList<RelativeBuildingLocation>(buildings.size());

			for (Building building : buildings) {
				_relativeBuildings.add(new RelativeBuildingLocation(building));
			}
			displayYegData(_relativeBuildings);
		}
	}

}
