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
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.util.ITransmorgifier;
import net.opgenorth.yeg.buildings.util.InputStreamToString;


import java.io.*;
import java.util.HashMap;

public class BuildingsContentProvider extends ContentProvider {
	public static final String TAG                  = Constants.LOG_TAG;
	public static final String DATABASE_NAME        = "buildings.db";
	public static final int    DATABASE_VERSION     = 5;
	public static final String BUILDINGS_TABLE_NAME = "buildings";

	private static HashMap<String, String> _buildingsProjectionMap;

	private static final int BUILDINGS   = 1;
	private static final int BUILDING_ID = 3;
	private static final UriMatcher _uriMatcher;

	public static final class Columns implements BaseColumns {
		public static final String AUTHORITY = "net.opgenorth.yeg.buildings";

		/**
		 * The content:// style URL for this table
		 */
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/buildings");

		/**
		 * The MIME type of {@link #CONTENT_URI} providing a directory of buildings.
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.opgenorth.yeg.building";

		/**
		 * The MIME type of a {@link #CONTENT_URI} sub-directory of a single building.
		 */
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.opgenorth.yet.building";

		private Columns() {
		}

		public static final String NAME              = "name";
		public static final String ENTITY_ID         = "entityid";
		public static final String ADDRESS           = "address";
		public static final String NEIGHBOURHOOD     = "neighbourhood";
		public static final String URL               = "url";
		public static final String CONSTRUCTION_DATE = "construction_date";
		public static final String LONGITUDE         = "longitude";
		public static final String LATITUDE          = "latitude";

		public static final String[] ALL_COLUMNS = new String[]{
				Columns._ID,
				Columns.NAME,
				Columns.ENTITY_ID,
				Columns.ADDRESS,
				Columns.NEIGHBOURHOOD,
				Columns.URL,
				Columns.CONSTRUCTION_DATE,
				Columns.LONGITUDE,
				Columns.LATITUDE
		};

		private static final String[] REQUIRED_COLUMNS = new String[]{
				Columns.ENTITY_ID,
				Columns.NAME,
				Columns.ADDRESS,
				Columns.LONGITUDE,
				Columns.LATITUDE
		};

		public static final String DEFAULT_SORT_ORDER = NAME;
	}

	static {
		_uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		_uriMatcher.addURI(Columns.AUTHORITY, "buildings", BUILDINGS);
		_uriMatcher.addURI(Columns.AUTHORITY, "buildings/#", BUILDING_ID);

		_buildingsProjectionMap = new HashMap<String, String>();
		_buildingsProjectionMap.put(Columns.ENTITY_ID, Columns.ENTITY_ID);
		_buildingsProjectionMap.put(Columns.NAME, Columns.NAME);
		_buildingsProjectionMap.put(Columns.ADDRESS, Columns.ADDRESS);
		_buildingsProjectionMap.put(Columns.NEIGHBOURHOOD, Columns.NEIGHBOURHOOD);
		_buildingsProjectionMap.put(Columns.URL, Columns.URL);
		_buildingsProjectionMap.put(Columns.CONSTRUCTION_DATE, Columns.CONSTRUCTION_DATE);
		_buildingsProjectionMap.put(Columns.LONGITUDE, Columns.LONGITUDE);
		_buildingsProjectionMap.put(Columns.LATITUDE, Columns.LATITUDE);
		_buildingsProjectionMap.put(Columns._ID, Columns._ID);
	}

	private DatabaseHelper _openHelper;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		private Context _context;

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			_context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase sqLiteDatabase) {
			createBuildingsTable(sqLiteDatabase);
			loadBuildingsTable(sqLiteDatabase);
		}

		private void loadBuildingsTable(SQLiteDatabase sqLiteDatabase) {
			String sqlLoad = getSqlForBuildingData();
			if (sqlLoad.length() > 0) {
				sqLiteDatabase.execSQL(sqlLoad);
				Log.v(TAG, "Loaded the buildings table with SQL.");
			}
		}

		private String getSqlForBuildingData() {
			ITransmorgifier<InputStream, String> inputStreamToString = new InputStreamToString();
			String sql = "";

			try  {
				sql =inputStreamToString.transmorgify(_context.getAssets().open("buildings.sql"));
			}
			catch (IOException ioex)  {
				Log.e(TAG, "Couldn't load buildings.sql", ioex);
				sql = "";
			}
			return sql;
		}

		private void createBuildingsTable(SQLiteDatabase sqLiteDatabase) {
			String sqlCreateTable = "CREATE TABLE " + BUILDINGS_TABLE_NAME + " (" +
					Columns._ID + " INTEGER PRIMARY KEY," +
					Columns.NAME + " TEXT," +
					Columns.ENTITY_ID + " TEXT," +
					Columns.ADDRESS + " TEXT, " +
					Columns.NEIGHBOURHOOD + " TEXT," +
					Columns.URL + " TEXT," +
					Columns.CONSTRUCTION_DATE + " TEXT," +
					Columns.LATITUDE + " DOUBLE," +
					Columns.LONGITUDE + " DOUBLE" +
					");";

			sqLiteDatabase.execSQL(sqlCreateTable);
			Log.v(TAG, "Created table with SQL '" + sqlCreateTable + "'");
		}

		@Override
		public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from " + oldVersion + " to " + newVersion + ", which will destroy all data.");
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
		}
		else {
			qb.appendWhere(Columns._ID + "=" + uri.getPathSegments().get(1));
		}
		String orderBy;
		if (TextUtils.isEmpty(sort)) {
			orderBy = Columns.DEFAULT_SORT_ORDER;
		}
		else {
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
			case BUILDING_ID:
				return Columns.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (_uriMatcher.match(uri) != BUILDINGS) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		}
		else {
			values = new ContentValues();
		}

		for (String colName : getRequiredColumns()) {
			if (!values.containsKey(colName)) {
				throw new IllegalArgumentException("Missing Column: " + colName);
			}
		}

		SQLiteDatabase db = _openHelper.getWritableDatabase();
		long rowId = db.insert(BUILDINGS_TABLE_NAME, Columns.NAME, values);
		if (rowId > 0) {
			Uri buildingUri = ContentUris.withAppendedId(Columns.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
			return buildingUri;
		}

		throw new SQLException("failed to insert row into " + uri);
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
		return Columns.REQUIRED_COLUMNS;
	}
}
