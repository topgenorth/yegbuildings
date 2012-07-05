using System;
using SQLite;

namespace net.opgenorth.yegbuildings.m4a.model
{
    public class Building: ISqliteIdentifiable
    {
        [PrimaryKey, AutoIncrement]
        public int Id { get; set; }
        public String EntityId { get; set; }
        [Indexed]
        public String Name { get; set;  }
        public String Address { get; set;  }
        public String ConstructionDate { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set;  }
    }
}