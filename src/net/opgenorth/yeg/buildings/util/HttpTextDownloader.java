package net.opgenorth.yeg.buildings.util;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import net.opgenorth.yeg.buildings.Constants;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;

public class HttpTextDownloader extends IntentService {
	private HttpClient _httpClient = null;
	private File _output;

	public HttpTextDownloader() {
		super(Constants.INTENT_SERVICE_HISTORICAL_BUILDING_DOWNLOAD);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		_httpClient = new DefaultHttpClient();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int result;
		String urlToDownload = intent.getData().toString();
		_output = new File(Environment.getExternalStorageDirectory(), intent.getData().getLastPathSegment());
		byte[] downloadedBytes;
		try {
			downloadedBytes = downloadFile(urlToDownload);
			result = Activity.RESULT_OK;
		}
		catch (Exception ex) {
			Log.e(Constants.LOG_TAG, "Exception in download for " + urlToDownload + " to file " + _output, ex);
			result = Activity.RESULT_CANCELED;
			downloadedBytes = new byte[0];
		}
		sendMessageWithResult(intent, result, downloadedBytes);
	}

	private byte[] downloadFile(String urlToDownload) throws IOException {
		HttpGet getMethod = new HttpGet(urlToDownload);
		ResponseHandler<byte[]> responseHandler = new ByteArrayResponseHandler();
		byte[] bytes = _httpClient.execute(getMethod, responseHandler);
		Log.d(Constants.LOG_TAG, "Downloaded " + bytes.length + " bytes for " + urlToDownload);
		return bytes;
	}

	private void sendMessageWithResult(Intent intent, int result, byte[] bytes) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Messenger messenger = (Messenger) extras.get(Constants.INTENT_SERVICE_DOWNLOAD_MESSENGER);
			Message msg = Message.obtain();
			msg.arg1 = result;
			if ((bytes != null) && bytes.length > 0) {
				msg.obj = new String(bytes);
			}
			else {
				msg.obj = "";
			}
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
