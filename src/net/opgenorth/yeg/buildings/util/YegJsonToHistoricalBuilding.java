package net.opgenorth.yeg.buildings.util;

import net.opgenorth.yeg.buildings.model.Building;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class YegJsonToHistoricalBuilding implements ITransmorgifier<Object, Building> {
	public Building transmorgify(Object object) {
		JSONObject jsonObject = (JSONObject) object;

		Building building = new Building();

		try {
			building.setRowKey(UUID.fromString(jsonObject.getString("RowKey")));
			building.setRowKey(UUID.fromString(jsonObject.getString("entityid")));
			building.setName(jsonObject.getString("name"));
			building.setAddress(jsonObject.getString("address"));
			building.setNeighbourHood(jsonObject.getString("neighbourhood"));
			building.setUrl(jsonObject.optString("url", ""));
			building.setConstructionDate(jsonObject.optString("construction_date", ""));

			double lat = jsonObject.getDouble("latitude");
			double lon = jsonObject.getDouble("longitude");

			building.setLocation(lat, lon);
		}
		catch (JSONException e) {
			e.printStackTrace();
			building = null;
		}
		return building;
	}

}
