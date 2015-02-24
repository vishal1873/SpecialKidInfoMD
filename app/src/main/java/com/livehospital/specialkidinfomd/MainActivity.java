package com.livehospital.specialkidinfomd;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract;
import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract.ServiceProviderInfo;
import com.livehospital.specialkidinfomd.sync.SpecialKidInfoSyncAdapter;
import com.livehospital.specialkidinfomd.common.Constants;

public class MainActivity extends ActionBarActivity implements
        LocationFragment.OnLocationSelectedListener,
        NavigationDrawerFragment.MenuSelectionListener,
    ServiceProviderInfoFragment.Callback


{
    public static final String MENU_SELECTED = "menuSelected";
    public static final String LOCATION = "Location";

    public static final String SELECTED_SERVICE_PROVIDER_INFO = "selected_service_provider_info";

    private Toolbar toolbar;
    private NavigationDrawerFragment mNavigationDrawerFragmentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mNavigationDrawerFragmentFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        mNavigationDrawerFragmentFragment.setUp(
                R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        SpecialKidInfoSyncAdapter.initializeSyncAdapter(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    /*
       This callback is invoked when a location is selected
     */
    public void onLocationSelected(String location, String fromMenu) {


       storeLocationInPreferences(location);

        if (fromMenu!=null);
            menuAction(fromMenu);

    }

    private void storeLocationInPreferences(String location) {

        SharedPreferences settings = getSharedPreferences(Constants.USER_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LOCATION,location);

        editor.commit();

    }


    @Override
    public void onSchoolMenuClicked() {

        menuAction("school");

    }

    private  void  menuAction(String type)
    {

        // if the location is set show the menu option according to the location.
        if(isLocationSet()) {

            Fragment fragment = new ServiceProviderInfoFragment();

            Bundle arguments = new Bundle();
            arguments.putString(MENU_SELECTED, type);
            fragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
        else
        {
            Fragment fragment = new LocationFragment();

            Bundle arguments = new Bundle();
            arguments.putString(MENU_SELECTED, type);
            fragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
    }

    @Override
    public void onABAMenuClicked() {

        menuAction("ABA");

    }

    @Override

    // This is the main menu item from the drawer. User can set the location directly.
    public void onSetLocationMenuClicked() {

        Fragment fragment = new LocationFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();
    }


    public boolean isLocationSet()
    {
        SharedPreferences settings = getSharedPreferences(Constants.USER_PREFERENCES, 0);
        String location = settings.getString(LOCATION, null);
        if (location == null)
            return false;
        return true;

    }

    @Override
    public void onServiceProviderItemSelected(Cursor cursor) {


        Fragment fragment = new ServiceProviderDetailFragment();

        Bundle arguments = new Bundle();

        String providerName = cursor.getString(cursor.getColumnIndex(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_NAME));
        arguments.putString(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_NAME, providerName);


        String landLineNumber = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LAND_LINE_NUMBER));
        arguments.putString(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LAND_LINE_NUMBER, landLineNumber);

        String emailAddress = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS));
        arguments.putString(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS, emailAddress);



        String mobileNumber = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER));
        arguments.putString(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER, mobileNumber);

        String remark = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_REMARK));
        arguments.putString(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_REMARK, remark);

        String address = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_ADDRESS));
        arguments.putString(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_ADDRESS, address);



        fragment.setArguments(arguments);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();

    }
}
