package net.opgenorth.yeg.buildings.views;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.maps.*;
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.data.ContentProviderDataService;
import net.opgenorth.yeg.buildings.data.IBuildingDataService;
import net.opgenorth.yeg.buildings.model.Building;

import java.util.ArrayList;
import java.util.List;


public class MapOfAllBuildings extends MapActivity {
    private List<Building> _buildingList = new ArrayList<Building>(76);
    public static final GeoPoint CENTRE_OF_MAP = new GeoPoint(53542700, -113493320);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeContentView();
        IBuildingDataService svc = new ContentProviderDataService(this);
        _buildingList = svc.fetchAll();
        for (Building building : _buildingList) {
            putPinOnMap(building);
        }

    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    private void putPinOnMap(Building building) {
//        Intent intent = getIntent();
//        LatLongLocation location = new LatLongLocation(intent);
//        String name = intent.getStringExtra(RelativeBuildingLocation.BUILDING_NAME);
//        String address = intent.getStringExtra(RelativeBuildingLocation.BUILDING_ADDRESS);
//        String constructionDate = "Construction Date: " +  intent.getStringExtra(RelativeBuildingLocation.BUILDING_CONSTRUCTION_DATE);
//        GeoPoint buildingLocation = location.getGeoPoint();
        GeoPoint buildingLocation = building.getLocation().getGeoPoint();
        String name = building.getName();
        MapView mapView = (MapView) findViewById(R.id.mapofallbuildings);


        Drawable buildingMarker = getResources().getDrawable(R.drawable.marker);
        buildingMarker.setBounds(0, 0, buildingMarker.getIntrinsicWidth(), buildingMarker.getIntrinsicHeight());
        ItemizedOverlay<OverlayItem> buildingOverlay = new BuildingLocationOverlay(buildingMarker, buildingLocation, name);
        mapView.getOverlays().add(buildingOverlay);
    }


    private void initializeContentView() {
        ActivityHelper helper = new ActivityHelper(this);

        if (helper.isDebug()) {
            Log.d(Constants.LOG_TAG, "Debuggable == TRUE, using mapofallbuildings_debug.");
            setContentView(R.layout.mapofallbuildings_debug);
        } else {
            Log.d(Constants.LOG_TAG, "Debuggable == FALSE, using mapofallbuildings_production.");
            setContentView(R.layout.mapofallbuildings_production);
        }

        MapView mapView = (MapView) findViewById(R.id.mapofallbuildings);
        mapView.setBuiltInZoomControls(true);
        mapView.displayZoomControls(true);
        mapView.getController().setZoom(13);
        mapView.getController().animateTo(CENTRE_OF_MAP);
    }

    private class BuildingLocationOverlay extends ItemizedOverlay<OverlayItem> {
        private OverlayItem _overlayItem = null;
        private Drawable _marker = null;

        public BuildingLocationOverlay(Drawable marker, GeoPoint buildingLocation, String buildingName) {
            super(marker);
            _marker = marker;
            _overlayItem = new OverlayItem(buildingLocation, "Building", buildingName);
            populate();
        }

        @Override
        protected OverlayItem createItem(int i) {
            return (_overlayItem);
        }

        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            super.draw(canvas, mapView, shadow);
            boundCenterBottom(_marker);
        }

        @Override
        protected boolean onTap(int i) {
            Toast.makeText(MapOfAllBuildings.this, _overlayItem.getSnippet(), Toast.LENGTH_SHORT).show();
            return (true);
        }

        @Override
        public int size() {
            return (1);
        }

    }

}



