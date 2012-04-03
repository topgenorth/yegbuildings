using System.Collections.Generic;
using Android.App;
using Android.GoogleMaps;
using Android.OS;

namespace net.opgenorth.yegbuildings.m4a
{
    public static class BuildingsHelper
    {
        public static GeoPoint GetPoint(this Building building)
        {
            return (new GeoPoint((int) (building.Latitude*1000000.0), (int) (building.Longitude*1000000.0)));
        }

        public static Building GetBuilding(this Activity activity, int index)
        {
            if (activity is YegBuildingsActivity)
            {
                return activity.Buildings()[index];
            }
            return null;
        }

        public static List<Building> Buildings(this Activity activity)
        {
            if (activity is YegBuildingsActivity)
            {
                return ((YegBuildingsActivity) activity).Buildings;
            }
            return new List<Building>(0);
        }

        public static int GetSelectedBuildingIndex(this Bundle bundle)
        {
            return bundle.GetInt("selected_building_index", -1);
        }

        public static void SetSelectedBuildingIndex(this Bundle bundle, int index)
        {
            if (index < -1)
            {
                index = -1;
            }
            bundle.PutInt("selected_building_index", index);
        }
    }
}