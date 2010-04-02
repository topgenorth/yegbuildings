package net.opgenorth.yeg.widget;

import android.view.View;
import android.widget.TextView;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.model.HistoricalBuilding;
import net.opgenorth.yeg.model.LatLongLocation;

public class HistoricalBuildingRowWrapper {
    private View _base = null;
    private TextView _nameLabel;
    private TextView _addressLabel;
	private TextView _yearBuiltLabel;
    private LatLongLocation _location;

    public HistoricalBuildingRowWrapper(View base) {
        _base= base;
    }
    public TextView getNameLabel() {
        if (_nameLabel == null) {
            _nameLabel  = (TextView) _base.findViewById(R.id.building_row_name);
        }
        return _nameLabel;
    }
    public TextView getAddressLabel() {
        if (_addressLabel == null)        {
            _addressLabel = (TextView ) _base.findViewById(R.id.building_row_address);
        }
        return _addressLabel;
    }
	public TextView getYearBuiltLabel() {
		if (_yearBuiltLabel == null) {
			_yearBuiltLabel = (TextView) _base.findViewById(R.id.building_row_year_built);
		}
		return _yearBuiltLabel;
	}

    public void display(HistoricalBuilding building) {
        getNameLabel().setText(building.getName() );
        getAddressLabel().setText(building.getAddress());
		getYearBuiltLabel().setText("Construction date: " + building.getConstructionDate());
    }
}
