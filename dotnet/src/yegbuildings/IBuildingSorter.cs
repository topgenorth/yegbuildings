using System.Collections.Generic;
using Android.Locations;
using Net.Opgenorth.Yeg.Buildings.model;

namespace Net.Opgenorth.Yeg.Buildings
{
    public interface IBuildingSorter
    {
        IList<Building> Sort(IEnumerable<Building> buildings);
        Location Location { get; }
    }
}