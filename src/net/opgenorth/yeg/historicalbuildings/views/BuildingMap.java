package net.opgenorth.yeg.historicalbuildings.views;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.maps.*;
import net.opgenorth.yeg.historicalbuildings.Constants;
import net.opgenorth.yeg.historicalbuildings.R;
import net.opgenorth.yeg.historicalbuildings.util.LocationManagerBuilder;
import net.opgenorth.yeg.historicalbuildings.widget.GoogleMapPin;

public class BuildingMap extends MapActivity implements IBuildingMapView {
	private MapView _edmontonMap;
	private TextView _buildingNameLabel;
	private TextView _buildingAddressLabel;
	private TextView _buildingConstructionDateLabel;
	private LocationManager _locationManager;
	private MyLocationOverlay _myLocationOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeContentView();

		_locationManager = LocationManagerBuilder.createLocationManager()
				.with(this)
				.listeningWith(_onLocationChange)
				.build();

		initializeMap();
		initializeMyLocation();

		GoogleMapPin pin = new GoogleMapPin(getIntent());
		pin.putOnMap(this);
	}

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

	private void initializeContentView() {
		if (isDebug()) {
			setContentView(R.layout.building_map_debug);
		}
		else {
			setContentView(R.layout.building_map_production);
		}
	}

	private void initializeMyLocation() {
		_myLocationOverlay = new MyLocationOverlay(this, _edmontonMap);
		_myLocationOverlay.enableMyLocation();
		_edmontonMap.getOverlays().add(_myLocationOverlay);
		updateMyLocationOnMap();
	}

	private void initializeMap() {
		_edmontonMap = (MapView) findViewById(R.id.map);
		_edmontonMap.getController().setZoom(17);
		_edmontonMap.setBuiltInZoomControls(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(getApplication()).inflate(R.menu.buildingmap_menu, menu);
		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (R.id.buildingmap_showMyLocation == itemId) {
			updateMyLocationOnMap();
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateMyLocationOnMap() {
		GeoPoint myLocation = _myLocationOverlay.getMyLocation();
		if (myLocation == null) {
			Toast.makeText(this, "Can't seem to figure out your location.", Toast.LENGTH_SHORT);
			Log.i(Constants.LOG_TAG, "Can't figure out the user's location.");
		}
		else {
			_edmontonMap.invalidate();
			_myLocationOverlay.enableMyLocation();
			_edmontonMap.getController().animateTo(myLocation);
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		_myLocationOverlay.disableMyLocation();
		_locationManager.removeUpdates(_onLocationChange);
	}

	@Override
	protected void onResume() {
		super.onResume();
		_myLocationOverlay.enableMyLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		_myLocationOverlay.disableMyLocation();
		_myLocationOverlay.disableMyLocation();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public TextView getNameLabel() {
		if (_buildingNameLabel == null) {
			_buildingNameLabel = (TextView) findViewById(R.id.building_map_name);
		}
		return _buildingNameLabel;
	}

	@Override
	public TextView getAddressLabel() {
		if (_buildingAddressLabel == null) {
			_buildingAddressLabel = (TextView) findViewById(R.id.building_map_address);
		}
		return _buildingAddressLabel;
	}

	@Override
	public TextView getConstructionDateLabel() {
		if (_buildingConstructionDateLabel == null) {
			_buildingConstructionDateLabel = (TextView) findViewById(R.id.building_map_construction_date);
		}
		return _buildingConstructionDateLabel;
	}

	@Override
	public void setName(String name) {
		getNameLabel().setText(name);
	}

	@Override
	public void setAddress(String address) {
		getAddressLabel().setText(address);
	}

	@Override
	public void setConstructionDate(String constructionDate) {
		getConstructionDateLabel().setText(constructionDate);
	}

	@Override
	public void setCenter(GeoPoint geoPoint) {
		_edmontonMap.getController().setCenter(geoPoint);
	}

	@Override
	public void showBuildingOnMap(GoogleMapPin pin) {
		Drawable buildingMarker = getResources().getDrawable(R.drawable.marker);
		buildingMarker.setBounds(0, 0, buildingMarker.getIntrinsicWidth(), buildingMarker.getIntrinsicHeight());

		ItemizedOverlay<OverlayItem> buildingOverlay = new BuildingLocationOverlay(buildingMarker, pin);
		_edmontonMap.getOverlays().add(buildingOverlay);
	}

	private class BuildingLocationOverlay extends ItemizedOverlay<OverlayItem> {
		private OverlayItem _overlayItem = null;
		private Drawable _marker = null;

		public BuildingLocationOverlay(Drawable marker, GoogleMapPin pin) {
			super(marker);
			_marker = marker;
			_overlayItem = new OverlayItem(pin.getGeoPoint(), "Building", pin.getBuildingName());
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return (_overlayItem);
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			boundCenterBottom(_marker);
		}

		@Override
		protected boolean onTap(int i) {
			Toast.makeText(BuildingMap.this, _overlayItem.getSnippet(), Toast.LENGTH_SHORT).show();
			return (true);
		}

		@Override
		public int size() {
			return (1);
		}

	}

	private LocationListener _onLocationChange = new LocationListener() {
		public void onLocationChanged(Location location) {
			// required for interface, not used
		}

		public void onProviderDisabled(String provider) {
			// required for interface, not used
		}

		public void onProviderEnabled(String provider) {
			// required for interface, not used
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// required for interface, not used
		}
	};
}


