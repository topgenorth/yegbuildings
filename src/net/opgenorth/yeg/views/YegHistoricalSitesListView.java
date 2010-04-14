package net.opgenorth.yeg.views;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import net.opgenorth.yeg.Constants;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.model.BuildingAndLocationWrapper;
import net.opgenorth.yeg.model.HistoricalBuilding;
import net.opgenorth.yeg.model.SortByDistanceFromLocation;
import net.opgenorth.yeg.util.IHistoricalBuildingsRepository;
import net.opgenorth.yeg.util.LocationManagerBuilder;
import net.opgenorth.yeg.util.YegOpenDataHistoricalBuildingRepository;
import net.opgenorth.yeg.widget.GoogleMapPin;
import net.opgenorth.yeg.widget.HistoricalBuildingListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YegHistoricalSitesListView extends ListActivity {
	private ProgressDialog _progressDialog;
	private TextView _foundHistoricalBuildingsTextView;
	private LocationManager _locationManager;
	private List<BuildingAndLocationWrapper> _buildings;

	LocationListener _onLocationChange = new LocationListener() {
		public void onLocationChanged(Location location) {
			Log.d(Constants.LOG_TAG, "new location " + location.getLongitude() + " " + location.getLatitude());
			for (BuildingAndLocationWrapper building : _buildings) {
				building.setRelativeLocation(location);
			}

			Collections.sort(_buildings);
			displayYegData(_buildings);
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

	private boolean isDebug() {
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE;
		}
		catch (PackageManager.NameNotFoundException e) {
			Log.e(Constants.LOG_TAG, "package name not found", e);
		}
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		_foundHistoricalBuildingsTextView = (TextView) findViewById(R.id.info);

		if (isDebug()) {

		}

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

	private void displayYegData(List<BuildingAndLocationWrapper> buildings) {
		HistoricalBuildingListAdapter adapter = new HistoricalBuildingListAdapter(this, buildings);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		BuildingAndLocationWrapper building = (BuildingAndLocationWrapper) l.getItemAtPosition(position);
		Intent intent = new Intent(YegHistoricalSitesListView.this, BuildingMap.class);
		GoogleMapPin mapPin = new GoogleMapPin(building.getHistoricalBuilding());
		mapPin.putExtra(intent);

		startActivity(intent);
	}

	private void loadYegOpenData() {
		_progressDialog = ProgressDialog
				.show(YegHistoricalSitesListView.this, "Please wait...", "Retrieving data...", true);
		Location myLocation = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		new HistoricalBuildingFetcher(myLocation).execute();
	}

	private class HistoricalBuildingFetcher extends AsyncTask<Void, Void, List<HistoricalBuilding>> {
		private IHistoricalBuildingsRepository _repository = new YegOpenDataHistoricalBuildingRepository();
		private Location _myLocation;

		HistoricalBuildingFetcher(Location myLocation) {
			_myLocation = myLocation;
		}

		@Override
		protected List<HistoricalBuilding> doInBackground(Void... voids) {
			List<HistoricalBuilding> buildings = _repository.get();

			if ((_myLocation != null)) {
				SortByDistanceFromLocation sorter = new SortByDistanceFromLocation(_myLocation);
				return sorter.sortList(buildings);
			}
			return buildings;
		}

		@Override
		protected void onPostExecute(List<HistoricalBuilding> historicalBuildings) {
			_progressDialog.dismiss();
			_foundHistoricalBuildingsTextView.setText("Found " + historicalBuildings.size() + " buildings.");
			_buildings = new ArrayList<BuildingAndLocationWrapper>(historicalBuildings.size());

			for (HistoricalBuilding building : historicalBuildings) {
				_buildings.add(new BuildingAndLocationWrapper(building));
			}
			displayYegData(_buildings);
		}
	}

}
