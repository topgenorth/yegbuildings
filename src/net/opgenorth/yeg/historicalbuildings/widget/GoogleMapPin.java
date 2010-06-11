package net.opgenorth.yeg.historicalbuildings.widget;

import android.content.Intent;
import com.google.android.maps.GeoPoint;
import net.opgenorth.yeg.historicalbuildings.model.Building;
import net.opgenorth.yeg.historicalbuildings.model.LatLongLocation;
import net.opgenorth.yeg.historicalbuildings.views.IBuildingMapView;

public class GoogleMapPin {
    public static final String LATITUDE = "net.opgenorth.yeg.latitude";
    public static final String LONGITUDE = "net.opgenorth.yeg.longitude";
    public static final String BUILDING_ADDRESS = "net.opgenorth.yeg.building_address";
    public static final String BUILDING_NAME = "net.opgenorth.yeg.building_name";
    public static final String BUILDING_CONSTRUCTION_DATE = "net.opgenorth.yeg.building_construction_date";

    private LatLongLocation _location;
    private String _name;
    private String _address;
    private String _constructionDate;

    public GoogleMapPin(Intent intent) {
        _location = new LatLongLocation(intent);
        _name = intent.getStringExtra(BUILDING_NAME);
        _address = intent.getStringExtra(BUILDING_ADDRESS);
        _constructionDate = intent.getStringExtra(BUILDING_CONSTRUCTION_DATE);
    }

    public GoogleMapPin(Building building) {
        _location = building.getLocation();
        _name = building.getName();
        _address = building.getAddress();
        _constructionDate = building.getConstructionDate();
    }


    public void putExtra(Intent intent) {
        intent.putExtra(BUILDING_NAME, _name);
        intent.putExtra(BUILDING_ADDRESS, _address);
        intent.putExtra(BUILDING_CONSTRUCTION_DATE, _constructionDate);
        _location.putExtra(intent);
    }

    @Override
    public String toString() {
        return "GoogleMapPin{" +
                "_location=" + _location +
                ", _name='" + _name + '\'' +
                '}';
    }

    public void putOnMap(IBuildingMapView buildingMapView) {
        buildingMapView.setName(_name);
        buildingMapView.setAddress(_address);
        buildingMapView.setConstructionDate("Construction Date: " + _constructionDate);
        buildingMapView.setCenter(_location.getGeoPoint());
        buildingMapView.showBuildingOnMap(this);
    }

    public GeoPoint getGeoPoint() {
        return _location.getGeoPoint();
    }

    public String getBuildingName() {
        return _name;
    }
}


