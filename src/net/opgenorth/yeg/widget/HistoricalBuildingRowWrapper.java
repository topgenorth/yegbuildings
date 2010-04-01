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
    private LatLongLocation _location;

    public HistoricalBuildingRowWrapper(View base) {
        _base= base;
    }
    public TextView getNameLabel() {
        if (_nameLabel == null) {
            _nameLabel  = (TextView) _base.findViewById(R.id.buildingName);
        }
        return _nameLabel;
    }
    public TextView getAddressLabel() {
        if (_addressLabel == null)        {
            _addressLabel = (TextView ) _base.findViewById(R.id.buildingAddress);
        }
        return _addressLabel;
    }

    public void display(HistoricalBuilding building) {
        getNameLabel().setText(building.getName() );
        getAddressLabel().setText(building.getAddress());
    }
}
