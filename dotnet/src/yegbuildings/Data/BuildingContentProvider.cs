using System;
using System.Collections.Generic;
using System.Linq;
using Android.Content;
using Android.Database;
using Android.Database.Sqlite;
using Uri = Android.Net.Uri;

namespace Net.Opgenorth.Yeg.Buildings.data
{
    public class BuildingContentProvider : ContentProvider
    {
        public static readonly String TAG = Constants.LOG_TAG;
        public static readonly String DATABASE_NAME = "buildings.db";
        public static readonly int DATABASE_VERSION = 5;
        public static readonly String BUILDINGS_TABLE_NAME = "buidings";
        private static readonly Dictionary<String, String> _buildingsProjectionMap;
        private static readonly UriMatcher _uriMatcher;
        private const int BUILDINGS = 1;
        private const int BUILDING_ID = 3;
        private DatabaseHelper _dbHelper;

        static BuildingContentProvider()
        {
            _uriMatcher = new UriMatcher(UriMatcher.NoMatch);
            _uriMatcher.AddURI(Columns.AUTHORITY, "buildings", BUILDINGS);
            _uriMatcher.AddURI(Columns.AUTHORITY, "buildings", BUILDING_ID);

            _buildingsProjectionMap = new Dictionary<string, string>
                                          {
                                              {Columns.ENTITY_ID, Columns.ENTITY_ID},
                                              {Columns.NAME, Columns.NAME},
                                              {Columns.ADDRESS, Columns.ADDRESS},
                                              {Columns.NEIGHBOURHOOD, Columns.NEIGHBOURHOOD},
                                              {Columns.URL, Columns.URL},
                                              {Columns.CONSTRUCTION_DATE, Columns.CONSTRUCTION_DATE},
                                              {Columns.LONGITUDE, Columns.LONGITUDE},
                                              {Columns.LATITUDE, Columns.LATITUDE},
                                              {Columns._ID, Columns._ID}
                                          };
        }

        public override int Delete(Uri uri, string selection, string[] selectionArgs)
        {
            var db = _dbHelper.WritableDatabase;
            string where;

            switch (_uriMatcher.Match(uri))
            {
                case BUILDINGS:
                    where = selection;
                    break;
                case BUILDING_ID:
                    where = GetWhereClause(uri, selection);
                    break;
                default:
                    throw new ArgumentException("Unknown URI:" + uri, "uri");
            }
            var count = db.Delete(BUILDINGS_TABLE_NAME, where, selectionArgs);
            return count;
        }

        private static string GetWhereClause(Uri uri, string selection)
        {
            var segment = uri.PathSegments[1];
            var where = Columns._ID + "=" + segment;
            if (!String.IsNullOrWhiteSpace(selection))
            {
                where += " AND (" + where + ")";
            }
            return where;
        }

        public override string GetType(Uri uri)
        {
            switch (_uriMatcher.Match(uri))
            {
                case BUILDINGS:
                case BUILDING_ID:
                    return Columns.CONTENT_TYPE;
                default:
                    throw new ArgumentException("Unknown URI: " + uri, "uri");
            }
        }

        public override Uri Insert(Uri uri, ContentValues initialValues)
        {
            if (_uriMatcher.Match(uri) != BUILDINGS)
            {
                throw new ArgumentException("Unknown URI: " + uri, "uri");
            }
            var values = initialValues == null ? new ContentValues() : new ContentValues(initialValues);

            foreach (var columnName in Columns.REQUIRED_COLUMNS.Where(columnName => !values.ContainsKey(columnName)))
            {
                throw new ArgumentException("Missing value for column " + columnName, "initialValues");
            }
            var db = _dbHelper.WritableDatabase;
            var rowId = db.Insert(BUILDINGS_TABLE_NAME, Columns.NAME, values);
            if (rowId > 0)
            {
                var buildingUri = ContentUris.WithAppendedId(Columns.CONTENT_URI, rowId);
                Context.ContentResolver.NotifyChange(uri, null);
                return buildingUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }

        public override bool OnCreate()
        {
            _dbHelper = new DatabaseHelper(Context);
            return true;
        }

        public override ICursor Query(Uri uri, string[] projection, string selection, string[] selectionArgs, string sortOrder)
        {
            var qb = new SQLiteQueryBuilder {Tables = BUILDINGS_TABLE_NAME};
            if (IsCollectionUri(uri))
            {
                qb.SetProjectionMap(_buildingsProjectionMap);
            }
            else
            {
                qb.AppendWhere(Columns._ID + "=" + uri.PathSegments[1]);
            }


            var orderBy = String.IsNullOrWhiteSpace(sortOrder) ? Columns.DEFAULT_SORT_ORDER : sortOrder;

            var db = _dbHelper.ReadableDatabase;
            var c = qb.Query(db, projection, selection, selectionArgs, null, null, orderBy);
            c.SetNotificationUri(Context.ContentResolver, uri);
            return c;
        }

        public override int Update(Uri uri, ContentValues values, string selection, string[] selectionArgs)
        {
            var db = _dbHelper.WritableDatabase;
            string where;
            switch (_uriMatcher.Match(uri))
            {
                case BUILDINGS:
                    where = selection;
                    break;
                case BUILDING_ID:
                    where = GetWhereClause(uri, selection);
                    break;
                default:
                    throw new ArgumentException("Unknown URI : " + uri, "uri");
            }
            var count = db.Update(BUILDINGS_TABLE_NAME, values, where, selectionArgs);
            Context.ContentResolver.NotifyChange(uri, null);
            return count;
        }

        private static bool IsCollectionUri(Uri uri)
        {
            return (_uriMatcher.Match(uri) == BUILDINGS);
        }
    }
}