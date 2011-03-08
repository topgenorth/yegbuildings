using Android.App;
using Android.OS;

namespace net.opgenorth.yeg.buildings
{
    [Activity(Label ="List")]
    public class BuildingList : ListActivity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.buildinglist);
            // Create your application here
        }

    }
}