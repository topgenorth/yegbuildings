using System;
using System.IO;
using System.Text;
using Android.Content;
using Android.Database.Sqlite;
using Android.Util;

namespace net.opgenorth.yeg.buildings.data
{
    public class DatabaseHelper : SQLiteOpenHelper
    {
        private readonly Context _context;

        public DatabaseHelper(Context context) : base(context, BuildingContentProvider.DATABASE_NAME, null, BuildingContentProvider.DATABASE_VERSION)
        {
            _context = context;
        }

        public override void OnCreate(SQLiteDatabase db)
        {
            CreateBuildingsTable(db);
            LoadBuildingsTable(db);
        }

        private void LoadBuildingsTable(SQLiteDatabase db)
        {
            var sqlLoad = GetSqlForBuildingData();
            if (String.IsNullOrEmpty(sqlLoad))
            {
                Log.Warn(BuildingContentProvider.TAG, "No SQL to initially load the table");
                return;
            }
            var count = 0;
            var separator = new[] {";\\n"};
            var insertStatements = sqlLoad.Split(separator, StringSplitOptions.RemoveEmptyEntries);
            foreach (var sql in insertStatements)
            {
                Log.Debug(BuildingContentProvider.TAG, "Running sql : " + sql);
                db.ExecSQL(sql);
                count++;
            }
            Log.Debug(BuildingContentProvider.TAG, "Inserted " + count + " buildings into the database.");
        }

        private void CreateBuildingsTable(SQLiteDatabase db)
        {
            var sqlCreateTable = new StringBuilder("CREATE TABLE ");
            sqlCreateTable.Append(BuildingContentProvider.BUILDINGS_TABLE_NAME);
            sqlCreateTable.Append(" (");
            sqlCreateTable.Append(Columns._ID);
            sqlCreateTable.Append(" INTEGER PRIMARY KEY, ");
            sqlCreateTable.Append(Columns.NAME);
            sqlCreateTable.Append(" TEXT, ");
            sqlCreateTable.Append(Columns.ENTITY_ID);
            sqlCreateTable.Append(" TEXT,");
            sqlCreateTable.Append(Columns.ADDRESS);
            sqlCreateTable.Append(" TEXT,");
            sqlCreateTable.Append(Columns.URL);
            sqlCreateTable.Append(" TEXT,");
            sqlCreateTable.Append(Columns.CONSTRUCTION_DATE);
            sqlCreateTable.Append(" TEXT,");
            sqlCreateTable.Append(Columns.LATITUDE);
            sqlCreateTable.Append(" DOUBLE, ");
            sqlCreateTable.Append(Columns.LONGITUDE);
            sqlCreateTable.Append(" DOUBLE");
            sqlCreateTable.Append(");");
            db.ExecSQL(sqlCreateTable.ToString());
            Log.Verbose(BuildingContentProvider.TAG, "Created a table with SQL : " + sqlCreateTable);
        }

        public override void OnUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.Warn(BuildingContentProvider.TAG, "Upgrading database from " + oldVersion + " to " + newVersion);
            db.ExecSQL("DROP TABLE IF EXISTS " + BuildingContentProvider.BUILDINGS_TABLE_NAME);
            OnCreate(db);
        }

        private String GetSqlForBuildingData()
        {
//                ITransmorgifier<object, string> inputStreamToString = new Inp
            var stream = new StreamReader(_context.Assets.Open("buidlings.sql"));
            var sql = stream.ReadToEnd();
            stream.Close();
            return sql;
        }
    }
}