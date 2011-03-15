using Android.Views;
using Android.Widget;
using Java.Lang;
using Net.Opgenorth.Yeg.Buildings.Model;

namespace Net.Opgenorth.Yeg.Buildings.Widgets
{
    public class BuildingRowWrapper : Object
    {
        private readonly View _baseView;
        private TextView _nameLabel;
        private TextView _addressLabel;
        private TextView _yearBuiltLabel;
        private TextView _distanceToMeLabel;

        public BuildingRowWrapper(View baseView)
        {
            _baseView = baseView;
            _baseView.Tag = this;
        }

        private TextView NameLabel
        {
            get { return _nameLabel ?? (_nameLabel = _baseView.FindViewById<TextView>(Resource.Id.building_row_name)); }
        }

        private TextView AddressLabel
        {
            get { return _addressLabel ?? (_addressLabel = _baseView.FindViewById<TextView>(Resource.Id.building_row_address)); }
        }

        private TextView YearBuilt
        {
            get { return _yearBuiltLabel ?? (_yearBuiltLabel = _baseView.FindViewById<TextView>(Resource.Id.building_row_year_built)); }
        }

        private TextView DistanceToMeLabel
        {
            get { return _distanceToMeLabel ?? (_distanceToMeLabel = _baseView.FindViewById<TextView>(Resource.Id.building_distance_to_me)); }
        }

        public void Display(RelativeBuildingLocation relativeBuilding)
        {
            NameLabel.Text = ((Building) relativeBuilding).Name;
            AddressLabel.Text = ((Building) relativeBuilding).Address;
            YearBuilt.Text = "Construction Date: " + ((Building) relativeBuilding).ConstructionDate;
            const string units = " km.";
            if (relativeBuilding.Distance < 1)
            {
                DistanceToMeLabel.Visibility = ViewStates.Gone;
                DistanceToMeLabel.Text = string.Empty;
            }
            else
            {
                DistanceToMeLabel.Visibility = ViewStates.Visible;
                var distance = relativeBuilding.Distance/1000.0;
                var formatString = (distance < 100) ? "{0:0000.0}" : "{0:00000}";
                DistanceToMeLabel.Text = "Distance: " + string.Format(formatString, distance) + units;
            }
        }
    }
}