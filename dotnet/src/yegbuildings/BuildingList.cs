using System.Collections.Generic;
using Android.App;
using Android.OS;
using Android.Widget;
using Net.Opgenorth.Yeg.Buildings.Data;
using Net.Opgenorth.Yeg.Buildings.Model;
using Net.Opgenorth.Yeg.Buildings.Widgets;

namespace Net.Opgenorth.Yeg.Buildings
{
    [Activity(Label = "YEG Building List", MainLauncher = true)]
    public class BuildingList : ListActivity
    {
        private TextView _buildingCount;
        private readonly List<RelativeBuildingLocation> _buildings = new List<RelativeBuildingLocation>();
        private BuildingListAdapter _buildingListAdapter;
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            InitializeView();
            GetBuildingList();
            DisplayBuildingList();
        }

        private void InitializeView()
        {
            SetContentView(Resource.Layout.buildinglist);
            _buildingCount = FindViewById<TextView>(Resource.Id.buildinglist_building_count);
        }

        private void GetBuildingList()
        {
            _buildings.Clear();
            var svc = new SQLiteBuildingDataService(this);
            foreach (var building in svc.FetchAll())
            {
                _buildings.Add(new RelativeBuildingLocation(building, null));
            }
        }

        private void DisplayBuildingList()
        {
            _buildingCount.Text = "Found " + _buildings.Count + " buildings.";
            _buildingListAdapter = new BuildingListAdapter(this, _buildings);
            ListAdapter = _buildingListAdapter;
        }
    }
}