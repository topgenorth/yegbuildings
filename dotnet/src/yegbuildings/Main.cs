using System;
using Android.App;
using Android.OS;
using Android.Views;
using Android.Widget;

namespace net.opgenorth.yeg.buildings
{
    [Activity(Label = "YEG Buildings", MainLauncher = true)]
    public class Main : TabActivity 
    {
        private int count = 1;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.Main);
        }

        public override bool OnCreateOptionsMenu(IMenu menu)
        {
            base.OnCreateOptionsMenu(menu);
            var inflater = new MenuInflater(this);
            inflater.Inflate(Resource.Menu.main, menu);
            return true;
        }

    }
}