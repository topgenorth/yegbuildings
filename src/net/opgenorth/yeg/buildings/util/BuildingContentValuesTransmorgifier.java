package net.opgenorth.yeg.buildings.util;

import android.content.ContentValues;
import net.opgenorth.yeg.buildings.data.BuildingsContentProvider;
import net.opgenorth.yeg.buildings.model.Building;

public class BuildingContentValuesTransmorgifier implements ITransmorgifier<Building, ContentValues> {
    @Override
    public ContentValues transmorgify(Building source) {
        if (source == null)
            return null;

        ContentValues values = new ContentValues();
        values.put(BuildingsContentProvider.Columns.ENTITY_ID, source.getRowKey().toString());
        values.put(BuildingsContentProvider.Columns.ADDRESS, source.getAddress());
        values.put(BuildingsContentProvider.Columns._ID, source.getId());
        values.put(BuildingsContentProvider.Columns.CONSTRUCTION_DATE, source.getConstructionDate());
        values.put(BuildingsContentProvider.Columns.NAME, source.getName());
        values.put(BuildingsContentProvider.Columns.NEIGHBOURHOOD, source.getNeighbourHood());
        values.put(BuildingsContentProvider.Columns.URL, source.getUrl());
        values.put(BuildingsContentProvider.Columns.LATITUDE, source.getLocation().getLatitude());
        values.put(BuildingsContentProvider.Columns.LONGITUDE, source.getLocation().getLongitude());

        return values;
    }
}
