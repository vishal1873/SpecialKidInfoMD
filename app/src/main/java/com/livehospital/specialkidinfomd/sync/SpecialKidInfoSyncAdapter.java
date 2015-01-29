package com.livehospital.specialkidinfomd.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.appspot.specialkidinfo.specialkidinfoapi.Specialkidinfoapi;
import com.appspot.specialkidinfo.specialkidinfoapi.model.ServiceProvider;
import com.appspot.specialkidinfo.specialkidinfoapi.model.ServiceProviderCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.livehospital.specialkidinfomd.R;
import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract;

import java.util.List;


public class SpecialKidInfoSyncAdapter extends AbstractThreadedSyncAdapter {



    // Interval at which to com.livehospital.specialkidinfomd.sync with the weather, in milliseconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 24*60*60;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;



    public final static String LOG_TAG = SpecialKidInfoSyncAdapter.class.getSimpleName();

    public SpecialKidInfoSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting com.livehospital.specialkidinfomd.sync");


        try {

            String type = "school";
            Specialkidinfoapi.Builder builder = new Specialkidinfoapi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            Specialkidinfoapi  api =  builder.build();

            ServiceProviderCollection response = api.getAllProviders().execute();


            //TODO to delete the database first and then add records

            //TODOD delete method needs to written properly in the provider
            getContext().getContentResolver().delete(SpecialKidInfoContract.ServiceProviderInfo.CONTENT_URI,null,null);

            //TODO to add batch insert
            for (ServiceProvider sp:response.getItems())
            {
                ContentValues data = new ContentValues();
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_NAME, sp.getName());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_TYPE, sp.getType());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS, sp.getEmailAddress());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER, sp.getMobileNumber());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LAND_LINE_NUMBER, sp.getLandlineNumber());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_WEB_SITE, sp.getWebsite());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LOCATION, sp.getLocation());

                Log.d(LOG_TAG, "The service provider"+sp.toString());

                Uri serviceProviderURI = getContext().getContentResolver().insert(SpecialKidInfoContract.ServiceProviderInfo.CONTENT_URI, data);
                Log.d(LOG_TAG, "The newly created serviceProviderURI is "+serviceProviderURI);

            }



        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage(), e);
        }

        return;
    }



    /**
     * Helper method to have the com.livehospital.specialkidinfomd.sync adapter com.livehospital.specialkidinfomd.sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

       // Log.d( SpecialKidInfoSyncAdapter.LOG_TAG, accountManager.getPassword(newAccount));
        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }


    /**
     * Helper method to schedule the com.livehospital.specialkidinfomd.sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic com.livehospital.specialkidinfomd.sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    private static void onAccountCreated(Account newAccount, Context context) {


          /*
         * Since we've created an account
         */
        SpecialKidInfoSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic com.livehospital.specialkidinfomd.sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a com.livehospital.specialkidinfomd.sync to get things started
         */
        syncImmediately(context);
    }


    public static void initializeSyncAdapter(Context context) {




        getSyncAccount(context);
    }

}