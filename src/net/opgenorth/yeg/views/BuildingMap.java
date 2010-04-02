package net.opgenorth.yeg.views;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.maps.*;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.widget.GoogleMapPin;


public class BuildingMap extends MapActivity implements IBuildingMapView {
	private MyLocationOverlay _myLocation;
	private MapView _map;
	private TextView _buildingNameLabel;
	private TextView _buildingAddressLabel;
	private TextView _buildingConstructionDateLabel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.building_map);

		initializeMap();

		GoogleMapPin pin = new GoogleMapPin(getIntent());
		pin.putOnMap(this);
	}

	private void initializeMap() {
		_map = (MapView) findViewById(R.id.map);
		_map.getController().setZoom(17);
		_map.setBuiltInZoomControls(true);
		_myLocation = new MyLocationOverlay(this, _map);
		_map.getOverlays().add(_myLocation);
	}

	@Override
	protected void onResume() {
		super.onResume();
		_myLocation.enableCompass();
	}

	@Override
	protected void onPause() {
		super.onPause();
		_myLocation.disableCompass();
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
		_map.getController().setCenter(geoPoint);
	}

	@Override
	public void showBuildingOnMap(GoogleMapPin pin) {
		Drawable buildingMarker = getResources().getDrawable(R.drawable.marker);
		buildingMarker.setBounds(0, 0, buildingMarker.getIntrinsicWidth(), buildingMarker.getIntrinsicHeight());

		ItemizedOverlay<OverlayItem> buildingOverlay = new BuildingLocationOverlay(buildingMarker, pin);
		_map.getOverlays().add(buildingOverlay);

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
}


