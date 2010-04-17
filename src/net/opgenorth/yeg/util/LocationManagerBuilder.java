package net.opgenorth.yeg.util;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import net.opgenorth.yeg.Constants;

public class LocationManagerBuilder {
	public static final int TIME_BETWEEN_GPS_UPDATES = 60000;
	public static final int DISTANCE_BETWEEN_GPS_UPDATES = 100;

	private LocationManager _locationManager;
	private LocationListener _locationListener;

	private LocationManagerBuilder() {
		_locationManager = null;
		_locationListener = null;
	}

	public static LocationManagerBuilder createLocationManager() {
		return new LocationManagerBuilder();
	}

	private Criteria createCriteria() {
		Criteria criteria = new Criteria();
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		return criteria;
	}

	public LocationManagerBuilder with(Activity activity) {
		_locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		return this;
	}

	public LocationManagerBuilder with(LocationManager locationManager) {
		_locationManager = locationManager;
		return this;
	}

	public LocationManagerBuilder listeningWith(LocationListener locationListener) {
		_locationListener = locationListener;
		return this;
	}

	public LocationManager build() {
		if (_locationManager == null) {
			return null;
		}
		if (_locationListener == null) {
			throw new NullPointerException("Must provide a LocationListener.");
		}

		String provider = _locationManager.getBestProvider(createCriteria(), true);
		Log.d(Constants.LOG_TAG, "Using provider " + provider + " for requesting location updates.");
		_locationManager.requestLocationUpdates(provider,
												TIME_BETWEEN_GPS_UPDATES,
												DISTANCE_BETWEEN_GPS_UPDATES,
												_locationListener);

		return _locationManager;
	}
}
