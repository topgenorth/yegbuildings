package net.opgenorth.yeg.historicalbuildings.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import net.opgenorth.yeg.historicalbuildings.R;
import net.opgenorth.yeg.historicalbuildings.model.RelativeBuildingLocation;

import java.util.List;

public class HistoricalBuildingListAdapter extends ArrayAdapter {
	private Activity _context;
	private List<RelativeBuildingLocation> _relativeBuildings;

	public HistoricalBuildingListAdapter(Activity context, List<RelativeBuildingLocation> relativeBuildings) {
		super(context, R.layout.historicalbuildingrow, relativeBuildings);
		_context = context;
		_relativeBuildings = relativeBuildings;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		HistoricalBuildingRowWrapper rowWrapper;

		if (row == null) {
			LayoutInflater inflater = _context.getLayoutInflater();
			row = inflater.inflate(R.layout.historicalbuildingrow, null);
			rowWrapper = new HistoricalBuildingRowWrapper(row);
			row.setTag(rowWrapper);
		}
		else {
			rowWrapper = (HistoricalBuildingRowWrapper) row.getTag();
		}

		if (!_relativeBuildings.isEmpty()) {
			RelativeBuildingLocation relativeBuilding = _relativeBuildings.get(position);
			rowWrapper.display(relativeBuilding);
		}

		if (position % 2 == 0) {
			row.setBackgroundResource(R.color.roweven);
		}
		else {
			row.setBackgroundResource(R.color.rowodd);
		}

		return (row);
	}
}
