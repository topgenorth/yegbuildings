using System;

namespace Net.Opgenorth.Yeg.Buildings.model
{
    public class Building
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
}