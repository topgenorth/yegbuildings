package net.opgenorth.yeg.buildings.data;

import android.content.ContextWrapper;
import android.database.Cursor;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.IBuildingSorter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SQLiteBuildingDataService implements IBuildingDataService {
    private ContextWrapper _context;

    public SQLiteBuildingDataService(ContextWrapper context) {
        this._context = context;
    }

    @Override
    public List<Building> fetchAll() {
        ArrayList<Building> list = new ArrayList<Building>();
        Cursor c = _context.getContentResolver().query(BuildingsContentProvider.Columns.CONTENT_URI, BuildingsContentProvider.Columns.ALL_COLUMNS, null, null, null);

        try {
            c.moveToFirst();
            do {
                Building building = getBuildingFromRow(c);
                list.add(building);
            } while (c.moveToNext());

        } finally {
            c.close();
        }

        return list;
    }

    @Override
    public List<Building> fetchAll(IBuildingSorter sortedBy) {
        return fetchAll();
    }

    @Override
    public boolean hasRecords() {
        // TODO:  there has to be a more efficient way to to this.
        boolean hasRecords = false;
        Cursor c = _context.getContentResolver().query(BuildingsContentProvider.Columns.CONTENT_URI, BuildingsContentProvider.Columns.ALL_COLUMNS, null, null, null);

        try {
            if (c.moveToFirst()) {
                long count = 0;
                do {
                    count++;
                }
                while (c.moveToNext());
                hasRecords = count > 0;
            }
        } catch (Exception ex) {
            hasRecords = false;
        } finally {
            c.close();
        }

        return hasRecords;
    }

    private Building getBuildingFromRow(Cursor c) {
        Building building = new Building();
        building.setId(c.getLong(0));
        building.setName(c.getString(1));
        building.setRowKey(UUID.fromString(c.getString(2)));
        building.setAddress(c.getString(3));
        building.setNeighbourHood(c.getString(4));
        building.setUrl(c.getString(5));
        building.setConstructionDate(c.getString(6));

        String latitude = c.getString(8);
        String longitude = c.getString(7);

        building.setLocation(Double.parseDouble(latitude), Double.parseDouble(longitude));
        return building;
    }
}
