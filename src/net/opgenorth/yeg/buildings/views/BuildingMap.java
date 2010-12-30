package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.maps.MapActivity;

public class BuildingMap extends MapActivity implements LocationListener {
    private ActivityHelper _activityHelper = new ActivityHelper(this);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        if (_activityHelper.isDebug()) {
            tv.setText("(DEBUG)This will show a buildings on a map.");

        }
        else {
            tv.setText("(PROD)This will show a buildings on a map.");

        }

        setContentView(tv);

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