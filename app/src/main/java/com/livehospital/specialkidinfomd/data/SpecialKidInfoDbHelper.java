package com.livehospital.specialkidinfomd.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 *  Manages the local database
 */
public class SpecialKidInfoDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "specialkidinfo.db";

    public SpecialKidInfoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SERVICE_PROVIDER_TABLE = "CREATE TABLE " + SpecialKidInfoContract.ServiceProviderInfo.TABLE_NAME + " (" +
                SpecialKidInfoContract.ServiceProviderInfo._ID + " INTEGER PRIMARY KEY," +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_NAME + " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_TYPE + " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS + " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER+ " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LAND_LINE_NUMBER + " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_WEB_SITE + " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LOCATION + " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_ADDRESS + " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_REMARK + " TEXT NOT NULL, " +
                SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_SUB_lOCATION + " TEXT NOT NULL " +

                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_SERVICE_PROVIDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SpecialKidInfoContract.ServiceProviderInfo.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}
