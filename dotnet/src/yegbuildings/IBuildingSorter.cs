using System.Collections.Generic;
using net.opgenorth.yeg.buildings.model;

namespace net.opgenorth.yeg.buildings
{
    public interface IBuildingSorter
    {
        IList<Building> Sort(IList<Building> buildings);
    }
}