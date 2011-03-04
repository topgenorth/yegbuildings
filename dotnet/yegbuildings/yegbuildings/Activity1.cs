using Android.App;
using Android.OS;
using Android.Widget;

namespace net.opgenorth.yeg.buildings
{
    [Activity(Label = "YEG Buildings", MainLauncher = true)]
    public class Activity1 : Activity
    {
        private int count = 1;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            // Get our button from the layout resource,
            // and attach an event to it
            var button = FindViewById<Button>(Resource.Id.MyButton);

            button.Click += delegate { button.Text = string.Format("{0} clicks!", count++); };
        }
    }
}