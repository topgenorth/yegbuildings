package net.opgenorth.yeg.views;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.model.LatLongLocation;

public class BuildingMap extends MapActivity {

    private MapView _map = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_map);
        _map = (MapView) findViewById(R.id.map);
        _map.getController().setZoom(17);

        LatLongLocation location = new LatLongLocation(getIntent());
        _map.getController().setCenter(location.createGeoPoint());

        _map.setBuiltInZoomControls(true);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
