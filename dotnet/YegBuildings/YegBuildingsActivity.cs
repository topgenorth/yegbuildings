using System.Collections.Generic;

using Android.App;
using Android.GoogleMaps;
using Android.Locations;
using Android.OS;
using Android.Util;

using net.opgenorth.yegbuildings.m4a.data;
using net.opgenorth.yegbuildings.m4a.model;
using net.opgenorth.yegbuildings.m4a.views;

namespace net.opgenorth.yegbuildings.m4a
{
    [Activity(Label = "@string/app_name", MainLauncher = true, Icon = "@drawable/icon")]
    public class YegBuildingsActivity : MapActivity, ILocationListener
    {
        private LocationManager _locationManager;

        internal List<Building> Buildings { get; private set; }

        protected override bool IsRouteDisplayed
        {
            get
            {
                return false;
            }
        }

        public void OnLocationChanged(Location location)
        {
            if (location == null)
            {
                Log.Warn(Globals.LogTag, "We don't have a location, can't do much!");
                return;
            }

            Log.Debug(Globals.LogTag, "New Location at " + location.Longitude + " , " + location.Latitude);
            if (!location.HasAccuracy)
            {
                Log.Debug(Globals.LogTag, "Seems there is no accuracy to this location.");
                return;
            }

            var list = FragmentManager.FindFragmentById<BuildingListFragment>(Resource.Id.building_list_fragment);
            list.UpdateWithLocation(location);
        }

        public void OnProviderDisabled(string provider)
        {
            Log.Verbose(Globals.LogTag, "Location provider disabled.");
        }

        public void OnProviderEnabled(string provider)
        {
            Log.Verbose(Globals.LogTag, "Location provider enabled.");
        }

        public void OnStatusChanged(string provider, Availability status, Bundle extras)
        {
            Log.Verbose(Globals.LogTag, "Status changed.");
        }

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            Globals.Initialize(ApplicationContext.PackageName);
            SetContentView(Resource.Layout.Main);

            var database = new BuildingDatabase(Globals.DatabaseName);
            database.CreateDatabase();

            var loader = new LoadBuildingsFromAssets(this);
            Buildings = loader.GetBuildings();

            InitializeLocationManager();
        }

        protected override void OnPause()
        {
            base.OnPause();
            _locationManager.RemoveUpdates(this);
        }

        protected override void OnResume()
        {
            base.OnResume();
            _locationManager.RequestLocationUpdates(LocationManager.GpsProvider, Globals.GpsUpdateTimeInterval, Globals.GpsUpdateDistanceInterval, this);
        }

        private void InitializeLocationManager()
        {
            //            var locationCriteria = new Criteria();
            //            locationCriteria.Accuracy = Accuracy.Medium;
            //            locationCriteria.PowerRequirement = Power.Medium;
            //
            //            var locationProvider = _locationManager.GetBestProvider(locationCriteria, false);

            _locationManager = (LocationManager)GetSystemService(LocationService);
        }
    }
}
