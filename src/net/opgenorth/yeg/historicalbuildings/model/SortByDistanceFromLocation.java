package net.opgenorth.yeg.historicalbuildings.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortByDistanceFromLocation implements IHistoricalBuildingSorter {
	private Location _location;

	public SortByDistanceFromLocation(Location location) {
		_location = location;
	}

	@Override
	public List<Building> sortList(List<Building> buildings) {
		ArrayList<RelativeBuildingLocation> list = new ArrayList<RelativeBuildingLocation>(buildings.size());
		for (Building building : buildings) {
			list.add(new RelativeBuildingLocation(building, _location));
		}

		Collections.sort(list);

		ArrayList<Building> sortedList = new ArrayList<Building>(buildings.size());
		for (RelativeBuildingLocation relativeBuilding : list) {
			sortedList.add(relativeBuilding.getHistoricalBuilding());
		}
		return sortedList;
	}
}
