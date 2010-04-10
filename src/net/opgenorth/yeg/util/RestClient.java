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

public class RestClient {
	private static ITransmorgifier<InputStream, String> inputStreamToString = new InputStreamToString();
	private static ITransmorgifier<String, JSONArray> convertToJSONArray = new StringToJSONObject();
	private static ITransmorgifier<Object, HistoricalBuilding> convertToHistoricalBuilding = new YegJsonToHistoricalBuilding();

	public static List<HistoricalBuilding> connect(String url) {
		List<HistoricalBuilding> buildings = new ArrayList<HistoricalBuilding>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream inputStream = entity.getContent();
				String result = inputStreamToString.transmorgify(inputStream);
				JSONArray jsonBuildings = convertToJSONArray.transmorgify(result);

				for (int i = 0; i < jsonBuildings.length(); i++) {
					JSONObject obj = (JSONObject) jsonBuildings.get(i);
					HistoricalBuilding building = convertToHistoricalBuilding.transmorgify(obj);
					if (building != null) {
						buildings.add(building);
					}
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
