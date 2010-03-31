package net.opgenorth.yeg.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import net.opgenorth.yeg.R;
import net.opgenorth.yeg.model.HistoricalBuilding;

import java.util.List;

public class HistoricalBuildingListAdapter extends ArrayAdapter {
    private Activity _context;
    private List<HistoricalBuilding> _buildings;

    public HistoricalBuildingListAdapter(Activity context, List<HistoricalBuilding> buildings) {
        super(context, R.layout.historicalbuildingrow, buildings);
        _context = context;
        _buildings = buildings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoricalBuildingRowWrapper rowWrapper;

        if (row == null) {
            LayoutInflater inflater = _context.getLayoutInflater() ;
            row = inflater.inflate(R.layout.historicalbuildingrow, null);
            rowWrapper = new HistoricalBuildingRowWrapper(row);
            row.setTag(rowWrapper);
        }
        else {
            rowWrapper = (HistoricalBuildingRowWrapper) row.getTag();
        }

        if (!_buildings.isEmpty() ) {
            HistoricalBuilding building = _buildings.get(position);
            rowWrapper.display(building);
        }

        return (row);
    }
}
