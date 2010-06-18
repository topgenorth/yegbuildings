package net.opgenorth.yeg.buildings.data;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import net.opgenorth.yeg.buildings.Building;
import net.opgenorth.yeg.buildings.Constants;

import java.util.HashMap;

public class SqliteContentProvider extends ContentProvider {
    public static final String TAG = "SqliteContentProvider";
    public static final String DATABASE_NAME = "buildings.db";
    public static final int DATABASE_VERSION = 1;
    public static final String BUILDINGS_TABLE_NAME = "buildings";

    private static HashMap<String, String> _buildingsProjectionMap;
    private static HashMap<String, String> _liveFolderProjectionMap;

    private static final int BUILDINGS = 1;
    private static final int BUILDING_ID = 2;
    private static final int LIVE_FOLDER_BUILDINGS = 3;

    private static final UriMatcher _uriMatcher;

    private DatabaseHelper _openHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String sql = "CREATE TABLE " + BUILDINGS_TABLE_NAME + " (" +
                    Building.Buildings._ID + " INTEGER PRIMARY KEY," +
                    Building.Buildings.NAME + " TEXT," +
                    Building.Buildings.PARTITION_KEY + " TEXT," +
                    Building.Buildings.ROW_KEY + " TEXT," +
                    Building.Buildings.ENTITY_ID + " TEXT," +
                    Building.Buildings.OPEN_DATA_TIMESTAMP + " TEXT," +
                    Building.Buildings.ADDRESS + " TEXT" +
                    Building.Buildings.NEIGHBOURHOOD + " TEXT," +
                    Building.Buildings.URL + " TEXT," +
                    Building.Buildings.CONSTRUCTION_DATE + " TEXT," +
                    Building.Buildings.LATITUDE + " TEXT," +
                    Building.Buildings.LONGITUDE + " TEXT," +
                    Building.Buildings.CREATED_DATE + " TEXT," +
                    Building.Buildings.MODIFIED_DATE + " TEXT,";

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
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getType(Uri uri) {
        switch (_uriMatcher.match(uri)) {
            case BUILDINGS:
            case LIVE_FOLDER_BUILDINGS:
                return Constants.CONTENT_TYPE;
            case BUILDING_ID:
                return Constants.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (_uriMatcher.match(uri) != BUILDINGS)
            throw new IllegalArgumentException("Unknown URI " + uri);
        ContentValues values;
        if (initialValues != null)      {
            values = new ContentValues(initialValues);
        }
        else {
            values = new ContentValues();
        }

        Long now = Long.valueOf(System.currentTimeMillis() );
        if (values.containsKey(Building.Buildings.CREATED_DATE ) == false ) {
            values.put(Building.Buildings.CREATED_DATE, now);
        }
        if (values.containsKey(Building.Buildings.MODIFIED_DATE) == false ) {
            values.put(Building.Buildings.MODIFIED_DATE, now);
        }
        if (values.containsKey(Building.Buildings.NAME) == false) {
            values.put(Building.Buildings.NAME, "");
        }

        SQLiteDatabase db = _openHelper.getWritableDatabase() ;
        long rowId = db.insert(BUILDINGS_TABLE_NAME, Building.Buildings.NAME, values);
        if (rowId > 0) {
            Uri buildingUri = ContentUris.withAppendedId(Constants.CONTENT_URI, rowId);
            return buildingUri;
        }

        throw new SQLException("failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    static {
        _uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        _uriMatcher.addURI(Constants.AUTHORITY, "notes", BUILDINGS);
        _uriMatcher.addURI(Constants.AUTHORITY, "notes/#", BUILDING_ID);
        _uriMatcher.addURI(Constants.AUTHORITY, "live_folders/notes", LIVE_FOLDER_BUILDINGS);

        _buildingsProjectionMap = new HashMap<String, String>();
//        _buildingsProjectionMap.put(Building.Buildings._ID, Building.Buildings._ID);
//        _buildingsProjectionMap.put(Building.Buildings.NAME, Building.Buildings.NAME);
//        _buildingsProjectionMap.put(Building.Buildings.NOTE, Notes.NOTE);
//        _buildingsProjectionMap.put(Notes.CREATED_DATE, Notes.CREATED_DATE);
//        _buildingsProjectionMap.put(Notes.MODIFIED_DATE, Notes.MODIFIED_DATE);

        // Support for Live Folders.
        _liveFolderProjectionMap = new HashMap<String, String>();
//        _liveFolderProjectionMap.put(LiveFolders._ID, Building.Buildings._ID + " AS " + LiveFolders._ID);
//        _liveFolderProjectionMap.put(LiveFolders.NAME, Notes.TITLE + " AS " + LiveFolders.NAME);
        // Add more columns here for more robust Live Folders.
    }
}
