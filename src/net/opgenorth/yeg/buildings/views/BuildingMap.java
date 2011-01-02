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
import java.util.List;

public class BuildingMap extends MapActivity implements LocationListener {
	private LocationManager   _locationManager;
	private MapView           _map;
	private MyLocationOverlay _myLocationOverlay;
	private ArrayList<RelativeBuildingLocation> _buildingList   = new ArrayList<RelativeBuildingLocation>(76);
	private ActivityHelper                      _activityHelper = new ActivityHelper(this);

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeContentView();
		initializeMapView();
		getBuildingList();
		initializeHistoricalBuildingsOverlay();
		initializeMyLocationOverlay();
	}

	private void initializeContentView() {
		if (_activityHelper.isDebug()) {
			Log.v(Constants.LOG_TAG, "Debuggable == TRUE, using mapofallbuildings_debug.xml.");
			setContentView(R.layout.mapofallbuildings_debug);
		}
		else {
			Log.v(Constants.LOG_TAG, "Debuggable == FALSE, using mapofallbuildings_production.xml.");
			setContentView(R.layout.mapofallbuildings_production);
		}
		_map = (MapView) findViewById(R.id.mapofallbuildings);

	}

	@Override
	protected void onStop() {

		_locationManager.removeUpdates(this);
		_myLocationOverlay.disableMyLocation();

		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor settingsEditor = settings.edit();
		settingsEditor.putInt(Constants.PREF_LAST_MAP_ZOOM, _map.getZoomLevel());

		// this should get the point that is in the middle of the map.
		GeoPoint p = _map.getProjection().fromPixels(_map.getWidth() / 2, _map.getHeight() / 2);
		settingsEditor.putInt(Constants.PREF_LAST_LAT, p.getLatitudeE6());
		settingsEditor.putInt(Constants.PREF_LAST_LON, p.getLongitudeE6());

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
		_myLocationOverlay.enableCompass();
	}

	@Override
	protected void onPause() {
		super.onPause();
		_locationManager.removeUpdates(this);
		_myLocationOverlay.disableMyLocation();
		_myLocationOverlay.disableCompass();
	}

	private void initializeMapView() {
		SharedPreferences settings = getPreferences(MODE_PRIVATE);

		int latitude = settings.getInt(Constants.PREF_LAST_LAT, Constants.PREF_DEFAULT_LAT);
		int longitude = settings.getInt(Constants.PREF_LAST_LON, Constants.PREF_DEFAULT_LON);
		GeoPoint centerOfMap = new GeoPoint(latitude, longitude);
		_map.getController().animateTo(centerOfMap);

		int mapZoom = settings.getInt(Constants.PREF_LAST_MAP_ZOOM, Constants.PREF_DEFAULT_MAP_ZOOM);
		_map.getController().setZoom(mapZoom);
		_map.setClickable(true);
		_map.setLongClickable(true);
		_map.setBuiltInZoomControls(true);
	}

	private void initializeMyLocationOverlay() {
		_myLocationOverlay = new MyLocationOverlay(this, _map);
		_myLocationOverlay.enableMyLocation();
		_map.getOverlays().add(_myLocationOverlay);
	}

	private void initializeHistoricalBuildingsOverlay() {
		Drawable buildingMarker = getResources().getDrawable(R.drawable.marker);
		buildingMarker.setBounds(0, 0, buildingMarker.getIntrinsicWidth(), buildingMarker.getIntrinsicHeight());
		_map.getOverlays().add(new BuildingsOverlay(buildingMarker));
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onStatusChanged(String s, int i, Bundle bundle) {
		Log.v(Constants.LOG_TAG, "GPS Provider status changed.");
	}

	@Override
	public void onProviderEnabled(String s) {
		Log.v(Constants.LOG_TAG, "GPS Provider is enabled.");
	}

	@Override
	public void onProviderDisabled(String s) {
		Log.v(Constants.LOG_TAG, "GPS Provider is disabled.");
	}


	private void getBuildingList() {
		// TODO: duplication with BuildingList.java
		IBuildingDataService svc = new SQLiteBuildingDataService(this);
		for (Building building : svc.fetchAll()) {
			_buildingList.add(new RelativeBuildingLocation(building, null));
		}
	}

	private class BuildingsOverlay extends ItemizedOverlay<OverlayItem> {
		private Drawable _marker;
		private List<OverlayItem> _items = new ArrayList<OverlayItem>();

		public BuildingsOverlay(Drawable drawable) {
			super(drawable);
			_marker = drawable;
			boundCenterBottom(drawable);
			loadItemsFromBuildings();
//			sample();
			populate();
		}

		private GeoPoint getPoint(double lat, double lon) {
			return (new GeoPoint((int) (lat * 1000000.0),
					(int) (lon * 1000000.0)));
		}

		private void sample() {
			_items.add(new OverlayItem(getPoint(40.748963847316034,
					-73.96807193756104),
					"UN", "United Nations"));
			_items.add(new OverlayItem(getPoint(40.76866299974387,
					-73.98268461227417),
					"Lincoln Center",
					"Home of Jazz at Lincoln Center"));
			_items.add(new OverlayItem(getPoint(40.765136435316755,
					-73.97989511489868),
					"Carnegie Hall",
					"Where you go with practice, practice, practice"));
			_items.add(new OverlayItem(getPoint(40.70686417491799,
					-74.01572942733765),
					"The Downtown Club",
					"Original home of the Heisman Trophy"));
		}

		private void loadItemsFromBuildings() {
			for (RelativeBuildingLocation location : _buildingList) {
				OverlayItem overlay = location.getOverlayItem();
				Log.d(Constants.LOG_TAG, "Adding location " + location.toString());
				_items.add(overlay);
			}
		}

		@Override
		protected OverlayItem createItem(int i) {
			return _items.get(i);
		}

		@Override
		public int size() {
			return _items.size();
		}
	}
}