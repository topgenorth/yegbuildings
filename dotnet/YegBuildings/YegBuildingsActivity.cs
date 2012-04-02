using Android.App;
using Android.GoogleMaps;
using Android.OS;

namespace net.opgenorth.yegbuildings.m4a
{
    [Activity(Label = "@string/app_name", MainLauncher = true, Icon = "@drawable/icon")]
    public class YegBuildingsActivity : MapActivity 
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.Main);
        }

        protected override bool IsRouteDisplayed
        {
            get { return false; }
        }
    }
}