using Android.App;
using Android.Content;
using Android.OS;
using Android.Util;
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
            SetupTabs();
        }

        private void SetupTabs()
        {
            TabHost.TabSpec spec;

            // Set up the first intent for the map.
            var mapIntent = new Intent().SetClass(this, typeof (BuildingMap));
            spec = TabHost.NewTabSpec("Map")
                .SetIndicator(
                    Resources.GetString(Resource.String.buildinglist_tabtext),
                    Resources.GetDrawable(Resource.Drawable.maps2))
                .SetContent(mapIntent);
            TabHost.AddTab(spec);

            // Setup another intent for the list.
            var listIntent = new Intent().SetClass(this, typeof (BuildingList));
            spec = TabHost.NewTabSpec("List")
                .SetIndicator(
                    Resources.GetString(Resource.String.buildinglist_tabtext),
                    Resources.GetDrawable(Resource.Drawable.ic_tab_list))
                .SetContent(listIntent);
            TabHost.AddTab(spec);
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
                Log.Wtf(Constants.LOG_TAG, "Don't know what to do with this IMenuItem: " + item.ItemId);
                Toast.MakeText(this, "Don't know how to handle menu item " + item.ItemId, ToastLength.Short);
            }
            return true;
        }
    }
}