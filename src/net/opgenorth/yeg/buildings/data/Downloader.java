package net.opgenorth.yeg.buildings.data;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.util.ByteArrayResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;

public class Downloader extends IntentService {
	private HttpClient _httpClient = null;

	public Downloader() {
		super(Constants.INTENT_SERVICE_HISTORICAL_BUILDING_DOWNLOAD);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		_httpClient = new DefaultHttpClient();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int result = downloadFile(intent);
		sendMessageWithResult(intent, result);
	}

	private int downloadFile(Intent intent) {
		HttpGet getMethod = new HttpGet(intent.getData().toString());
		int result = Activity.RESULT_CANCELED;

		try {
			ResponseHandler<byte[]> responseHandler = new ByteArrayResponseHandler();
			byte[] responseBody = _httpClient.execute(getMethod, responseHandler);
			File output = new File(Environment.getExternalStorageDirectory(), intent.getData().getLastPathSegment());
			if (output.exists()) {
				output.delete();
			}

			FileOutputStream fos = new FileOutputStream(output.getPath());
			fos.write(responseBody);
			fos.close();
			result = Activity.RESULT_OK;
		}
		catch (Exception ex) {
			Log.e(Constants.LOG_TAG, "Exception in download", ex);
		}
		return result;
	}

	private void sendMessageWithResult(Intent intent, int result) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Messenger messenger = (Messenger) extras.get(Constants.INTENT_SERVICE_DOWNLOAD_MESSENGER);
			Message msg = Message.obtain();
			msg.arg1 = result;

			try {
				messenger.send(msg);
			}
			catch (android.os.RemoteException rex) {
				Log.w(Constants.LOG_TAG, "Exception sending message", rex);
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		_httpClient.getConnectionManager().shutdown();
	}
}
