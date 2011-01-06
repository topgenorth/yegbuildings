package net.opgenorth.yeg.buildings.data;

import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.util.ITransmorgifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HistoricalBuildingsCSVToBuildings implements ITransmorgifier<String, List<Building>> {
	@Override
	public List<Building> transmorgify(String source) {
		String[] lines = source.split("\n");
		ArrayList<Building> buildings = new ArrayList<Building>(lines.length);

		// skip the first line, as that is the field names.
		for (int i = 1; i < lines.length; i++) {
			String line = lines[i];
			Building building= createBuilding(line);
			buildings.add(building);
		}

		return buildings;
	}

	public Building createBuilding(String csvLine) {
		String[] parts= csvLine.split(",");
		Building building = new Building();
		building.setRowKey(UUID.fromString(parts[0]));
		building.setName(parts[1]);
		building.setAddress(parts[2]);
		building.setNeighbourHood(parts[3]);
		building.setUrl(parts[4]);
		building.setConstructionDate(parts[5]);

		double lat = Double.parseDouble(parts[6]);
		double lon = Double.parseDouble(parts[7]);
		building.setLocation(lat, lon);
		return building;
	}
}
