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
import android.widget.ListView;

import com.livehospital.specialkidinfomd.adapter.ServiceProviderInfoAdapter;
import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract;


public class OTProviderInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {


    public interface OnFragmentInteractionListener {
        public void showDrawerToggle(boolean showDrawerToggle);
    }

    public final static String LOG_TAG = OTProviderInfoFragment.class.getSimpleName();

    ServiceProviderInfoAdapter mServiceProviderAdapter;


    public static final int COL_PROVIDER_NAME = 1;
    private static final int SERVICE_PROVIDER_LOADER = 0;

	public OTProviderInfoFragment(){}


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

        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mServiceProviderAdapter);

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

        Uri serviceProviderInfoUri = SpecialKidInfoContract.ServiceProviderInfo.buildServiceProviderWithType("OT");


        Log.d(LOG_TAG, "Exiting on onCreateLoader");
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                serviceProviderInfoUri,
                null,
                null,
                null,
                null
        );



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
