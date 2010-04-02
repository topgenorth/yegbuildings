package net.opgenorth.yeg.views;

import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import net.opgenorth.yeg.widget.GoogleMapPin;

public interface IBuildingMapView {
	TextView getNameLabel();
	TextView getAddressLabel();
	TextView getConstructionDateLabel();

	void setName(String name);
	void setAddress(String address);
	void setConstructionDate(String constructionDate);
	void setCenter(GeoPoint geoPoint);
	void showBuildingOnMap(GoogleMapPin pin);
}
