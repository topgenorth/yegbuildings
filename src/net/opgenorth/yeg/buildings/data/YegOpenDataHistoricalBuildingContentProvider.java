package net.opgenorth.yeg.buildings.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class YegOpenDataHistoricalBuildingContentProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getType(Uri uri) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // Nothing to do here - you can't insert into YEG OpenData.
        return null; 
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        // Nothing to do here - you can't delete from YEG OpenData
        return 0;  
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        // Nothing to do here - you can't update the YEG OpenData.
        return 0;  
    }
}
