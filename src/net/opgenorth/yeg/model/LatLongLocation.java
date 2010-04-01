package net.opgenorth.yeg.model;

import android.content.Intent;
import com.google.android.maps.GeoPoint;

public class LatLongLocation {
    private double _latitude;
    private double _longitude;
    public static final String LATITUDE = "net.opgenorth.yeg.latitude";
    public static final String LONGITUDE = "net.opgenorth.yet.longitude";

    public LatLongLocation(double latitude, double longitude) {
        _latitude = latitude;
        _longitude = longitude;
    }

    public LatLongLocation(Intent intent) {
        _latitude = intent.getDoubleExtra(LATITUDE, 0);
        _longitude = intent.getDoubleExtra(LONGITUDE, 0);
    }

    @Override
    public String toString() {
        return "LatLongLocation{" +
                "lat=" + _latitude +
                ", long=" + _longitude +
                '}';
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

    public void addTo(Intent intent) {
        intent.putExtra(LATITUDE, _latitude);
        intent.putExtra(LONGITUDE, _longitude);
    }

    public GeoPoint createGeoPoint() {
        GeoPoint geopoint = new GeoPoint((int) (_latitude * 1000000.0), (int) (_longitude * 1000000.0));
        return geopoint;
    }
}
