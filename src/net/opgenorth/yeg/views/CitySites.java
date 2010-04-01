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
import net.opgenorth.yeg.util.RestClient;
import net.opgenorth.yeg.widget.HistoricalBuildingListAdapter;

import java.util.List;

public class CitySites extends ListActivity {
	private ProgressDialog _progressDialog;
	private TextView _helloWorldText;
    private ListView _listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		_helloWorldText = (TextView) findViewById(R.id.info);
		loadYegOpenData();
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
            Toast.makeText(this, "TODO: configuration ", Toast.LENGTH_SHORT).show();
        }
        else if (R.id.refreshData == itemId) {
            loadYegOpenData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayYegData(List<HistoricalBuilding> buildings) {
        // TODO maybe sort the data?
        HistoricalBuildingListAdapter adapter = new HistoricalBuildingListAdapter(this, buildings);
        setListAdapter(adapter);        
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        HistoricalBuilding building = (HistoricalBuilding) l.getItemAtPosition(position);
        Intent i = new Intent(CitySites.this, BuildingMap.class );
        building.getLocation().addTo(i);
        i.putExtra("net.opgenorth.yeg.buildingtext", building.getName() );
        startActivity(i);
    }

    private void loadYegOpenData() {
		_progressDialog = ProgressDialog.show(CitySites.this, "Please wait...", "Retrieving data...", true);
		new HistoricalBuildingFetcher().execute();
	}

	class HistoricalBuildingFetcher extends AsyncTask<Void, Void, List<HistoricalBuilding>> {
		public static final String YEG_HISTORIC_DATA_URL = "http://datafeed.edmonton.ca/v1/coe/HistoricalBuildings?format=json";

		@Override
		protected List<HistoricalBuilding> doInBackground(Void... voids) {
			List<HistoricalBuilding> buildings = RestClient.connect(YEG_HISTORIC_DATA_URL);
			return buildings;
		}

		@Override
		protected void onPostExecute(List<HistoricalBuilding> historicalBuildings) {
			_progressDialog.dismiss() ;
			_helloWorldText.setText("Found " + historicalBuildings.size() + " buildings.");
            displayYegData(historicalBuildings);
		}
	}

}
