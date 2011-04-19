package net.opgenorth.yeg.buildings.model;

import android.content.Intent;
import android.location.Location;
import com.google.android.maps.OverlayItem;
import net.opgenorth.yeg.buildings.Constants;

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
		}
		else if (getDistance() > o.getDistance()) {
			return 1;
		}
		else {
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

		double lat = _building.getLatitude();
		double lon = _building.getLongitude();

		intent.putExtra(Constants.INTENT_BUILDING_NAME_KEY, _building.getName());
		intent.putExtra(Constants.INTENT_BUILDING_ADDRESS_KEY, _building.getAddress());
		intent.putExtra(Constants.INTENT_BUILDING_CONSTRUCTION_DATE_KEY, _building.getConstructionDate());
		intent.putExtra(Constants.INTENT_LATITUDE_KEY, lat);
		intent.putExtra(Constants.INTENT_LONGITUDE_KEY, lon);
	}

	public OverlayItem getOverlayItem() {
//		String snippet = _building.getName() + "\n" + _building.getAddress() + "\n" + _building.getConstructionDate() ;
        String snippet = _building.getId().toString();
		OverlayItem item = new OverlayItem(_building.getGeoPoint(), _building.getName(), snippet);
		return item;
	}

	@Override
	public String toString() {
		return _building.getName() + ":" + _building.getLatitude() + "," + _building.getLongitude();
	}


}
