using System;
using System.Collections.Generic;
using net.opgenorth.yeg.buildings.model;

namespace net.opgenorth.yeg.buildings.data
{
    public class CsvLineToBuilding : ITransmorgifier<IList<string>, Building>, ITransmorgifier<string, Building>
    {
        public Building Transmorgify(IList<string> source)
        {
            if (source == null)
            {
                return null;
            }
            if (source.Count < 9)
            {
                throw new ArgumentException("There must be at least 8 parts in the IList<string> for conversion.");
            }

// ReSharper disable UseObjectOrCollectionInitializer
            var building = new Building();
// ReSharper restore UseObjectOrCollectionInitializer
            building.RowKey = Guid.Parse(source[0]);
            building.Name = source[1].Trim();
            building.Address = source[2].Trim();
            building.NeighbourHood = source[3].Trim();
            building.Url = source[4].Trim();
            building.ConstructionDate = source[5].Trim();
            building.Latitude = Double.Parse(source[6]);
            building.Longitude = Double.Parse(source[7]);
            return building;
        }


        public Building Transmorgify(string source)
        {
            if (String.IsNullOrWhiteSpace(source))
            {
                return null;
            }
            string[] parts = source.Split(',');
            return Transmorgify(parts);
        }

    }
}