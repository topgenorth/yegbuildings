package net.opgenorth.yeg.buildings;

import android.provider.BaseColumns;

public final class Building {

    private Building() {}

    public static final class Buildings implements BaseColumns {
        private Buildings() {}

        public static final String DEFAULT_SORT_ORDER = "modified DESC";
        public static final String NAME = "name";
        public static final String PARTITION_KEY = "partition_key";
        public static final String ROW_KEY = "row_key";
        public static final String ENTITY_ID = "entityid";
        public static final String OPEN_DATA_TIMESTAMP = "open_data_timestamp";
        public static final String ADDRESS = "address";
        public static final String NEIGHBOURHOOD = "neighbourhood";
        public static final String URL = "url";
        public static final String CONSTRUCTION_DATE = "construction_date";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";

        public static final String CREATED_DATE = "created";
        public static final String MODIFIED_DATE = "modified";
    }
}
