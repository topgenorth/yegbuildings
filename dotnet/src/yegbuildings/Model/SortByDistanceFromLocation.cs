using System.Collections.Generic;
using System.Linq;
using Android.Locations;
using Net.Opgenorth.Yeg.Buildings.Util;

namespace Net.Opgenorth.Yeg.Buildings.model
{
    public class SortByDistanceFromLocation : IBuildingSorter
    {
        private readonly Location _location;

        public SortByDistanceFromLocation(Location location)
        {
            _location = location;
        }

        public Location Location
        {
            get { return _location; }
        }

        public IList<Building> Sort(IEnumerable<Building> buildings)
        {
            if (_location == null)
            {
                return buildings.ToList();
            }

            var locationToBuildings = from building in buildings
                                      select new
                                                 {
                                                     Building = building,
                                                     Distance = _location.DistanceTo(building)
                                                 };
            var sortedList = from l2b in locationToBuildings
                             orderby l2b.Distance
                             select l2b.Building;

            return sortedList.ToList();
        }
    }
}