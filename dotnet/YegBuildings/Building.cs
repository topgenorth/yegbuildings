using System;

namespace net.opgenorth.yegbuildings.m4a
{
    public class Building
    {
        public Guid EntityId { get; set; }
        public String Name { get; set;  }
        public String Address { get; set;  }
        public String ConstructionDate { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set;  }
    }
}