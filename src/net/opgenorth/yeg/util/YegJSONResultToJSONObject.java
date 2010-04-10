package net.opgenorth.yeg.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Converts the string to a JSONArray that we can parse elsewhere.
 */
public class YegJSONResultToJSONObject implements ITransmorgifier<String, JSONArray> {
	@Override
	public JSONArray transmorgify(String source) {
		JSONObject root;
		JSONArray jsonBuildings = null;
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
