package net.opgenorth.yeg.historicalbuildings.data;

import net.opgenorth.yeg.historicalbuildings.model.Building;

import java.util.List;

public interface IReadOnlyBuildingRepository {
	List<Building> get();
}
