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
            }
            else
            {
                resourceIdForGoogleMapsApiKey = Resource.String.google_maps_api_key_release;
            }
            return context.Resources.GetString(resourceIdForGoogleMapsApiKey);
        }

        /// <summary>
        ///   A helper method that will determine if the application is debuggable or not.
        /// </summary>
        /// <param name="context"> </param>
        /// <returns> The valid of the android:debuggable attribute in AndroidManifest.XML </returns>
        public static bool IsDebuggable(this Context context)
        {
            var isDebuggable = (context.ApplicationInfo.Flags & ApplicationInfoFlags.Debuggable) != 0;
            return isDebuggable;
        }
    }
}