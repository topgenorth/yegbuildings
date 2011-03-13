using System;
using Android.Content;
using Android.Locations;

namespace Net.Opgenorth.Yeg.Buildings.model
{
    public class RelativeBuildingLocation
    {
        private readonly Building _building;
        private readonly Location _relativeLocation;

        public RelativeBuildingLocation(Building building, Location relativeLocation)
        {
            _building = building;
            _relativeLocation = relativeLocation;
        }

        public static implicit operator Building(RelativeBuildingLocation relativeBuildingLocation)
        {
            return relativeBuildingLocation._building;
        }

        public static implicit operator Location(RelativeBuildingLocation relativeBuildingLocation)
        {
            return relativeBuildingLocation._relativeLocation;
        }

        public double Distance
        {
            get { throw new NotImplementedException(); }
        }

        public void AddTo(Intent intent)
        {
            throw new NotImplementedException();
        }

        public override string ToString()
        {
            throw new NotImplementedException();
        }
    }
}