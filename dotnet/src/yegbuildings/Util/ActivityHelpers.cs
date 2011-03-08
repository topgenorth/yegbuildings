using System;
using Android.App;
using Android.Content.PM;
using Android.Util;

namespace net.opgenorth.yeg.buildings.Util
{
    public static class ActivityHelpers
    {
        /// <summary>
        /// Tells us if the activity is in debug mode or not.
        /// </summary>
        /// <param name="activity"></param>
        /// <returns></returns>
        public static bool IsDebug(this Activity activity)
        {
            try
            {
                var packageInfo = activity.PackageManager.GetPackageInfo(activity.PackageName, 0);
                return (packageInfo.ApplicationInfo.Flags & ApplicationInfoFlags.Debuggable) == ApplicationInfoFlags.Debuggable;
            }
            catch (Exception e)
            {
                Log.Wtf(Constants.LOG_TAG, "Couldn't tell you if we're debuggable.", e);
                return true;
            }
        }
    }
}