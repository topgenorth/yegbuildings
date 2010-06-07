package net.opgenorth.yeg.historicalbuildings.util;

import net.opgenorth.yeg.historicalbuildings.model.Building;

import java.util.List;

public interface IHistoricalBuildingsRepository {
	List<Building> get();
}
