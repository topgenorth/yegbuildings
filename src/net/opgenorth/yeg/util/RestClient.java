package net.opgenorth.yeg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

public class RestClient {
	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	public static List<HistoricalBuilding> connect(String url)
	{
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
				String result= convertStreamToString(inputStream);

				// A Simple JSONObject Creation
				JSONObject json=new JSONObject(result);

 				// A Simple JSONObject Parsing
				JSONArray nameArray=json.names();
				JSONArray jsonBuildings = json.toJSONArray(nameArray).getJSONArray(0);

				for (int i = 0; i < jsonBuildings.length(); i++) {
					JSONObject obj = (JSONObject) jsonBuildings.get(i);
					HistoricalBuilding building = new HistoricalBuilding() ;

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
					// latitude
					// longitude
					buildings.add(building);
				}
				inputStream.close();
			}


		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return buildings;
	}
}