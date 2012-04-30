using System;
using System.Collections.Generic;
using System.Linq;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Views;
using Android.Widget;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a.views
{
    public class BuildingArrayAdapter : ArrayAdapter<Building>
    {
        private Context _context;
        private readonly List<Building> _buildings;

        public BuildingArrayAdapter(Context context, List<Building> buildings) : base(context, Resource.Layout.buildingrow, buildings)
        {
            if (buildings == null)
            {
                throw new ArgumentNullException("buildings", "Must provide a list of buildings.");
            }
            _context = context;
            _buildings = buildings;
        }

        public override View GetView(int position, View convertView, ViewGroup parent)
        {
            var inflater = (LayoutInflater) Context.GetSystemService(Context.LayoutInflaterService);
            var row = inflater.Inflate(Resource.Layout.buildingrow, parent, false);

            var name = row.FindViewById<TextView>(Resource.Id.buildingname);
            var address = row.FindViewById<TextView>(Resource.Id.buildingaddress);
            var building = _buildings[position];
            name.Text = building.Name;
            address.Text = building.Address;

            return row;
        }
    }

    public class BuildingListFragment : ListFragment
    {
        private int _selectedBuildingIndex;

        public override void OnActivityCreated(Bundle savedInstanceState)
        {
            base.OnActivityCreated(savedInstanceState);
            ListAdapter = new BuildingArrayAdapter(Activity, Activity.Buildings()); ;

            if (savedInstanceState != null)
            {
                _selectedBuildingIndex = savedInstanceState.GetSelectedBuildingIndex();
            }
            ListView.ChoiceMode = ChoiceMode.Single;
            ShowBuilding(_selectedBuildingIndex);
        }

        public override void OnListItemClick(ListView l, View v, int position, long id)
        {
            base.OnListItemClick(l, v, position, id);
            ShowBuilding(position);
        }

        public override void OnSaveInstanceState(Bundle outState)
        {
            base.OnSaveInstanceState(outState);
            outState.SetSelectedBuildingIndex(_selectedBuildingIndex);
        }

        private void ShowBuilding(int position)
        {
            _selectedBuildingIndex = position;
            if (position < 0)
            {
                // todo zoom to the current location of device.
                return;
            }
            ListView.SetItemChecked(position, true);
            var building = Activity.GetBuilding(position);

            var mapFrag = FragmentManager.FindFragmentById<MapFragment>(Resource.Id.map_fragment);
            mapFrag.AnimateTo(building);
        }
    }
}