using System.Collections.Generic;
using System.Linq;
using Android.App;
using Android.Views;
using Android.Widget;
using Net.Opgenorth.Yeg.Buildings.Model;

namespace Net.Opgenorth.Yeg.Buildings.Widgets
{
    public class BuildingListAdapter : ArrayAdapter<RelativeBuildingLocation>
    {
        private readonly Activity _context;
        private readonly List<RelativeBuildingLocation> _buildingLocations;

        public BuildingListAdapter(Activity context, List<RelativeBuildingLocation> relativeBuildingLocations)
            : base(context, Resource.Layout.historicalbuildingrow, relativeBuildingLocations)
        {
            _context = context;
            _buildingLocations = relativeBuildingLocations;
        }

        public override View GetView(int position, View convertView, ViewGroup parent)
        {
            BuildingRowWrapper rowWrapper;

            if (convertView == null)
            {
                convertView = _context.LayoutInflater.Inflate(Resource.Layout.historicalbuildingrow, null);
                rowWrapper = new BuildingRowWrapper(convertView);
            }
            else
            {
                rowWrapper = (BuildingRowWrapper) convertView.Tag;
            }

            if (_buildingLocations.Any())
            {
                rowWrapper.Display(_buildingLocations[position]);
            }

            return convertView;
        }
    }
}