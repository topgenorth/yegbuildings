package net.opgenorth.yeg.buildings.data;

import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.util.ITransmorgifier;

import java.util.ArrayList;
import java.util.List;

public class HistoricalBuildingsCSVToBuildings implements ITransmorgifier<String, List<Building>> {
	@Override
	public List<Building> transmorgify(String source) {
		String[] lines = source.split("\n");
		ArrayList<Building> buildings = new ArrayList<Building>(lines.length);

		for (String line : lines) {
			Building building= createBuilding(line);
			buildings.add(building);
		}


		return buildings;
	}

	public Building createBuilding(String csvLine) {
		String[] parts= csvLine.split(",");
		Building building = new Building();

		return building;

	}
}
