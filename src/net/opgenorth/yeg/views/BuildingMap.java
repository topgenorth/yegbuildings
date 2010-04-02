package net.opgenorth.yeg.views;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.model.LatLongLocation;
import net.opgenorth.yeg.widget.GoogleMapPin;


public class BuildingMap extends MapActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.building_map);
		MapView map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(17);

		LatLongLocation location = new LatLongLocation(getIntent());

		map.getController().setCenter(location.createGeoPoint());
		map.setBuiltInZoomControls(true);

		Drawable marker = getResources().getDrawable(R.drawable.marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());

		GoogleMapPin pin = new GoogleMapPin(getIntent(), marker);
		map.getOverlays().add(new StatusOverlay(pin));
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


