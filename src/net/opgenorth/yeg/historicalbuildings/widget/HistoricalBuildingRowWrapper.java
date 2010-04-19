package net.opgenorth.yeg.historicalbuildings.widget;

import android.view.View;
import android.widget.TextView;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.historicalbuildings.model.BuildingAndLocationWrapper;

public class HistoricalBuildingRowWrapper {
	private View _base = null;
	private TextView _nameLabel;
	private TextView _addressLabel;
	private TextView _yearBuiltLabel;
	private TextView _distanceToMeLabel;

	public HistoricalBuildingRowWrapper(View base) {
		_base = base;
	}

	public TextView getNameLabel() {
		if (_nameLabel == null) {
			_nameLabel = (TextView) _base.findViewById(R.id.building_row_name);
		}
		return _nameLabel;
	}

	public TextView getAddressLabel() {
		if (_addressLabel == null) {
			_addressLabel = (TextView) _base.findViewById(R.id.building_row_address);
		}
		return _addressLabel;
	}

	public TextView getYearBuiltLabel() {
		if (_yearBuiltLabel == null) {
			_yearBuiltLabel = (TextView) _base.findViewById(R.id.building_row_year_built);
		}
		return _yearBuiltLabel;
	}

	public TextView getDistanceToMeLabel() {
		if (_distanceToMeLabel == null) {
			_distanceToMeLabel = (TextView) _base.findViewById(R.id.building_distance_to_me);
		}
		return _distanceToMeLabel;
	}

	public void display(BuildingAndLocationWrapper building) {
		getNameLabel().setText(building.getHistoricalBuilding().getName());
		getAddressLabel().setText(building.getHistoricalBuilding().getAddress());
		getYearBuiltLabel().setText("Construction date: " + building.getHistoricalBuilding().getConstructionDate());

		if (building.getDistance() < 1) {
			getDistanceToMeLabel().setVisibility(View.GONE);
		}
		else {
			getDistanceToMeLabel().setVisibility(View.VISIBLE);
			getDistanceToMeLabel().setText("Distance: " + building.getDistance() + " metres.");
		}
	}
}
