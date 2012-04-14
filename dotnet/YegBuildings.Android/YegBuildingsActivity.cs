using System.Collections.Generic;
using Android.App;
using Android.GoogleMaps;
using Android.OS;

namespace net.opgenorth.yegbuildings.m4a
{
    [Activity(Label = "@string/app_name", MainLauncher = true, Icon = "@drawable/icon")]
    public class YegBuildingsActivity : MapActivity
    {
        private List<Building> _buildings;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.Main);

            var loader = new LoadBuildingsFromAssets(this);
            _buildings = loader.GetBuildings();
        }


        internal List<Building> Buildings
        {
            get { return _buildings; }
        }

        protected override bool IsRouteDisplayed
        {
            get { return false; }
        }
    }
}