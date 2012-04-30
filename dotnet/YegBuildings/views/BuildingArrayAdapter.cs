using System;
using System.Collections.Generic;
using Android.Content;
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
            View row;
            RestaurantHolder holder;
            if (convertView == null)
            {
                var inflater = (LayoutInflater) Context.GetSystemService(Context.LayoutInflaterService);
                row = inflater.Inflate(Resource.Layout.buildingrow, parent, false);
                holder = new RestaurantHolder(row);
            }
            else
            {
                row = convertView;
                holder = new RestaurantHolder(row);
            }

            holder.PopulateFrom(_buildings[position]);
//            row.SetBackgroundResource(position%2 == 0 ? Resource.Color.roweven : Resource.Color.rowodd);

            return row;
        }
    }
}