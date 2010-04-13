package net.opgenorth.yeg.model;

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
	public List<HistoricalBuilding> sortList(List<HistoricalBuilding> buildings) {
		ArrayList<BuildingAndLocationWrapper> list = new ArrayList<BuildingAndLocationWrapper>(buildings.size());
		for (HistoricalBuilding building : buildings) {
			list.add(new BuildingAndLocationWrapper(building, _location));
		}

		Collections.sort(list);

		ArrayList<HistoricalBuilding> sortedList = new ArrayList<HistoricalBuilding>(buildings.size());
		for (BuildingAndLocationWrapper building : list) {
			sortedList.add(building.getHistoricalBuilding());
		}
		return sortedList;
	}
}
