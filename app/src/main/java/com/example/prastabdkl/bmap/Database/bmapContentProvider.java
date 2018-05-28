package com.example.prastabdkl.bmap.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Prastab Dhakal on 1/8/2017.
 */

public class bmapContentProvider extends ContentProvider {


    // Create Uri matcher used by this content provider
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DBHelper mOpenHelper;
    static final int WORKER = 100;

    /**
     * Here we create UriMatcher for Worker table.
     * @return matcher
     */
    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher= new UriMatcher(sUriMatcher.NO_MATCH);
        final String authority = bmapContract.CONTENT_AUTHORITY;

        // For each type of Uri, we want to create corresponding code
        matcher.addURI(authority, bmapContract.PATH_WORKER, WORKER);


        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Cursor returnCursor;

        switch (sUriMatcher.match(uri)){
            case WORKER: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                    bmapContract.WorkerEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Create a new database
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case WORKER : {
                long _id = db.insert(bmapContract.WorkerEntry.TABLE_NAME, null, values);
                if (_id > 0){
                    returnUri = bmapContract.WorkerEntry.buildWorkerUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }
}
