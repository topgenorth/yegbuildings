package net.opgenorth.yeg.buildings.views;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.data.IBuildingDataService;
import net.opgenorth.yeg.buildings.data.SQLiteBuildingDataService;

public class BuildingMap extends MapActivity implements LocationListener {
	private ActivityHelper       _activityHelper      = new ActivityHelper(this);
	private IBuildingDataService _buildingDataService = new SQLiteBuildingDataService(this);
	private MapView _map;
	private Overlay _historicalBuildingsOverlay;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeContentView();
	}

	private void initializeContentView() {
		initializeMapView();
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