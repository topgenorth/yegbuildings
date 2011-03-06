using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace net.opgenorth.yeg.buildings.model
{
    class Building
    {
        public long Id { get; set; }
        public Guid RowKey { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string NeighbourHood { get; set; }
        public string Url { get; set; }
        public string ConstructionDate { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
    }

    public class LatLonLocation
    {
        
    }
}