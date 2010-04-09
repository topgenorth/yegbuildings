package net.opgenorth.yeg.util;

import net.opgenorth.yeg.model.HistoricalBuilding;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestClient {
	private static ITransmorgifier<InputStream, String> inputStreamToString = new InputStreamToStringTransmorgifier();

	public static List<HistoricalBuilding> connect(String url) {
		List<HistoricalBuilding> buildings = new ArrayList<HistoricalBuilding>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		HttpResponse response;
		try {
			response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream inputStream = entity.getContent();
				String result = inputStreamToString.transmorgify(inputStream);

				// A Simple JSONObject Creation
				JSONObject json = new JSONObject(result);

				// A Simple JSONObject Parsing
				JSONArray nameArray = json.names();
				JSONArray jsonBuildings = json.toJSONArray(nameArray).getJSONArray(0);

				for (int i = 0; i < jsonBuildings.length(); i++) {
					JSONObject obj = (JSONObject) jsonBuildings.get(i);
					HistoricalBuilding building = new HistoricalBuilding();

					building.setPartitionKey(obj.getString("PartitionKey"));
					building.setRowKey(UUID.fromString(obj.getString("RowKey")));
					// Timestamp
					building.setRowKey(UUID.fromString(obj.getString("entityid")));
					building.setName(obj.getString("name"));
					building.setAddress(obj.getString("address"));
					building.setNeighbourHood(obj.getString("neighbourhood"));
					building.setUrl(obj.optString("url", ""));
					building.setConstructionDate(obj.optString("construction_date", ""));

					double lat = obj.getDouble("latitude");
					double lon = obj.getDouble("longitude");

					building.setLocation(lat, lon);
					buildings.add(building);
				}
				inputStream.close();
			}

		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return buildings;
	}
}
