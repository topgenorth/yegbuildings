using System.Collections.Generic;
using Net.Opgenorth.Yeg.Buildings.Model;

namespace Net.Opgenorth.Yeg.Buildings
{
    public interface IBuildingDataService
    {
        bool HasRecords { get; }
        IList<Building> FetchAll();
        IList<Building> FetchAll(IBuildingSorter sortedBy);
    }
}