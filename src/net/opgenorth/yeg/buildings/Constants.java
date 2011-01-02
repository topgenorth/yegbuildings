package net.opgenorth.yeg.buildings;

public final class Constants {
	public static final String LOG_TAG                               = "net.opgenorth.yeg.buildings";
	public static final String INTENT_LATITUDE_KEY                   = LOG_TAG + ".latitude";
	public static final String INTENT_LONGITUDE_KEY                  = LOG_TAG + ".longitude";
	public static final String INTENT_BUILDING_ADDRESS_KEY           = LOG_TAG + ".building_address";
	public static final String INTENT_BUILDING_NAME_KEY              = LOG_TAG + ".building_name";
	public static final String INTENT_BUILDING_CONSTRUCTION_DATE_KEY = LOG_TAG + ".building_construction_date";
	public static final String PREF_LAST_LAT                         = LOG_TAG + ".last_known_latitude";
	public static final String PREF_LAST_LON                         = LOG_TAG + ".last_known_longitude";
	public static final String PREF_LAST_MAP_ZOOM                    = LOG_TAG + ".last_map_zoom";
	public static final int    PREF_DEFAULT_LAT                      = 53544643;
	public static final int    PREF_DEFAULT_LON                      = -113490060;
	public static final int    PREF_DEFAULT_MAP_ZOOM                 = 14;

	private Constants() {
	}


}
