package net.opgenorth.yeg.buildings.views;

import android.widget.TextView;
import com.google.android.maps.GeoPoint;

public interface IBuildingMapView {
	TextView getNameLabel();
	TextView getAddressLabel();
	TextView getConstructionDateLabel();

	void setName(String name);
	void setAddress(String address);
	void setConstructionDate(String constructionDate);
	void setCenter(GeoPoint geoPoint);
}
