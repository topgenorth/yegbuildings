using System;
using Android.Provider;
using Uri = Android.Net.Uri;

namespace Net.Opgenorth.Yeg.Buildings.Data
{
    public static class Columns
    {
        public static readonly String AUTHORITY = "net.opgenorth.yeg.buildings";
        public static readonly Uri CONTENT_URI = Uri.Parse("content://" + AUTHORITY + "/buildings");
        public static readonly String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.opgenorth.yeg.building";
        public static readonly String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.opgenorth.yet.building";
        public static readonly String _ID = BaseColumnsConsts.Id;
        public static readonly String NAME = "name";
        public static readonly String ENTITY_ID = "entityid";
        public static readonly String ADDRESS = "address";
        public static readonly String NEIGHBOURHOOD = "neighbourhood";
        public static readonly String URL = "url";
        public static readonly String CONSTRUCTION_DATE = "construction_date";
        public static readonly String LONGITUDE = "longitude";
        public static readonly String LATITUDE = "latitude";

        public static readonly String[] ALL_COLUMNS = new[]
                                                          {
                                                              _ID,
                                                              NAME,
                                                              ENTITY_ID,
                                                              ADDRESS,
                                                              NEIGHBOURHOOD,
                                                              URL,
                                                              CONSTRUCTION_DATE,
                                                              LONGITUDE,
                                                              LATITUDE
                                                          };

        public  static readonly String[] REQUIRED_COLUMNS = new[]
                                                                {
                                                                    ENTITY_ID,
                                                                    NAME,
                                                                    ADDRESS,
                                                                    LONGITUDE,
                                                                    LATITUDE
                                                                };

        public static readonly String DEFAULT_SORT_ORDER = NAME;
    }
}