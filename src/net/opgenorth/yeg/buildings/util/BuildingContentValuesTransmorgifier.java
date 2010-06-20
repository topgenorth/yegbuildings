package net.opgenorth.yeg.buildings.util;

import android.content.ContentValues;
import net.opgenorth.yeg.buildings.data.SqliteContentProvider;
import net.opgenorth.yeg.buildings.model.Building;

public class BuildingContentValuesTransmorgifier implements ITransmorgifier<Building, ContentValues> {
    @Override
    public ContentValues transmorgify(Building source) {
        if (source == null)
            return null;

        ContentValues values = new ContentValues();
        values.put(SqliteContentProvider.Columns.ROW_KEY, source.getRowKey().toString());
        values.put(SqliteContentProvider.Columns.ADDRESS, source.getAddress());
        values.put(SqliteContentProvider.Columns._ID, source.getId());
        values.put(SqliteContentProvider.Columns.CONSTRUCTION_DATE, source.getConstructionDate());
        values.put(SqliteContentProvider.Columns.NAME, source.getName());
        values.put(SqliteContentProvider.Columns.NEIGHBOURHOOD, source.getNeighbourHood());
        values.put(SqliteContentProvider.Columns.URL, source.getUrl());
        values.put(SqliteContentProvider.Columns.LATITUDE, source.getLocation().getLatitude());
        values.put(SqliteContentProvider.Columns.LONGITUDE, source.getLocation().getLongitude());

        return values;
    }
}
