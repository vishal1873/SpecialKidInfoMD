package com.livehospital.specialkidinfomd.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract.ServiceProviderInfo;

/**
 *
 */
public class SpecialKidInfoProvider extends ContentProvider {

    private static final int SERVICE_PROVIDER = 100;

    private static final int SERVICE_PROVIDER_WITH_LOCATION_AND_TYPE = 101;

    private SpecialKidInfoDbHelper mDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private static final String sTypeAndLocationSelection = ServiceProviderInfo.TABLE_NAME +
            "." + ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_TYPE + " = ? AND " +
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LOCATION + " = ? ";

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SpecialKidInfoContract.CONTENT_AUTHORITY;


        matcher.addURI(authority, SpecialKidInfoContract.PATH_SERVICE_PROVIDER, SERVICE_PROVIDER);
        matcher.addURI(authority,  SpecialKidInfoContract.PATH_SERVICE_PROVIDER+"/*/*", SERVICE_PROVIDER_WITH_LOCATION_AND_TYPE);


        return matcher;
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new SpecialKidInfoDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor = null;
        switch (sUriMatcher.match(uri)) {

            case SERVICE_PROVIDER: {
                retCursor = getServiceProviderInfo(uri, projection, sortOrder);
                break;


            }

            case SERVICE_PROVIDER_WITH_LOCATION_AND_TYPE:{
                String location=  ServiceProviderInfo.getLocationFromUri(uri);
                String type = ServiceProviderInfo.getTypeFromUri(uri);
                selection = sTypeAndLocationSelection;
                selectionArgs = new String[]{type,location};

                retCursor = getServiceProviderInfoWithLocationAndType(uri, projection, selection,selectionArgs, sortOrder);
                break;
            }


        }
        return retCursor;
    }

    private Cursor getServiceProviderInfoWithLocationAndType(Uri uri, String[] projection,
                                                             String selection,  String[] selectionArgs,String sortOrder) {

        Cursor retCursor;



        retCursor = mDbHelper.getReadableDatabase().query( SpecialKidInfoContract.ServiceProviderInfo.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return retCursor;


    }

    private Cursor getServiceProviderInfo(Uri uri, String[] projection, String sortOrder) {

        Cursor retCursor;
        retCursor = mDbHelper.getReadableDatabase().query( SpecialKidInfoContract.ServiceProviderInfo.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
        return retCursor;

    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SERVICE_PROVIDER:
                return SpecialKidInfoContract.ServiceProviderInfo.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case SERVICE_PROVIDER: {
                long _id = db.insert(SpecialKidInfoContract.ServiceProviderInfo.TABLE_NAME, null, contentValues);
                if (_id > 0)
                    returnUri =SpecialKidInfoContract.ServiceProviderInfo.buildServiceProviderUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int ret = mDbHelper.getWritableDatabase().delete(SpecialKidInfoContract.ServiceProviderInfo.TABLE_NAME,null,null);
        return ret;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }


}
