package net.opgenorth.yeg.model;

import android.location.Location;

public class BuildingAndLocationWrapper implements Comparable<BuildingAndLocationWrapper> {
	private HistoricalBuilding _building;
	private Location _relativeLocation;

	public BuildingAndLocationWrapper(HistoricalBuilding building) {
		_building = building;
		_relativeLocation = null;
	}
	public BuildingAndLocationWrapper(HistoricalBuilding building, Location location) {
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
	public int compareTo(BuildingAndLocationWrapper o) {
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

	public HistoricalBuilding getHistoricalBuilding() {
		return _building;
	}
}
