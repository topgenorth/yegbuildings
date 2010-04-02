package net.opgenorth.yeg.views;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.maps.*;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.widget.GoogleMapPin;


public class BuildingMap extends MapActivity {
	private MyLocationOverlay _myLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.building_map);

		MapView map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(17);
		map.setBuiltInZoomControls(true);
		_myLocation = new MyLocationOverlay(this, map);

		Drawable buildingMarker = getResources().getDrawable(R.drawable.marker);
		buildingMarker.setBounds(0, 0, buildingMarker.getIntrinsicWidth(), buildingMarker.getIntrinsicHeight());

		GoogleMapPin pin = new GoogleMapPin(getIntent(), buildingMarker);
		map.getController().setCenter(pin.getGeoPoint());

		map.getOverlays().add(_myLocation);
		map.getOverlays().add(new StatusOverlay(pin));
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

	private class StatusOverlay extends ItemizedOverlay<OverlayItem> {
		private OverlayItem _overlayItem = null;
		private Drawable _marker = null;

		public StatusOverlay(GoogleMapPin pin) {
			super(pin.getMarker());
			_marker = pin.getMarker();
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


