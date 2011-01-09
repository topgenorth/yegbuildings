package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.data.BuildingsContentProvider;
import net.opgenorth.yeg.buildings.data.HistoricalBuildingsCSVToBuildings;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.util.BuildingContentValuesTransmorgifier;
import net.opgenorth.yeg.buildings.util.HttpTextDownloader;
import net.opgenorth.yeg.buildings.util.ITransmorgifier;

import java.util.List;

public class Main extends TabActivity {
	private ProgressDialog _progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(getApplication()).inflate(R.menu.main, menu);
		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (R.id.main_menu_refreshdata == itemId) {
			Intent i = createIntentToReloadYegHistoricalBuildings();
			_progressDialog = ProgressDialog.show(Main.this, "Please wait...", "Downloading list");
			startService(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private Intent createIntentToReloadYegHistoricalBuildings() {
		Intent i = new Intent(this, HttpTextDownloader.class);
		i.setData(Uri.parse("http://data.edmonton.ca/DataBrowser/DownloadCsv?container=coe&entitySet=HistoricalBuildings&filter=NOFILTER"));
		i.putExtra(Constants.INTENT_SERVICE_DOWNLOAD_MESSENGER, new Messenger(_downloadCompleteHandler));
		return i;
	}

	private void setupTabs() {
		Resources appResources = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// Create an Intent to launch an activity for the "Map" tab
		intent = new Intent().setClass(this, BuildingMap.class);
		spec = tabHost.newTabSpec("map").setIndicator(
				appResources.getString(R.string.buildingmap_tabtext),
				appResources.getDrawable(R.drawable.maps2))
				.setContent(intent);
		tabHost.addTab(spec);

		// Create an Intent to launch an activity for the "List" tab
		intent = new Intent().setClass(this, BuildingList.class);
		spec = tabHost.newTabSpec("list").setIndicator(
				appResources.getString(R.string.buildinglist_tabtext),
				appResources.getDrawable(R.drawable.ic_tab_list))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}


	private Handler _downloadCompleteHandler = new Handler() {
		private ITransmorgifier<Building, ContentValues> _toContentValues = new BuildingContentValuesTransmorgifier();
		@Override
		public void handleMessage(Message msg) {
			String message;
			if (msg.arg1 == Activity.RESULT_OK) {
				String csv = (String) msg.obj;
				Log.d(Constants.LOG_TAG, "Downloaded " + csv.length() + " bytes.");
				ITransmorgifier<String, List<Building>> transmorgifier = new HistoricalBuildingsCSVToBuildings();
				List<Building> buildings = transmorgifier.transmorgify(csv);

				Log.d(Constants.LOG_TAG, "Downloaded " + buildings.size() + " buildings. Now to add them to the database.");

				// TODO this is a cheat:  just blast the contents of the whole database
				int rowsDeleted = getContentResolver().delete(BuildingsContentProvider.Columns.CONTENT_URI, "_id!=-1", null);
				Log.d(Constants.LOG_TAG, "Deleted " + rowsDeleted + " rows from the buildings table.");
				for (Building building : buildings) {
					ContentValues contentValues = _toContentValues.transmorgify(building);
					getContentResolver().insert(BuildingsContentProvider.Columns.CONTENT_URI, contentValues);
				}
				Log.d(Constants.LOG_TAG, "Added " + buildings.size() + " to the buildings table.");
				message = "Done.";
			}
			else {
				message = "Looks like the download didn't work.";
			}
			_progressDialog.dismiss();
			Toast.makeText(Main.this, message, Toast.LENGTH_SHORT).show();
		}
	};
}