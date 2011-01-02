package net.opgenorth.yeg.buildings.widget;

import android.view.View;
import android.widget.TextView;
import net.opgenorth.yeg.buildings.R;
import net.opgenorth.yeg.buildings.model.RelativeBuildingLocation;

import java.text.DecimalFormat;

public class BuildingRowWrapper {
	private View _base = null;
	private TextView _nameLabel;
	private TextView _addressLabel;
	private TextView _yearBuiltLabel;
	private TextView _distanceToMeLabel;

	public BuildingRowWrapper(View base) {
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

	public void display(RelativeBuildingLocation relativeBuilding) {
		getNameLabel().setText(relativeBuilding.getBuilding().getName());
		getAddressLabel().setText(relativeBuilding.getBuilding().getAddress());
		getYearBuiltLabel().setText("Construction date: " + relativeBuilding.getBuilding().getConstructionDate());

		if (relativeBuilding.getDistance() < 1) {
			getDistanceToMeLabel().setVisibility(View.GONE);
		}
		else {
			// TODO  refactor out the number formatting.
			DecimalFormat formatter;
			String units;
			double distance = relativeBuilding.getDistance();
			if (distance < 1000) {
				units = " m.";
				formatter = new DecimalFormat("####");
			}
			else {
				units = " km.";
				distance = distance / 1000;
				if (distance < 100) {
					formatter = new DecimalFormat("####.#");
				}
				else {
					formatter = new DecimalFormat("#####");
				}
			}

			getDistanceToMeLabel().setVisibility(View.VISIBLE);
			getDistanceToMeLabel().setText("Distance: " + formatter.format(distance) + units);
		}
	}
}
