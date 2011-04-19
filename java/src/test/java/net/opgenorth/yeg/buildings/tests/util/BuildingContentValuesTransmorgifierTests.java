package net.opgenorth.yeg.buildings.tests.util;

import android.content.ContentValues;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import net.opgenorth.yeg.buildings.data.BuildingsContentProvider;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.util.BuildingContentValuesTransmorgifier;
import org.junit.*;
import org.junit.runner.RunWith;


import java.util.UUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
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
    public void shouldPutConstructionDateIntoContentValues() {
        assertThat("2002", equalTo(_contentValues.getAsString(BuildingsContentProvider.Columns.CONSTRUCTION_DATE)));
    }

    @Test()
    public void shouldPutAddressIntoContentValues() {
        assertThat("66 Mockingbird Lane", equalTo(_contentValues.getAsString(BuildingsContentProvider.Columns.ADDRESS)));
    }
}
