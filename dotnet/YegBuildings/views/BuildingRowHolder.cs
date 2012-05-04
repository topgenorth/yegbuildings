using Android.Locations;
using Android.Views;
using Android.Widget;
using Java.Lang;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a.views
{
    /// <summary>
    ///   This class holds the references to the controls on a row.
    /// </summary>
    internal class BuildingRowHolder : Object
    {
        private readonly View _view;
        private readonly TextView _nameView;
        private readonly TextView _addressView;
        private readonly TextView _distanceView;

        public BuildingRowHolder(View view)
        {
            _view = view;
            _nameView = view.FindViewById<TextView>(Resource.Id.buildingname);
            _addressView = view.FindViewById<TextView>(Resource.Id.buildingaddress);
            _distanceView = view.FindViewById<TextView>(Resource.Id.buildingdistance);
        }

        public void PopulateFrom(Building building)
        {
            PopulateFrom(building, null);
        }

        public void PopulateFrom(Building building, Location location)
        {
            _view.Tag = this;
            _nameView.Text = building.Name;
            _addressView.Text = building.Address;
            if (location == null)
            {
                _distanceView.Visibility = ViewStates.Gone;
                return;
            }

            _distanceView.Text = building.GetDistanceTo(location) + " metres.";
            _distanceView.Visibility = ViewStates.Visible;
        }
    }
}