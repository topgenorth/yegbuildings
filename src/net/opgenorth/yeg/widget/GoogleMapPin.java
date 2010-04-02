package net.opgenorth.yeg.widget;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import net.opgenorth.yeg.model.HistoricalBuilding;
import net.opgenorth.yeg.model.LatLongLocation;

public class GoogleMapPin {

	public static final String BUILDING_NAME = "net.opgenorth.yeg.building_name";
	private LatLongLocation _location;
	private String _name;
	private Drawable _marker;

	public static final String LATITUDE = "net.opgenorth.yeg.latitude";
	public static final String LONGITUDE = "net.opgenorth.yet.longitude";

	public GoogleMapPin(HistoricalBuilding building) {
		this(building, null);
	}

	public GoogleMapPin(Intent intent, Drawable marker) {
		_location = new LatLongLocation(intent);
		_name = intent.getStringExtra(BUILDING_NAME);
		_marker = marker;
	}

	public GoogleMapPin(HistoricalBuilding building, Drawable marker) {
		_location = building.getLocation();
		_name = building.getName();
		_marker = marker;
	}

	public void putExtra(Intent intent) {
		intent.putExtra(BUILDING_NAME, _name);
		_location.putExtra(intent);
	}

	@Override
	public String toString() {
		return "GoogleMapPin{" +
			   "_location=" + _location +
			   ", _name='" + _name + '\'' +
			   '}';
	}

	public Drawable getMarker() {
		return _marker;
	}

	public String getBuildingName() {
		return _name;
	}

	public GeoPoint getGeoPoint() {
		return _location.createGeoPoint();
	}
}
