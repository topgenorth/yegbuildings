package net.opgenorth.yeg.buildings.data;

import android.content.ContextWrapper;
import android.database.Cursor;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.IBuildingSorter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContentProviderDataService implements IBuildingDataService {
    private ContextWrapper _context;


    public ContentProviderDataService(ContextWrapper _context) {
        this._context = _context;
    }

    @Override
    public List<Building> fetchAll() {
        List<Building> buildings = new ArrayList<Building>(80);

        Cursor c = _context.getContentResolver().query(SqliteContentProvider.Columns.CONTENT_URI, SqliteContentProvider.Columns.ALL_COLUMNS, null, null, null);
        c.moveToFirst();
        do {
            Building building = getBuildingFromRow(c);
            buildings.add(building);
        } while (c.moveToNext());

        c.close();
        return buildings;
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

    @Override
    public List<Building> fetchAll(IBuildingSorter sortedBy) {
        return fetchAll();
    }
}
