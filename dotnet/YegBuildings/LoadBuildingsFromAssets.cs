using System;
using System.Collections.Generic;
using System.IO;
using Android.App;
using System.Linq;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a
{
    public class LoadBuildingsFromAssets
    {
        private readonly Activity _activity;

        public LoadBuildingsFromAssets(Activity activity)
        {
            _activity = activity;
        }


        public List<Building> GetBuildings()
        {
            var list = new List<Building>();
            using (var sr = new StreamReader(_activity.Assets.Open("buildings.csv")))
            {
                String line;
                while ((line = sr.ReadLine()) != null)
                {
                    var parts = line.Split(',');
                    var building = new Building
                                       {
                                           EntityId = Guid.Parse(parts[0]),
                                           Name = parts[1],
                                           Address = parts[2],
                                           ConstructionDate = parts[3],
                                           Latitude = Double.Parse(parts[4]),
                                           Longitude = Double.Parse(parts[5])
                                       };
                    list.Add(building);
                }
            }

            var sortedList = from building in list
                             orderby building.Name
                             select building;
            return sortedList.ToList();
        }
    }
}