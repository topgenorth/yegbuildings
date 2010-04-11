package net.opgenorth.yeg.model;

import android.content.Intent;
import android.location.Location;
import com.google.android.maps.GeoPoint;
import net.opgenorth.yeg.widget.GoogleMapPin;

public class LatLongLocation {
	private double _latitude;
	private double _longitude;

	public LatLongLocation(double latitude, double longitude) {
		_latitude = latitude;
		_longitude = longitude;
	}

	public LatLongLocation(Intent intent) {
		_latitude = intent.getDoubleExtra(GoogleMapPin.LATITUDE, 0);
		_longitude = intent.getDoubleExtra(GoogleMapPin.LONGITUDE, 0);
	}

	public double getDistanceTo(Location location) {
		float[] result = new float[3];
		Location.distanceBetween(_latitude, _longitude, location.getLatitude(), location.getLongitude(), result);
		return result[0];
	}

	@Override
	public String toString() {
		return "location : " + _latitude + "," + _longitude;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		LatLongLocation that = (LatLongLocation) o;

		if (Double.compare(that._latitude, _latitude) != 0) {
			return false;
		}
		if (Double.compare(that._longitude, _longitude) != 0) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = _latitude != +0.0d ? Double.doubleToLongBits(_latitude) : 0L;
		result = (int) (temp ^ (temp >>> 32));
		temp = _longitude != +0.0d ? Double.doubleToLongBits(_longitude) : 0L;
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public void putExtra(Intent intent) {
		intent.putExtra(GoogleMapPin.LATITUDE, _latitude);
		intent.putExtra(GoogleMapPin.LONGITUDE, _longitude);
	}

	public GeoPoint getGeoPoint() {
		GeoPoint geopoint = new GeoPoint((int) (_latitude * 1000000.0), (int) (_longitude * 1000000.0));
		return geopoint;
	}
}
