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

        public override bool OnMenuItemSelected(int featureId, IMenuItem item)
        {
            if (item.ItemId == Resource.Id.main_menu_refreshdata)
            {
                Toast.MakeText(this, "TODO: Reload buildings from data.edmonton.ca", ToastLength.Short);
            }
            else
            {
                Toast.MakeText(this, "Don't know how to handle menu item " + item.ItemId, ToastLength.Short);
            }
        }
    }
}