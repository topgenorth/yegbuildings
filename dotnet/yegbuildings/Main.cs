using Android.App;
using Android.OS;
using Android.Widget;

namespace net.opgenorth.yeg.buildings
{
    [Activity(Label = "YEG Buildings", MainLauncher = true)]
    public class Main : Activity
    {
        private int count = 1;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.Main);
        }
    }
}