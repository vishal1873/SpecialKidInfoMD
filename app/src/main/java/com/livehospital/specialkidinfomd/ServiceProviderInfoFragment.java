package com.livehospital.specialkidinfomd;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.livehospital.specialkidinfomd.adapter.ServiceProviderInfoAdapter;
import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract;
import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract.ServiceProviderInfo;


public class ServiceProviderInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String[] SPECIAL_PROVIDER_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            ServiceProviderInfo.TABLE_NAME + "." + ServiceProviderInfo._ID,
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_NAME,
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER,
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LAND_LINE_NUMBER,
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS,
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_WEB_SITE,
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_ADDRESS,
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_REMARK,
            ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_SUB_lOCATION,

    };


    public interface OnFragmentInteractionListener {
        public void showDrawerToggle(boolean showDrawerToggle);
    }

    public final static String LOG_TAG = ServiceProviderInfoFragment.class.getSimpleName();

    ServiceProviderInfoAdapter mServiceProviderAdapter;


    public static final int COL_PROVIDER_NAME = 1;
    private static final int SERVICE_PROVIDER_LOADER = 0;

    public ServiceProviderInfoFragment() {
    }


    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*
        try {
            this.mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Entering on CreateView");
        //SpecialKidInfoSyncAdapter.syncImmediately(getActivity());

        mServiceProviderAdapter = new ServiceProviderInfoAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_service_provider_list, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mServiceProviderAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = mServiceProviderAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                   /* Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .putExtra(DetailActivity.DATE_KEY, cursor.getString(COL_WEATHER_DATE));
                    startActivity(intent);*/

                    ((Callback) getActivity())
                            .onServiceProviderItemSelected(cursor);
                }

            }
        });


        Button b = (Button) rootView.findViewById(R.id.location_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ((Callback) getActivity()).onSetLocationMenuClicked();
            }
        });


        Log.d(LOG_TAG, "Exiting on CreateView");

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering  onActivityCreated(");
        getLoaderManager().initLoader(SERVICE_PROVIDER_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "Exiting on onActivityCreated");

    }


/* Need to see this
    @Override
    public void onResume() {
        super.onResume();
        if (mLocation != null && !mLocation.equals(Utility.getPreferredLocation(getActivity()))) {
            getLoaderManager().restartLoader(FORECAST_LOADER, null, this);
        }
    }
*/

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {


        Log.d(LOG_TAG, "Entering  onCreateLoader(");

        Uri serviceProviderInfoUri = getServiceProviderURI();

        Log.d(LOG_TAG, "Exiting on onCreateLoader");
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                serviceProviderInfoUri,
                SPECIAL_PROVIDER_COLUMNS,
                null,
                null,
                null
        );


    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onServiceProviderItemSelected(Cursor cursor);

        public void onSetLocationMenuClicked();


    }

    public Uri getServiceProviderURI() {


        String type = null;

        /*
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getString(MainActivity.MENU_SELECTED);
        }*/

        GlobalState globalState = (GlobalState)getActivity().getApplicationContext();
        type = globalState.getServiceProviderType();

        Uri serviceProviderInfoUri = SpecialKidInfoContract.ServiceProviderInfo.buildServiceProviderWithType(type);
        return serviceProviderInfoUri;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mListener.showDrawerToggle(false);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        Log.d(LOG_TAG, "Entering  onLoadFinished(");
        mServiceProviderAdapter.swapCursor(cursor);
        Log.d(LOG_TAG, "Exiting on onLoadFinished");

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        Log.d(LOG_TAG, "Entering  onLoaderReset(");
        mServiceProviderAdapter.swapCursor(null);
        Log.d(LOG_TAG, "Exiting on onLoaderReset");

    }
}
