using System.Collections.Generic;
using System.Linq;
using Net.Opgenorth.Yeg.Buildings.model;

namespace Net.Opgenorth.Yeg.Buildings.data
{
    public class CsvLinesToBuildingList : ITransmorgifier<IList<string>, IList<Building>>
    {
        public static readonly ITransmorgifier<string, Building> LineTransmorgifier = new CsvLineToBuilding();
        public IList<Building> Transmorgify(IList<string> source)
        {
            return source.Select(line => LineTransmorgifier.Transmorgify(line)).Where(building => building != null).ToList();
        }

    }
}