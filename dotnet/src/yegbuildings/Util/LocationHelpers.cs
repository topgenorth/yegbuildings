using Android.Content;
using Android.Locations;
using Net.Opgenorth.Yeg.Buildings.Model;

namespace Net.Opgenorth.Yeg.Buildings.Util
{
    public static class LocationHelpers
    {
        public static void AddTo(this Location location, Intent intent)
        {
            intent.PutExtra(Constants.INTENT_LATITUDE_KEY, location.Latitude);
            intent.PutExtra(Constants.INTENT_LONGITUDE_KEY, location.Longitude);
        }

        public static float DistanceTo(this Location location, Building building)
        {
            var buildingLocation = new Location("Building");
            buildingLocation.Longitude = building.Longitude;
            buildingLocation.Latitude = building.Latitude;
            return location.DistanceTo(buildingLocation);
        }
    }
}