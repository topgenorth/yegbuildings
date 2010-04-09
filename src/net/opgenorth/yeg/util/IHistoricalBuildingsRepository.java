package net.opgenorth.yeg.util;

import net.opgenorth.yeg.model.HistoricalBuilding;

import java.util.List;

public interface IHistoricalBuildingsRepository {
	List<HistoricalBuilding> get();
}
