using System.Collections.Generic;
using net.opgenorth.yeg.buildings.model;

namespace net.opgenorth.yeg.buildings
{
    public interface IBuildingDataService
    {
        bool HasRecords { get; }
        IList<Building> FetchAll();
        IList<Building> FetchAll(IBuildingSorter sortedBy);
    }
}