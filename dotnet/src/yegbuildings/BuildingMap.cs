using Android.App;
using Android.OS;
using Net.Opgenorth.Yeg.Buildings;
using Net.Opgenorth.Yeg.Buildings.Util;

namespace Net.Opgenorth.Yeg.Buildings
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