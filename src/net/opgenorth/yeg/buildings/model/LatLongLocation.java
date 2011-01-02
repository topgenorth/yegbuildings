package net.opgenorth.yeg.buildings.model;

import android.content.Intent;
import android.location.Location;
import com.google.android.maps.GeoPoint;
import net.opgenorth.yeg.buildings.Constants;

/**
 * A wrapper that helps with moving the location around via Intents.
 */
public class LatLongLocation {
	private double _latitude;
	private double _longitude;

	public LatLongLocation(double latitude, double longitude) {
		_latitude = latitude;
		_longitude = longitude;
	}

	public LatLongLocation(Intent intent) {
		_latitude = intent.getDoubleExtra(Constants.INTENT_LATITUDE_KEY, 0);
		_longitude = intent.getDoubleExtra(Constants.INTENT_LONGITUDE_KEY, 0);
	}

	public double getDistanceTo(Location location) {

		Location thisLocation = new Location("me");
		thisLocation.setLatitude(_latitude);
		thisLocation.setLongitude(_longitude);

		int distance = (int) thisLocation.distanceTo(location);

		return distance;

	}

	@Override
	public String toString() {
		return "location : " + _longitude + "," + _latitude;
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
		intent.putExtra(Constants.INTENT_LATITUDE_KEY, _latitude);
		intent.putExtra(Constants.INTENT_LONGITUDE_KEY, _longitude);
	}

	public GeoPoint getGeoPoint() {
		GeoPoint geopoint = new GeoPoint((int) (_latitude * 1000000.0), (int) (_longitude * 1000000.0));
		return geopoint;
	}

	public double getLatitude() {
		return _latitude;
	}

	public double getLongitude() {
		return _longitude;
	}
}
