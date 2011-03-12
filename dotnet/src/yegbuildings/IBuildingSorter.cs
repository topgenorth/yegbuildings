using System.Collections.Generic;
using Android.Locations;
using net.opgenorth.yeg.buildings.model;

namespace net.opgenorth.yeg.buildings
{
    public interface IBuildingSorter
    {
        IList<Building> Sort(IEnumerable<Building> buildings);
        Location Location { get; }
    }
}