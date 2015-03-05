package com.livehospital.specialkidinfomd;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.appspot.specialkidinfo.specialkidinfoapi.Specialkidinfoapi;
import com.appspot.specialkidinfo.specialkidinfoapi.model.ServiceProvider;
import com.appspot.specialkidinfo.specialkidinfoapi.model.ServiceProviderCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract;

/**
 *
 */
public class AsyncFirstTimeDataLoader extends AsyncTask<String, Void,  Void> {

    private AsyncTaskFirstTimeDataLoaderCallback mCallBack;

    public AsyncFirstTimeDataLoader(AsyncTaskFirstTimeDataLoaderCallback callback)
    {
        mCallBack = callback;
    }

    public interface AsyncTaskFirstTimeDataLoaderCallback{
        public void onAsyncTaskComplete();
    }


    @Override
    protected Void doInBackground(String... strings) {
        try {

            String type = "school";
            Specialkidinfoapi.Builder builder = new Specialkidinfoapi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            Specialkidinfoapi  api =  builder.build();

            ServiceProviderCollection response = api.getAllProviders().execute();

            //TODO to refactor sync and AsyncTask to use common insertion code.

            //TODO to delete the database first and then add records

            //TODOD delete method needs to written properly in the provider
            ((Activity)mCallBack).getContentResolver().delete(SpecialKidInfoContract.ServiceProviderInfo.CONTENT_URI,null,null);

            //TODO to add batch insert
            for (ServiceProvider sp:response.getItems())
            {
                ContentValues data = new ContentValues();
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_NAME, sp.getName());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_TYPE, sp.getType().toLowerCase());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS, sp.getEmailAddress());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER, sp.getMobileNumber());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LAND_LINE_NUMBER, sp.getLandlineNumber());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_WEB_SITE, sp.getWebsite());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LOCATION, sp.getLocation().toLowerCase());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_ADDRESS, sp.getAddress());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_REMARK, sp.getRemark());
                data.put(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_SUB_lOCATION, sp.getSublocation());

                //Log.d(LOG_TAG, "The service provider" + sp.toString());

                Uri serviceProviderURI = ((Activity)mCallBack).getContentResolver().insert(SpecialKidInfoContract.ServiceProviderInfo.CONTENT_URI, data);
                //Log.d(LOG_TAG, "The newly created serviceProviderURI is "+serviceProviderURI);

            }



        } catch (Exception e) {
            //Log.d(LOG_TAG, e.getMessage(), e);
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        mCallBack.onAsyncTaskComplete();

    }
}
