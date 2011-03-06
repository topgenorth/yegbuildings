using System.Collections.Generic;
using net.opgenorth.yeg.buildings.model;

namespace net.opgenorth.yeg.buildings
{
    public interface IBuildingDataService
    {
        IList<Building> FetchAll();
        IList<Building> FetchAll(IBuildingSorter sortedBy);
        bool HasRecords { get;  }
    }

    public interface IBuildingSorter
    {
        IList<Building> Sort(IList<Building> buildings);
    }
}