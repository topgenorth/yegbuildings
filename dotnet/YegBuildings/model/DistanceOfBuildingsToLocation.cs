using System.Collections.Generic;
using Android.Locations;

namespace net.opgenorth.yegbuildings.m4a.model
{
    /// <summary>
    /// Used to help determine the distance between two points for a building.
    /// </summary>
    public class DistanceOfBuildingsToLocation: IComparer<Building>
    {
        private readonly Location _location;
        public DistanceOfBuildingsToLocation(Location location)
        {
            _location = location;
        }

        public int Compare(Building x, Building y)
        {
            return x.GetDistanceTo(_location).CompareTo(y.GetDistanceTo(_location));
        }
    }
}