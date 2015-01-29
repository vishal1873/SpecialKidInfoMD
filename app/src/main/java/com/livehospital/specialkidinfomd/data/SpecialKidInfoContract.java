package com.livehospital.specialkidinfomd.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines tables and columns for specialkidsinformation database
 */
public class SpecialKidInfoContract {

    public static final String CONTENT_AUTHORITY = "com.livehospital.specialkidinfomd";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SERVICE_PROVIDER = "serviceprovider";


    /* Inner class that defines the table contents of the serviceprovider table */
    public static final class ServiceProviderInfo implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SERVICE_PROVIDER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_SERVICE_PROVIDER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_SERVICE_PROVIDER;

        // Table name
        public static final String TABLE_NAME = "service_provider_info";

        public static final String COLUMN_SERVICE_PROVIDER_NAME = "service_provider_name";
        public static final String COLUMN_SERVICE_PROVIDER_TYPE= "service_provider_type";

        public static final String COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS= "service_provider_email_address";
        public static final String COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER= "service_provider_mobile_number";
        public static final String COLUMN_SERVICE_PROVIDER_LAND_LINE_NUMBER= "service_provider_land_line_number";
        public static final String COLUMN_SERVICE_PROVIDER_WEB_SITE= "service_provider_website";
        public static final String COLUMN_SERVICE_PROVIDER_LOCATION = "service_provider_location";


        public static Uri buildServiceProviderUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static Uri buildServiceProviderWithType (String type) {
            return CONTENT_URI.buildUpon().appendPath("bangalore").appendPath(type).build();
        }


        public static String getTypeFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }


        public static String getLocationFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

}
