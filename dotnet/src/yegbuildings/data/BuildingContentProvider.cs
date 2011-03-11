using System;
using System.Collections.Generic;
using Android.Content;
using Android.Database;
using Android.Database.Sqlite;
using Uri = Android.Net.Uri;

namespace net.opgenorth.yeg.buildings.data
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
        private DatabaseHelper _openHelper;

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
            throw new NotImplementedException();
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

        public override Uri Insert(Uri uri, ContentValues values)
        {
            throw new NotImplementedException();
        }

        public override bool OnCreate()
        {
            _openHelper = new DatabaseHelper(this.Context);
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


            string orderBy;
            if (String.IsNullOrWhiteSpace(sortOrder))
            {
                orderBy = Columns.DEFAULT_SORT_ORDER;
            }
            else
            {
                orderBy = sortOrder;
            }

            var db = _openHelper.ReadableDatabase;
            var c = qb.Query(db, projection, selection, selectionArgs, null, null, orderBy);
            c.SetNotificationUri(this.Context.ContentResolver, uri);
            return c;
        }

        public override int Update(Uri uri, ContentValues values, string selection, string[] selectionArgs)
        {
            throw new NotImplementedException();
        }

        private bool IsCollectionUri(Uri uri)
        {
            return (_uriMatcher.Match(uri) == BUILDINGS);
        }
    }
}