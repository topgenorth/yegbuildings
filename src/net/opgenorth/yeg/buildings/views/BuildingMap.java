package net.opgenorth.yeg.buildings.views;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.google.android.maps.*;
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.data.IBuildingDataService;
import net.opgenorth.yeg.buildings.data.SQLiteBuildingDataService;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.RelativeBuildingLocation;
import net.opgenorth.yeg.buildings.util.LocationManagerBuilder;

import java.util.ArrayList;

public class BuildingMap extends MapActivity implements LocationListener {
	private ArrayList<RelativeBuildingLocation> _buildingList = new ArrayList<RelativeBuildingLocation>(76);
	private MapView _map;
	private MyLocationOverlay _myLocationOverlay;
	private Overlay _historicalBuildingsOverlay;
	private ActivityHelper       _activityHelper      = new ActivityHelper(this);
	private IBuildingDataService _buildingDataService = new SQLiteBuildingDataService(this);
	private LocationManager _locationManager;
	private Location        _currentLocation;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeContentView();
	}

	private void initializeContentView() {
		initializeMapView();
	}

	@Override
	protected void onStop() {

		_locationManager.removeUpdates(this);
		_myLocationOverlay.disableMyLocation();

		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor settingsEditor = settings.edit();
		settingsEditor.putInt(Constants.LAST_MAP_ZOOM, _map.getZoomLevel());

		// this should get the point that is in the middle of the map.
		GeoPoint p = _map.getProjection().fromPixels(_map.getWidth() / 2, _map.getHeight() / 2);
		settingsEditor.putInt(Constants.LAST_LAT, p.getLatitudeE6());
		settingsEditor.putInt(Constants.LAST_LON, p.getLongitudeE6());

		settingsEditor.commit();

		super.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
		_locationManager = LocationManagerBuilder.createLocationManager()
				.with(this)
				.listeningWith(this)
				.build();
		_myLocationOverlay.enableMyLocation();
	}

	@Override
	protected void onResume() {
		super.onResume();
		_locationManager = LocationManagerBuilder.createLocationManager()
				.with(this)
				.listeningWith(this)
				.build();
		_myLocationOverlay.enableMyLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		_locationManager.removeUpdates(this);
		_myLocationOverlay.disableMyLocation();
	}

	private void initializeMapView() {
		if (_activityHelper.isDebug()) {
			Log.d(Constants.LOG_TAG, "Debuggable == TRUE, using mapofallbuildings_debug.xml.");
			setContentView(R.layout.mapofallbuildings_debug);
		}
		else {
			Log.d(Constants.LOG_TAG, "Debuggable == FALSE, using mapofallbuildings_production.xml.");
			setContentView(R.layout.mapofallbuildings_production);
		}
		_map = (MapView) findViewById(R.id.mapofallbuildings);
		_map.invalidate();

		_myLocationOverlay = new MyLocationOverlay(this, _map);
		_myLocationOverlay.enableMyLocation();
		_map.getOverlays().add(_myLocationOverlay);

		_map.setClickable(true);
		_map.setLongClickable(true);
		_map.setBuiltInZoomControls(true);

		SharedPreferences settings = getPreferences(MODE_PRIVATE);

		int latitude = settings.getInt(Constants.LAST_LAT, Constants.DEFAULT_LAT);
		int longitude = settings.getInt(Constants.LAST_LON, Constants.DEFAULT_LON);
		GeoPoint centerOfMap = new GeoPoint(latitude, longitude);
		_map.getController().animateTo(centerOfMap);

		int mapZoom = settings.getInt(Constants.LAST_MAP_ZOOM, Constants.DEFAULT_MAP_ZOOM);
		_map.getController().setZoom(mapZoom);

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		_currentLocation = location;
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

	/**
	 * Overlay to show the historical buildings.
	 */
	private class HistoricalBuildingsOverlap extends ItemizedOverlay<OverlayItem> {

		public HistoricalBuildingsOverlap(Drawable drawable) {
			super(drawable);
		}

		@Override
		protected OverlayItem createItem(int i) {
			Building building = _buildingList.get(i).getBuilding();
			OverlayItem buildingOverlayItem =  new OverlayItem(building.getGeoPoint(), building.getName(), building.getAddress());
			return buildingOverlayItem;
		}

		@Override
		public int size() {
			return _buildingList.size();
		}
	}

}