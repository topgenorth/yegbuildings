package net.opgenorth.yeg.widget;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.views.BuildingMap;

public class BuildingLocationOverlay extends ItemizedOverlay<OverlayItem> {
	private OverlayItem _overlayItem = null;
	private Drawable _marker = null;
	private MapActivity _buildingMap;

	public BuildingLocationOverlay(MapActivity buildingMap, GoogleMapPin pin) {
		super(buildingMap.getResources().getDrawable(R.drawable.marker));
		_buildingMap = buildingMap;

		_marker = buildingMap.getResources().getDrawable(R.drawable.marker);
		_marker.setBounds(0, 0, _marker.getIntrinsicWidth(), _marker.getIntrinsicHeight());
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
		Toast.makeText(_buildingMap, _overlayItem.getSnippet(), Toast.LENGTH_SHORT).show();
		return (true);
	}


	@Override
	public int size() {
		return (1);
	}

}
