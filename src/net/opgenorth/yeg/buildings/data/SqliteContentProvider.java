package net.opgenorth.yeg.buildings.data;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public class SqliteContentProvider extends ContentProvider {
    public static final String TAG = "SqliteContentProvider";
    public static final String DATABASE_NAME = "buildings.db";
    public static final int DATABASE_VERSION = 1;
    public static final String BUILDINGS_TABLE_NAME = "buildings";

    private static HashMap<String, String> _buildingsProjectionMap;
//    private static HashMap<String, String> _liveFolderProjectionMap;

    private static final int BUILDINGS = 1;
    private static final int BUILDING_ID = 2;
//    private static final int LIVE_FOLDER_BUILDINGS = 3;

    private static final UriMatcher _uriMatcher;

    public static final class Columns implements BaseColumns {
        public static final String AUTHORITY = "net.opgenorth.yeg.buildings.Provider";

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/buildings");

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of buildings.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.building";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single building.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.building";

        private Columns() {
        }

        public static final String DEFAULT_SORT_ORDER = "modified DESC";
        public static final String NAME = "name";
        public static final String PARTITION_KEY = "partition_key";
        public static final String ROW_KEY = "row_key";
        public static final String ENTITY_ID = "entityid";
        public static final String OPEN_DATA_TIMESTAMP = "open_data_timestamp";
        public static final String ADDRESS = "address";
        public static final String NEIGHBOURHOOD = "neighbourhood";
        public static final String URL = "url";
        public static final String CONSTRUCTION_DATE = "construction_date";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String CREATED_DATE = "created";
        public static final String MODIFIED_DATE = "modified";
    }

    static {
        _uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        _uriMatcher.addURI(Columns.AUTHORITY, "buildings", BUILDINGS);
        _uriMatcher.addURI(Columns.AUTHORITY, "buildings/#", BUILDING_ID);
//        _uriMatcher.addURI(Columns.AUTHORITY, "live_folders/buildings", LIVE_FOLDER_BUILDINGS);

        _buildingsProjectionMap = new HashMap<String, String>();
        _buildingsProjectionMap.put(Columns._ID, Columns._ID);
        _buildingsProjectionMap.put(Columns.CREATED_DATE, Columns.CREATED_DATE);
        _buildingsProjectionMap.put(Columns.MODIFIED_DATE, Columns.MODIFIED_DATE);
        _buildingsProjectionMap.put(Columns.NAME, Columns.NAME);
        _buildingsProjectionMap.put(Columns.PARTITION_KEY, Columns.PARTITION_KEY);
        _buildingsProjectionMap.put(Columns.ROW_KEY, Columns.ROW_KEY);
        _buildingsProjectionMap.put(Columns.ENTITY_ID, Columns.ENTITY_ID);
        _buildingsProjectionMap.put(Columns.OPEN_DATA_TIMESTAMP, Columns.OPEN_DATA_TIMESTAMP);
        _buildingsProjectionMap.put(Columns.ADDRESS, Columns.ADDRESS);
        _buildingsProjectionMap.put(Columns.NEIGHBOURHOOD, Columns.NEIGHBOURHOOD);
        _buildingsProjectionMap.put(Columns.URL, Columns.URL);
        _buildingsProjectionMap.put(Columns.CONSTRUCTION_DATE, Columns.CONSTRUCTION_DATE);
        _buildingsProjectionMap.put(Columns.LONGITUDE, Columns.LONGITUDE);

        // Support for Live Folders.
//        _liveFolderProjectionMap = new HashMap<String, String>();
//        _liveFolderProjectionMap.put(LiveFolders._ID, Building.Buildings._ID + " AS " + LiveFolders._ID);
//        _liveFolderProjectionMap.put(LiveFolders.NAME, Notes.TITLE + " AS " + LiveFolders.NAME);
        // Add more columns here for more robust Live Folders.
    }

    private DatabaseHelper _openHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String sql = "CREATE TABLE " + BUILDINGS_TABLE_NAME + " (" +
                    Columns._ID + " INTEGER PRIMARY KEY," +
                    Columns.NAME + " TEXT," +
                    Columns.PARTITION_KEY + " TEXT," +
                    Columns.ROW_KEY + " TEXT," +
                    Columns.ENTITY_ID + " TEXT," +
                    Columns.OPEN_DATA_TIMESTAMP + " TEXT," +
                    Columns.ADDRESS + " TEXT, " +
                    Columns.NEIGHBOURHOOD + " TEXT," +
                    Columns.URL + " TEXT," +
                    Columns.CONSTRUCTION_DATE + " TEXT," +
                    Columns.LATITUDE + " TEXT," +
                    Columns.LONGITUDE + " TEXT," +
                    Columns.CREATED_DATE + " TEXT," +
                    Columns.MODIFIED_DATE + " TEXT" +
                    ");";

            sqLiteDatabase.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from " + oldVersion + " to " + newVersion + ", which will destory all data.");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BUILDINGS_TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public boolean onCreate() {
        _openHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(BUILDINGS_TABLE_NAME);

        if (isCollectionUri(uri)) {
            qb.setProjectionMap(_buildingsProjectionMap);
        } else {
            qb.appendWhere(Columns._ID + "=" + uri.getPathSegments().get(1));
        }
        String orderBy;
        if (TextUtils.isEmpty(sort)) {
            orderBy = Columns.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sort;
        }
        SQLiteDatabase db = _openHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (_uriMatcher.match(uri)) {
            case BUILDINGS:
//            case LIVE_FOLDER_BUILDINGS:
//                return Columns.CONTENT_TYPE;
            case BUILDING_ID:
                return Columns.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (_uriMatcher.match(uri) != BUILDINGS)
            throw new IllegalArgumentException("Unknown URI " + uri);
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        for (String colName : getRequiredColumns()) {
            if (!values.containsKey(colName)) {
                throw new IllegalArgumentException("Missing Column: " + colName);
            }
        }
        populateDefaultValues(values);

        SQLiteDatabase db = _openHelper.getWritableDatabase();
        long rowId = db.insert(BUILDINGS_TABLE_NAME, Columns.NAME, values);
        if (rowId > 0) {
            Uri buildingUri = ContentUris.withAppendedId(Columns.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uri, null);
            return buildingUri;
        }

        throw new SQLException("failed to insert row into " + uri);
    }

    private void populateDefaultValues(ContentValues values) {
        Long now = System.currentTimeMillis();
        if (!values.containsKey(Columns.CREATED_DATE)) {
            values.put(Columns.CREATED_DATE, now);
        }
        if (!values.containsKey(Columns.MODIFIED_DATE)) {
            values.put(Columns.MODIFIED_DATE, now);
        }
        if (!values.containsKey(Columns.NAME)) {
            values.put(Columns.NAME, "");
        }
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        int count;
        switch (_uriMatcher.match(uri)) {
            case BUILDINGS:
                count = db.delete(BUILDINGS_TABLE_NAME, where, whereArgs);
                break;
            case BUILDING_ID:
                String segement = uri.getPathSegments().get(1);
                count = db.delete(BUILDINGS_TABLE_NAME, Columns._ID + "=" + segement +
                        (!TextUtils.isEmpty(where) ? "AND (" + where + ')' : ""), whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String where, String[] whereArgs) {
        int count;
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        switch (_uriMatcher.match(uri)) {
            case BUILDINGS:
                count = db.update(BUILDINGS_TABLE_NAME, contentValues, where, whereArgs);
                break;
            case BUILDING_ID:
                String buildingId = uri.getPathSegments().get(1);
                count = db.update(BUILDINGS_TABLE_NAME, contentValues, Columns._ID + "=" + buildingId
                        + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private boolean isCollectionUri(Uri url) {
        return (_uriMatcher.match(url) == BUILDINGS);
    }

    private String[] getRequiredColumns() {
        return new String[]{
                Columns.ROW_KEY,
                Columns.NAME,
                Columns.ADDRESS,
                Columns.LONGITUDE,
                Columns.LATITUDE
        };
    }
}
