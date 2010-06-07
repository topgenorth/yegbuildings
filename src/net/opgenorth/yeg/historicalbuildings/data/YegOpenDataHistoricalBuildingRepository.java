package net.opgenorth.yeg.historicalbuildings.data;

import net.opgenorth.yeg.historicalbuildings.model.Building;
import net.opgenorth.yeg.historicalbuildings.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves Historical Buildings from the City of Edmonton's Open Data Catalogue.
 */
public class YegOpenDataHistoricalBuildingRepository implements IReadOnlyBuildingRepository {
	public static final String YEG_HISTORIC_DATA_URL = "http://datafeed.edmonton.ca/v1/coe/HistoricalBuildings?format=json";
	private ITransmorgifier<String, JSONArray> _convertToJSONArray = new YegJSONToJSONObject();
	private ITransmorgifier<Object, Building> _convertToHistoricalBuilding = new YegJsonToHistoricalBuilding();
	private IRestClient _restClient = new HttpGetRestClient();

	@Override
	public List<Building> get() {
		ArrayList<Building> buildings = new ArrayList<Building>();
		String jsonResponse = _restClient.getContents(YEG_HISTORIC_DATA_URL);
		if (jsonResponse != null) {
			JSONArray jsonBuildings = _convertToJSONArray.transmorgify(jsonResponse);
			for (int i = 0; i < jsonBuildings.length(); i++) {
				JSONObject obj;
				try {
					obj = (JSONObject) jsonBuildings.get(i);
					Building building = _convertToHistoricalBuilding.transmorgify(obj);
					if (building != null) {
						buildings.add(building);
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return buildings;
	}
}
