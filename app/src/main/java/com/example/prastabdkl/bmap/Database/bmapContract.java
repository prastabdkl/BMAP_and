package com.example.prastabdkl.bmap.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Prastab Dhakal on 1/8/2017.
 */

public class bmapContract {
    /*
    *   The "Content Authority" is the name of entire content provider similar
    *   to the relationship between our domain name and its site.
    *   A convenient string to use for the content authority is the package name for the app
    *   which is guaranteed to be unique on the device.
    */
    public static final String CONTENT_AUTHORITY = "com.example.bmap.provider";

    /*
    * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    * the content provider.
    * */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_WORKER = "worker";

    /*
    * Inner class to define the contents of worker table
    * */
    public static final class WorkerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORKER;

        //Table name
        public static final String TABLE_NAME = "Worker";

        //Column food item name
        public static final String COLUMN_ID =  "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_BANK = "bank_id";
        public static final String COLUMN_CITY = "place";
        public static final String COLUMN_PHONE = "phone";

        public static Uri buildWorkerUri(long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
