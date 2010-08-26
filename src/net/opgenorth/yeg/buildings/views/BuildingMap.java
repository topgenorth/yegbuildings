package net.opgenorth.yeg.buildings.views;

import android.content.Intent;
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
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.model.LatLongLocation;
import net.opgenorth.yeg.buildings.model.RelativeBuildingLocation;
import net.opgenorth.yeg.buildings.util.LocationManagerBuilder;

public class BuildingMap extends MapActivity implements IBuildingMapView {
	private MapView _edmontonMapView;
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
        putPinOnMap();
	}

    private void putPinOnMap() {
        Intent intent = getIntent();
        LatLongLocation location = new LatLongLocation(intent);
        String name = intent.getStringExtra(RelativeBuildingLocation.BUILDING_NAME);
        String address = intent.getStringExtra(RelativeBuildingLocation.BUILDING_ADDRESS);
        String constructionDate = "Construction Date: " +  intent.getStringExtra(RelativeBuildingLocation.BUILDING_CONSTRUCTION_DATE);
        GeoPoint buildingLocation = location.getGeoPoint();

        setName(name);
        setAddress(address);
        setConstructionDate(constructionDate);
        setCenter(buildingLocation);

        // This will draw the pin on the map.
		Drawable buildingMarker = getResources().getDrawable(R.drawable.marker);
		buildingMarker.setBounds(0, 0, buildingMarker.getIntrinsicWidth(), buildingMarker.getIntrinsicHeight());
		ItemizedOverlay<OverlayItem> buildingOverlay = new BuildingLocationOverlay(buildingMarker, buildingLocation, name);
		_edmontonMapView.getOverlays().add(buildingOverlay);
    }

    private void initializeContentView() {
        ActivityHelper helper = new ActivityHelper(this);

        if (helper.isDebug()) {
            Log.d(Constants.LOG_TAG, "Debuggable == TRUE, using building_map_debug.");
            setContentView(R.layout.building_map_debug);
        }
        else {
            Log.d(Constants.LOG_TAG, "Debuggable == FALSE, using building_map_production.");
            setContentView(R.layout.building_map_production);
        }
    }



	private void initializeMyLocation() {
		_myLocationOverlay = new MyLocationOverlay(this, _edmontonMapView);
		_myLocationOverlay.enableMyLocation();
		_edmontonMapView.getOverlays().add(_myLocationOverlay);
		updateMyLocationOnMap();
	}

	private void initializeMap() {
		_edmontonMapView = (MapView) findViewById(R.id.map);
		_edmontonMapView.getController().setZoom(17);
        _edmontonMapView.setSatellite(true);
		_edmontonMapView.setBuiltInZoomControls(true);
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
			_edmontonMapView.invalidate();
			_myLocationOverlay.enableMyLocation();
			_edmontonMapView.getController().animateTo(myLocation);
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
		_edmontonMapView.getController().setCenter(geoPoint);
	}



	private class BuildingLocationOverlay extends ItemizedOverlay<OverlayItem> {
		private OverlayItem _overlayItem = null;
		private Drawable _marker = null;

		public BuildingLocationOverlay(Drawable marker, GeoPoint buildingLocation, String buildingName) {
			super(marker);
			_marker = marker;
			_overlayItem = new OverlayItem(buildingLocation, "Building", buildingName);
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

