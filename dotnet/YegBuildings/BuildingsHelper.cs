using System.Collections.Generic;
using Android.App;

namespace net.opgenorth.yegbuildings.m4a
{
    public static class BuildingsHelper
    {
        public static List<Building> Buildings (this Activity activity)
        {
            if (activity is YegBuildingsActivity )
            {
                return ((YegBuildingsActivity) activity).Buildings;
            }
            return new List<Building>(0);
        } 
    }
}