package net.opgenorth.yeg.buildings.model;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;

/**
 * This class is a decorator around a building to help with calculating the distance from a
 * given point and to help with sorting a list of buildings.
 */
public class RelativeBuildingLocation implements Comparable<RelativeBuildingLocation> {
    private Building _building;
    private Location _relativeLocation;
    public static final String LATITUDE = "net.opgenorth.yeg.latitude";
    public static final String LONGITUDE = "net.opgenorth.yeg.longitude";
    public static final String BUILDING_ADDRESS = "net.opgenorth.yeg.building_address";
    public static final String BUILDING_NAME = "net.opgenorth.yeg.building_name";
    public static final String BUILDING_CONSTRUCTION_DATE = "net.opgenorth.yeg.building_construction_date";

    public RelativeBuildingLocation(Building building, Location location) {
        _building = building;
        _relativeLocation = location;
    }

    public void setRelativeLocation(Location location) {
        _relativeLocation = location;
    }

    public double getDistance() {

        if (_relativeLocation == null) {
            return 0;
        }
        return _building.getLocation().getDistanceTo(_relativeLocation);
    }

    @Override
    public int compareTo(RelativeBuildingLocation o) {
        if (getDistance() == o.getDistance()) {
            return 0;
        } else if (getDistance() > o.getDistance()) {
            return 1;
        } else {
            return -1;
        }
    }

    public Building getBuilding() {
        return _building;
    }

    public void addTo(Intent intent) {
        if (_building == null) {
            throw new NullPointerException("Don't have a building to set the location for.");
        }

        double lat = _building.getLocation().getLatitude();
        double lon = _building.getLocation().getLongitude();

        intent.putExtra(BUILDING_NAME, _building.getName());
        intent.putExtra(BUILDING_ADDRESS, _building.getAddress());
        intent.putExtra(BUILDING_CONSTRUCTION_DATE, _building.getConstructionDate());
        intent.putExtra(LATITUDE, lat);
        intent.putExtra(LONGITUDE, lon);
/*
        This is if we want to use the mapview

        Uri uri = Uri.parse("geo:" + lat + "," + lon);
        intent.setData(uri);
*/

    }
}
