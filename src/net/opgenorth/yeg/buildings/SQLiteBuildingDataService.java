package net.opgenorth.yeg.buildings;

import net.opgenorth.yeg.buildings.data.IBuildingDataService;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.IBuildingSorter;

import java.util.ArrayList;
import java.util.List;


public class SQLiteBuildingDataService implements IBuildingDataService {
    @Override
    public List<Building> fetchAll() {
        ArrayList<Building> list = new ArrayList<Building>();

        return list;
    }

    @Override
    public List<Building> fetchAll(IBuildingSorter sortedBy) {
        return new ArrayList<Building>(0);
    }
}
