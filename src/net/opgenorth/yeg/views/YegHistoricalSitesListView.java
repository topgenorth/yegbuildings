package net.opgenorth.yeg.views;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.model.HistoricalBuilding;
import net.opgenorth.yeg.util.IHistoricalBuildingsRepository;
import net.opgenorth.yeg.util.YegOpenDataHistoricalBuildingRepository;
import net.opgenorth.yeg.widget.GoogleMapPin;
import net.opgenorth.yeg.widget.HistoricalBuildingListAdapter;

import java.util.List;

public class YegHistoricalSitesListView extends ListActivity {
	private ProgressDialog _progressDialog;
	private TextView _foundHistoricalBuildingsTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		_foundHistoricalBuildingsTextView = (TextView) findViewById(R.id.info);
		loadYegOpenData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(getApplication()).inflate(R.menu.main, menu);
		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (R.id.configureSettings == itemId) {
			Toast.makeText(this, "Settings: Coming in a future version.", Toast.LENGTH_SHORT).show();
		}
		else if (R.id.refreshData == itemId) {
			loadYegOpenData();
		}
        else if (R.id.showAllOnMap == itemId) {
            Toast.makeText(this, "Map All: Coming in a future version.", Toast.LENGTH_SHORT).show();
        }
		return super.onOptionsItemSelected(item);
	}

	private void displayYegData(List<HistoricalBuilding> buildings) {
		HistoricalBuildingListAdapter adapter = new HistoricalBuildingListAdapter(this, buildings);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		HistoricalBuilding building = (HistoricalBuilding) l.getItemAtPosition(position);

		Intent intent = new Intent(YegHistoricalSitesListView.this, BuildingMap.class);

		GoogleMapPin mapPin = new GoogleMapPin(building);
		mapPin.putExtra(intent);

		startActivity(intent);
	}

	private void loadYegOpenData() {
		_progressDialog = ProgressDialog.show(YegHistoricalSitesListView.this, "Please wait...", "Retrieving data...", true);
		new HistoricalBuildingFetcher().execute();
	}

	class HistoricalBuildingFetcher extends AsyncTask<Void, Void, List<HistoricalBuilding>> {
		private IHistoricalBuildingsRepository _repository = new YegOpenDataHistoricalBuildingRepository();
		@Override
		protected List<HistoricalBuilding> doInBackground(Void... voids) {
			return _repository.get();
		}

		@Override
		protected void onPostExecute(List<HistoricalBuilding> historicalBuildings) {
			_progressDialog.dismiss();
			_foundHistoricalBuildingsTextView.setText("Found " + historicalBuildings.size() + " buildings.");
			displayYegData(historicalBuildings);
		}
	}

}
