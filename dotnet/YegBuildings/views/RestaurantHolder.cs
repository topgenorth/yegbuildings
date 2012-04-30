using Android.Views;
using Android.Widget;
using Java.Lang;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a.views
{
    internal class RestaurantHolder : Object
    {
        private readonly View _view;
        private readonly TextView _nameView;
        private readonly TextView _addressView;

        public RestaurantHolder(View view)
        {
            _view = view;
            _nameView = view.FindViewById<TextView>(Resource.Id.buildingname);
            _addressView = view.FindViewById<TextView>(Resource.Id.buildingaddress);
        }

        public void PopulateFrom(Building building)
        {
            _view.Tag = this;
            _nameView.Text = building.Name;
            _addressView.Text = building.Address;
        }
    }
}