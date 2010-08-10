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

        Uri uri = Uri.parse("geo:" + lat + "," + lon);
        intent.setData(uri);
        
    }
}
