using SQLite;
using net.opgenorth.yegbuildings.m4a.model;

namespace net.opgenorth.yegbuildings.m4a.data
{
    internal class BuildingDatabase : SQLiteConnection, IBuildingDatabase
    {
        public BuildingDatabase() : this(Globals.DatabaseName)
        {
            
        }
        public BuildingDatabase(string databasePath) : base(databasePath)
        {
            CreateDatabase();
        }

        public void CreateDatabase()
        {
            CreateTable<Building>();
        }

 
    }
}