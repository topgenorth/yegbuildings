using Android.App;
using Android.OS;
using Android.Widget;
using yegbuildings;

namespace net.opgenorth.yeg.buildings
{
    [Activity(Label = "YEG Buildings", MainLauncher = true)]
    public class Main : Activity
    {
        private int _count = 1;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.Main);
            var button = FindViewById<Button>(Resource.Id.MyButton);
            button.Click += delegate { button.Text = string.Format("{0} clicks!", _count++); };
        }
    }
}