using System;
using System.Collections.Generic;
using Android.Content;
using Android.Database;
using Android.Util;
using Java.Lang;
using net.opgenorth.yeg.buildings.model;
using Exception = System.Exception;

namespace net.opgenorth.yeg.buildings.data
{
    public class SQLiteBuildingDataService : IBuildingDataService
    {
        private readonly ContextWrapper _context;

        public SQLiteBuildingDataService(ContextWrapper context)
        {
            _context = context;
        }

        public bool HasRecords
        {
            get
            {
                bool hasRecords;
                long count = 0;
                var c = _context.ContentResolver.Query(Columns.CONTENT_URI, Columns.ALL_COLUMNS, null, null, null);
                try
                {
                    c.MoveToFirst();
                    do
                    {
                        count++;
                    }
                    while (c.MoveToNext());
                    hasRecords = count > 0;
                }
                catch (Exception ex)
                {
                    hasRecords = false;
                    Log.Debug(Constants.LOG_TAG, (Throwable) ex, "Problem to see if we have records.");
                }
                finally
                {
                    c.Close();
                }
                return hasRecords;
            }
        }

        public List<Building> FetchAllInternal()
        {
            var buildings = new List<Building>();
            var c = _context.ContentResolver.Query(Columns.CONTENT_URI, Columns.ALL_COLUMNS, null, null, null);
            try
            {
                c.MoveToFirst();
                do
                {
                    var building = GetBuildingFromRow(c);
                    buildings.Add(building);
                }
                while (c.MoveToNext());
            }
            finally
            {
                c.Close();
            }
            return buildings;
        }

        private static Building GetBuildingFromRow(ICursor cursor)
        {
            var building = new Building
                               {
                                   Id = cursor.GetLong(0),
                                   Name = cursor.GetString(1),
                                   RowKey = Guid.Parse(cursor.GetString(2)),
                                   Address = cursor.GetString(3),
                                   NeighbourHood = cursor.GetString(4),
                                   Url = cursor.GetString(5),
                                   ConstructionDate = cursor.GetString(6),
                                   Longitude = cursor.GetDouble(7),
                                   Latitude = cursor.GetDouble(8)
                               };
            return building;
        }

        public IList<Building> FetchAll()
        {
            return FetchAllInternal();
        }

        public IList<Building> FetchAll(IBuildingSorter sortedBy)
        {
            var buildings = FetchAllInternal();
            return sortedBy == null ? buildings : sortedBy.Sort(buildings);
        }
    }
}