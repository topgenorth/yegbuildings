using System.Collections.Generic;
using Android.App;
using Android.Locations;
using Android.OS;
using Android.Views;
using Android.Widget;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a.views
{
    /// <summary>
    /// Displays a list of buidings
    /// </summary>
    public class BuildingListFragment : ListFragment
    {
        private int _selectedBuildingIndex;
        private List<Building> _buildings;
        private BuildingArrayAdapter _listAdapter;

        public override void OnActivityCreated(Bundle savedInstanceState)
        {
            base.OnActivityCreated(savedInstanceState);
            _buildings = Activity.Buildings();
            _listAdapter = new BuildingArrayAdapter(Activity, _buildings);
            ;
            ListAdapter = _listAdapter;

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
            if ((position < 0) || (position >= _buildings.Count))
            {
                return;
            }
            _selectedBuildingIndex = position;
            ListView.SetItemChecked(position, true);
            var building = _buildings[position];
            var mapFrag = FragmentManager.FindFragmentById<MapFragment>(Resource.Id.map_fragment);
            mapFrag.AnimateTo(building);
        }

        public void UpdateWithLocation(Location location)
        {
            _listAdapter.UpdateLocation(location);
        }
    }
}