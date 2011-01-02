package net.opgenorth.yeg.buildings.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.model.Building;
import net.opgenorth.yeg.buildings.model.RelativeBuildingLocation;

import java.util.ArrayList;
import java.util.List;

public class BuildingListAdapter extends ArrayAdapter {
	private Activity                       _context;
	private List<RelativeBuildingLocation> _relativeBuildings;
	private List<Building>                 _buildings;

	public BuildingListAdapter(Activity context, List<RelativeBuildingLocation> relativeBuildings) {
		super(context, R.layout.historicalbuildingrow, relativeBuildings);
		_context = context;
		_relativeBuildings = relativeBuildings;
		_buildings = new ArrayList<Building>(relativeBuildings.size());
		for (RelativeBuildingLocation building : relativeBuildings) {
			_buildings.add(building.getBuilding());
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		BuildingRowWrapper rowWrapper;

		if (row == null) {
			LayoutInflater layoutInflater = _context.getLayoutInflater();
			row = layoutInflater.inflate(R.layout.historicalbuildingrow, null);
			rowWrapper = new BuildingRowWrapper(row);
			row.setTag(rowWrapper);
		}
		else {
			rowWrapper = (BuildingRowWrapper) row.getTag();
		}

		if (!_relativeBuildings.isEmpty()) {
			RelativeBuildingLocation relativeBuilding = _relativeBuildings.get(position);
			rowWrapper.display(relativeBuilding);
		}

		return (row);
	}

}
