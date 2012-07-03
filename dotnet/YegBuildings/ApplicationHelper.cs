using Android.App;
using Android.Content;
using Android.Content.PM;
using Android.Util;

namespace net.opgenorth.yegbuildings.m4a
{
    public static class ApplicationHelper
    {
        public static string GetGoogleMapsApiKey(this Fragment fragment)
        {
            var context = fragment.Activity;
            return context.GetGoogleMapsApiKey();
        }

        public static string GetGoogleMapsApiKey(this Context context)
        {
            int resourceIdForGoogleMapsApiKey;
            if (context.IsDebuggable())
            {
                resourceIdForGoogleMapsApiKey = Resource.String.google_maps_api_key_debug;
                Log.Debug(Globals.LogTag, "Using the debug API key for Google Maps.");
            }
            else
            {
                resourceIdForGoogleMapsApiKey = Resource.String.google_maps_api_key_release;
                Log.Debug(Globals.LogTag, "Using the release API key for Google Maps.");
            }
            return context.Resources.GetString(resourceIdForGoogleMapsApiKey);
        }

        public static bool IsDebuggable(this Context context)
        {
            var flags = (int) context.ApplicationInfo.Flags;
            var df = (int) ApplicationInfoFlags.Debuggable;
            var x = flags & df;

            Log.Debug(Globals.LogTag, "ApplicationInfoFlags = {0}, {1} {2}", flags, df, x);

            var isDebuggable = (context.ApplicationInfo.Flags & ApplicationInfoFlags.Debuggable) != 0;
            return isDebuggable;
        }
    }
}