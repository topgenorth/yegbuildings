package net.opgenorth.yeg.util;

import android.util.Log;
import net.opgenorth.yeg.Constants;
import net.opgenorth.yeg.model.HistoricalBuilding;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class YegJsonToHistoricalBuilding implements ITransmorgifier<Object, HistoricalBuilding> {
	private ITransmorgifier<String, Date> stringToDate = new YegOpenDataStringToDateTime();

	public HistoricalBuilding transmorgify(Object object) {
		JSONObject jsonObject = (JSONObject) object;

		HistoricalBuilding building = new HistoricalBuilding();

		try {
			building.setPartitionKey(jsonObject.getString("PartitionKey"));
			building.setRowKey(UUID.fromString(jsonObject.getString("RowKey")));
            			
			String dateTimeString = jsonObject.getString("Timestamp");
			building.setTimestamp(stringToDate.transmorgify(dateTimeString));

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
