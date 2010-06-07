package net.opgenorth.yeg.historicalbuildings.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Converts the string to a JSONArray that we can parse elsewhere.
 */
public class YegJSONToJSONObject implements ITransmorgifier<String, JSONArray> {
	@Override
	public JSONArray transmorgify(String source) {
		JSONObject root;
		JSONArray jsonBuildings = null;

        if (source == null) {
            return null;
            
        }
		try {
			root = new JSONObject(source);
			JSONArray nameArray = root.names();
			jsonBuildings = root.toJSONArray(nameArray).getJSONArray(0);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonBuildings;
	}
}
