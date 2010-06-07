package net.opgenorth.yeg.historicalbuildings.model;

import android.location.Location;

public class RelativeBuildingLocation implements Comparable<RelativeBuildingLocation> {
	private Building _building;
	private Location _relativeLocation;

	public RelativeBuildingLocation(Building building) {
		_building = building;
		_relativeLocation = null;
	}
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

	public Building getHistoricalBuilding() {
		return _building;
	}
}