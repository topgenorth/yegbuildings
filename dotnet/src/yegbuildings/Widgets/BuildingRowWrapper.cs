using Android.Views;
using Android.Widget;

namespace net.opgenorth.yeg.buildings.Widgets
{
    public class BuildingRowWrapper
    {
        private View _baseView;
        private TextView _nameLabel;
        private TextView _addressLabel;
        private TextView _yearBuiltLabel;
        private TextView _distanceToMeLabel;

        public BuildingRowWrapper(View baseView)
        {
            _baseView = baseView;
        }

        public TextView NameLabel
        {
            get
            {
                if (_nameLabel == null)
                {
                    _nameLabel = _baseView.FindViewById<TextView>(Resource.Id.building_row_name);
                }
                return _nameLabel;
            }
        }
        public TextView AddressLabel
        {
            get
            {
                if (_addressLabel == null)
                {
                    _addressLabel = _baseView.FindViewById<TextView>(Resource.Id.building_row_address);
                }
                return _addressLabel;
            }
        }
        public TextView YearBuilt
        {
            get
            {
                if (_yearBuiltLabel == null)
                {
                    _yearBuiltLabel = _baseView.FindViewById<TextView>(Resource.Id.building_row_year_built);
                }
                return _yearBuiltLabel;
            }
        }

        public TextView DistanceToMeLabel
        {
            get
            {
                if (_distanceToMeLabel == null)
                {
                    _distanceToMeLabel = _baseView.FindViewById<TextView>(Resource.Id.building_distance_to_me);
                }
                return _distanceToMeLabel;
            }
        }
        public void Display(object relativeBuilding)
        {
            
        }
    }
}