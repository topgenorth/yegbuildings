#region
using Android.App;
using Android.OS;

#endregion

namespace net.opgenorth.m4a.yegbuildings
{
    [Activity(Label = "@string/ApplicationName", MainLauncher = true, Icon = "@drawable/icon")]
    public class YegBuildingsActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
        }
    }
}