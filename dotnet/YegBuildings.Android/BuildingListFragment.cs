using System.Linq;
using Android.App;
using Android.OS;
using Android.Views;
using Android.Widget;

namespace net.opgenorth.yegbuildings.m4a
{
    public class BuildingListFragment : ListFragment
    {
        private int _selectedBuildingIndex;


        public override void OnActivityCreated(Bundle savedInstanceState)
        {
            base.OnActivityCreated(savedInstanceState);
            var buildingNames = from building in Activity.Buildings() 
                                select building.Name;
            var adapter = new ArrayAdapter(Activity, Android.Resource.Layout.SimpleListItemChecked, buildingNames.ToList());
            ListAdapter = adapter;

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