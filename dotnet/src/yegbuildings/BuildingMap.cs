using Android.App;
using Android.OS;
using net.opgenorth.yeg.buildings.Util;

namespace net.opgenorth.yeg.buildings
{
    [Activity(Label = "Map")]
    public class BuildingMap : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(this.IsDebug() ? Resource.Layout.mapofallbuildings_debug : Resource.Layout.mapofallbuildings_production);
        }
    }
}