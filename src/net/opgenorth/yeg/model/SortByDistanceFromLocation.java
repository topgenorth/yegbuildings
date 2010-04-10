package net.opgenorth.yeg.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortByDistanceFromLocation implements IHistoricalBuildingSorter {
	private Location _location;

	public SortByDistanceFromLocation(Location location) {_location = location;}

	@Override
	public List<HistoricalBuilding> sortList(List<HistoricalBuilding> buildings) {
		ArrayList<DistanceToBuilding> list = new ArrayList<DistanceToBuilding>(buildings.size());
		for (HistoricalBuilding building : buildings) {
			DistanceToBuilding x = new DistanceToBuilding(building, _location);
			list.add(x);
		}

		Collections.sort(list);

		ArrayList<HistoricalBuilding> sortedList = new ArrayList<HistoricalBuilding>(buildings.size());
		for (DistanceToBuilding building : list) {
			sortedList.add(building.getHistoricalBuilding());
		}
		return sortedList;
	}

	private class DistanceToBuilding implements Comparable<DistanceToBuilding> {
		private HistoricalBuilding _historicalBuilding;
		private double _distanceToReferenceLocation;

		public DistanceToBuilding(HistoricalBuilding historicalBuilding, Location location) {
			_historicalBuilding = historicalBuilding;
			_distanceToReferenceLocation = historicalBuilding.getLocation().getDistanceTo(location);
		}

		public HistoricalBuilding getHistoricalBuilding() {
			return _historicalBuilding;
		}

		public double getDistance() {
			return _distanceToReferenceLocation;
		}

		@Override
		public int compareTo(DistanceToBuilding o) {
			if (_distanceToReferenceLocation == o.getDistance()) {
				return 0;
			}
			else if (_distanceToReferenceLocation > o.getDistance()) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}

}
