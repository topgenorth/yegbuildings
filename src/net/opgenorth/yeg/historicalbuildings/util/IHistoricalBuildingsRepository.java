package net.opgenorth.yeg.historicalbuildings.util;

import net.opgenorth.yeg.historicalbuildings.model.HistoricalBuilding;

import java.util.List;

public interface IHistoricalBuildingsRepository {
	List<HistoricalBuilding> get();
}
