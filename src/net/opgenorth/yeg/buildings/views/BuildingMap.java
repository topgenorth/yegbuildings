package net.opgenorth.yeg.buildings.views;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.maps.MapActivity;
import net.opgenorth.yeg.buildings.Constants;
import net.opgenorth.yeg.buildings.R;

public class BuildingMap extends MapActivity implements LocationListener {
    private ActivityHelper _activityHelper = new ActivityHelper(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeContentView();
    }

    private void initializeContentView() {
        if (_activityHelper.isDebug()) {
            Log.d(Constants.LOG_TAG, "Debuggable == TRUE, using building_map_debug.");
            setContentView(R.layout.mapofallbuildings_debug);
        }
        else {
            Log.d(Constants.LOG_TAG, "Debuggable == FALSE, using building_map_production.");
            setContentView(R.layout.mapofallbuildings_production);
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderEnabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderDisabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}