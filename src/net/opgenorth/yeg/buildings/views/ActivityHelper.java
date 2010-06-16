package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import net.opgenorth.yeg.buildings.Constants;

public class ActivityHelper {
	private Activity _activity;

	public ActivityHelper(Activity activity) {
		_activity = activity;
	}

	public boolean isDebug() {
		try {
			PackageInfo packageInfo = _activity.getPackageManager().getPackageInfo(_activity.getPackageName(), 0);
			return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE;
		}
		catch (PackageManager.NameNotFoundException e) {
			Log.e(Constants.LOG_TAG, "package name not found", e);
		}
		return false;
	}
}
