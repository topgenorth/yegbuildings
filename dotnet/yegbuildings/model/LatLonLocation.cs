using System;
using Android.Content;
using Android.Locations;

namespace net.opgenorth.yeg.buildings.model
{
    public class LatLonLocation
    {
        private readonly double _latitude;

        private readonly double _longitude;

        public LatLonLocation(double latitude, double longitude)
        {
            _latitude = latitude;
            _longitude = longitude;
        }

        public LatLonLocation(Intent intent)
        {
            _latitude = intent.GetDoubleExtra(Constants.INTENT_LATITUDE_KEY, 0);
            _longitude = intent.GetDoubleExtra(Constants.INTENT_LONGITUDE_KEY, 0);
        }

        public double Latitude
        {
            get { return _latitude; }
        }

        public double Longitude
        {
            get { return _longitude; }
        }

        public double GetDistanceTo(Location location)
        {
            var thisLocation = new Location("me");
            thisLocation.Latitude = _latitude;
            thisLocation.Longitude = _longitude;

            float distance = thisLocation.DistanceTo(location);
            return distance;
        }

        public void PutExtra(Intent intent)
        {
            intent.PutExtra(Constants.INTENT_LATITUDE_KEY, _latitude);
            intent.PutExtra(Constants.INTENT_LONGITUDE_KEY, _longitude);
        }

//        public GeoPoint GetGeoPoint()
//        {
//            var geopoint = new GeoPoint( )
//        }
    }
}