using System;

namespace net.opgenorth.yeg.buildings
{
    public static class Constants
    {
        public static readonly String HISTORICAL_BUILDINGS_CSV_URL = @"http://data.edmonton.ca/DataBrowser/DownloadCsv?container=coe&entitySet=HistoricalBuildings&filter=NOFILTER";
        public static readonly String LOG_TAG = "net.opgenorth.yeg.buildings";
        public static readonly String INTENT_LATITUDE_KEY = LOG_TAG + ".latitude";
        public static readonly String INTENT_LONGITUDE_KEY = LOG_TAG + ".longitude";
        public static readonly String INTENT_BUILDING_ADDRESS_KEY = LOG_TAG + ".building_address";
        public static readonly String INTENT_BUILDING_NAME_KEY = LOG_TAG + ".building_name";
        public static readonly String INTENT_BUILDING_CONSTRUCTION_DATE_KEY = LOG_TAG + ".building_construction_date";
        public static readonly String PREF_LAST_LAT = LOG_TAG + ".last_known_latitude";
        public static readonly String PREF_LAST_LON = LOG_TAG + ".last_known_longitude";
        public static readonly String PREF_LAST_MAP_ZOOM = LOG_TAG + ".last_map_zoom";
        public static readonly String INTENT_SERVICE_HISTORICAL_BUILDING_DOWNLOAD = LOG_TAG + ".download.yeg.data";
        public static readonly String INTENT_SERVICE_DOWNLOAD_MESSENGER = LOG_TAG + "download.messenger";
        public static readonly int PREF_DEFAULT_LAT = 53544643;
        public static readonly int PREF_DEFAULT_LON = -113490060;
        public static readonly int PREF_DEFAULT_MAP_ZOOM = 14;
    }
}