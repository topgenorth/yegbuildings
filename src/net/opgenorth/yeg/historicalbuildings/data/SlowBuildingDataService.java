package net.opgenorth.yeg.historicalbuildings.data;

import net.opgenorth.yeg.historicalbuildings.model.Building;
import net.opgenorth.yeg.historicalbuildings.model.IBuildingSorter;

import java.util.List;

/**
 * This ALWAYS goes out and makes a call to the YEG Open Data repository.
 */
public class SlowBuildingDataService implements IBuildingDataService {
    private IReadOnlyBuildingRepository _repository = new YegOpenDataHistoricalBuildingRepository();

    @Override
    public List<Building> fetchAll() {
        return _repository.fetch();
    }

    @Override
    public List<Building> fetchAll(IBuildingSorter sortedBy) {
        List<Building> buildings = fetchAll();
        if (sortedBy != null)
            return sortedBy.sortList(buildings);
        else
            return buildings;
    }


}
