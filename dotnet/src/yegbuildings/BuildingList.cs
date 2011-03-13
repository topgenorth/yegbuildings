using Android.App;
using Android.OS;
using Net.Opgenorth.Yeg.Buildings;

namespace Net.Opgenorth.Yeg.Buildings
{
    [Activity(Label ="List")]
    public class BuildingList : ListActivity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.buildinglist);
        }

    }
}