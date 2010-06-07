package net.opgenorth.yeg.historicalbuildings.data;

import net.opgenorth.yeg.historicalbuildings.model.Building;
import net.opgenorth.yeg.historicalbuildings.model.IBuildingSorter;

import java.util.List;

public interface IBuildingDataService {
    List<Building> fetchAll();
    List<Building> fetchAll(IBuildingSorter sortedBy);
}
