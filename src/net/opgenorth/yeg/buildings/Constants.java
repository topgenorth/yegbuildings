package net.opgenorth.yeg.buildings;

import android.net.Uri;

public final class Constants {
    private Constants() {}
    public static final String AUTHORITY = "net.opgenorth.yeg.buildings";
	public static final String LOG_TAG = "net.opgenorth.yeg.buildings";

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/buildings");

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of buildings.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.building";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single building.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.building";
}
