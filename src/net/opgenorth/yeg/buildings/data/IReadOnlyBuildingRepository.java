package net.opgenorth.yeg.buildings.data;

import net.opgenorth.yeg.buildings.model.Building;

import java.util.List;

public interface IReadOnlyBuildingRepository {
	List<Building> fetch();
}
