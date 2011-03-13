using System;
using Android.Content;
using Android.Locations;
using Net.Opgenorth.Yeg.Buildings.Util;

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
            get { return _relativeLocation == null ? 0 : _relativeLocation.DistanceTo(_building); }
        }

        public void AddTo(Intent intent)
        {
            intent.PutExtra(Constants.INTENT_BUILDING_NAME_KEY, _building.Name);
            intent.PutExtra(Constants.INTENT_BUILDING_ADDRESS_KEY, _building.Address);
            intent.PutExtra(Constants.INTENT_BUILDING_CONSTRUCTION_DATE_KEY, _building.ConstructionDate);
            intent.PutExtra(Constants.INTENT_LATITUDE_KEY, _building.Latitude);
            intent.PutExtra(Constants.INTENT_LONGITUDE_KEY, _building.Longitude);
        }

        public override string ToString()
        {
            return String.Format("{0}:{1},{2}", _building.Name, _building.Latitude, _building.Longitude);
        }

        public Building Building
        {
            get { return _building; }
        }

        public Location Location
        {
            get { return _relativeLocation; }
        }
    }
}