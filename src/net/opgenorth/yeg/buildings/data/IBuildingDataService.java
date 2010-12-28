package net.opgenorth.yeg.buildings.data;

import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.IBuildingSorter;

import java.util.List;

public interface IBuildingDataService {
    List<Building> fetchAll();
    List<Building> fetchAll(IBuildingSorter sortedBy);
    boolean hasRecords();
}
