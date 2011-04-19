package net.opgenorth.yeg.buildings.tests.util;

import android.content.ContentValues;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.util.BuildingContentValuesTransmorgifier;
import org.junit.*;

import java.util.UUID;

public class BuildingContentValuesTransmorgifierTests {

    private ContentValues _contentValues;
    private UUID _rowKey = UUID.randomUUID();
    @Before
    public void setup() {
        Building _building = new Building();
        _building.setAddress("66 Mockingbird Lane");
        _building.setConstructionDate("2002");
        _building.setLocation(53.1, 113.2);
        _building.setName("A Building");
        _building.setNeighbourHood("Nice Neighbourhood");
        _building.setId(123456L);
        _building.setRowKey(_rowKey);
        _building.setUrl("http://www.google.com");

        BuildingContentValuesTransmorgifier transmorgifier = new BuildingContentValuesTransmorgifier();
        _contentValues = transmorgifier.transmorgify(_building);
    }
    @Test
    public void shouldConvertAddress() {

    }
}
