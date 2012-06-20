using System;
using System.Collections.Generic;
using Android.Content;
using Android.Locations;
using Android.Views;
using Android.Widget;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a.views
{
    /// <summary>
    /// Used to display a list of buildings.
    /// </summary>
    public class BuildingArrayAdapter : ArrayAdapter<Building>
    {
        private Context _context;
        private readonly List<Building> _buildings;
        private Location _location;

        public BuildingArrayAdapter(Context context, List<Building> buildings) : base(context, Resource.Layout.buildingrow, buildings)
        {
            _location = null;
            if (buildings == null)
            {
                throw new ArgumentNullException("buildings", "Must provide a list of buildings.");
            }
            _context = context;
            _buildings = buildings;
        }

        public override View GetView(int position, View convertView, ViewGroup parent)
        {
            View row;
            BuildingRowHolder holder;
            if (convertView == null)
            {
                var inflater = (LayoutInflater) Context.GetSystemService(Context.LayoutInflaterService);
                row = inflater.Inflate(Resource.Layout.buildingrow, parent, false);
                holder = new BuildingRowHolder(row);
            }
            else
            {
                row = convertView;
                holder = new BuildingRowHolder(row);
            }

            holder.PopulateFrom(_buildings[position], _location);
            return row;
        }

        public void UpdateLocation(Location location)
        {
            _location = location;
            _buildings.Sort(new DistanceOfBuildingsToLocation(location));
            NotifyDataSetChanged();
        }
    }
}