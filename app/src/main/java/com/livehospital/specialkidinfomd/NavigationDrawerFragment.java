package com.livehospital.specialkidinfomd;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.livehospital.specialkidinfomd.adapter.NavigationDrawerAdapter;
import com.livehospital.specialkidinfomd.common.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * The navigation drawer has the main menu items
 */
public class NavigationDrawerFragment extends Fragment {


    // This interface is implemented by the main activity
    // This interface represents menu selection in the NavigationDrawer
    public interface MenuSelectionListener {
        public void onSupportGroupMenuClicked();

        public void onSchoolMenuClicked();

        public void onABAMenuClicked();

        public void onRDIMenuClicked();

        public void onIntegratedProvidersMenuClicked();

        public void onSpeechTherapistClicked();

        public void onOTMenuClicked();

        public void onSetLocationMenuClicked();

    }

    private final String LOG_TAG = NavigationDrawerFragment.class.getSimpleName();

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;


    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    private RecyclerView recyclerView;
    private NavigationDrawerAdapter adapter;

    private MenuSelectionListener mCallback;





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (MenuSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }


        setHasOptionsMenu(true);
    }


    public static List<Information> getData() {
        //load only static data inside a drawer
        List<Information> data = new ArrayList<>();

        //TODO - to put a for loop

        String[] titles = {"Support Groups", "ABA Providers", "RDI providers", "OT Providers", "Speech Therapist",
                "Integrated providers", "Special Schools", "Set Location"};
        Information current = new Information();

        current.title = titles[0];
        data.add(current);

        current = new Information();

        current.title = titles[1];
        data.add(current);

        current = new Information();

        current.title = titles[2];
        data.add(current);

        current = new Information();

        current.title = titles[3];
        data.add(current);

        current = new Information();

        current.title = titles[4];
        data.add(current);

        current = new Information();

        current.title = titles[5];
        data.add(current);

        current = new Information();

        current.title = titles[6];
        data.add(current);

        current = new Information();

        current.title = titles[7];
        data.add(current);


        return data;
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }


    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.USER_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        FragmentActivity activity = (FragmentActivity)mCallback;

        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }

       // activity.getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);


    }

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.navigation_drawer_fragment,
                container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Log.d(LOG_TAG, "Inside click " + position);
                invokeMenu(position);
                mDrawerLayout.closeDrawers();
            }


            @Override
            public void onLongClick(View view, int position) {

                Log.d(LOG_TAG, "Inside long click" + position);
                invokeMenu(position);
                mDrawerLayout.closeDrawers();
            }
        }));



        return layout;


    }


    public void invokeMenu(int position) {
        switch (position) {
            case 0:
                mCallback.onSupportGroupMenuClicked();
                break;

            case 1:
                mCallback.onABAMenuClicked();
                break;

            case 2:
                mCallback.onRDIMenuClicked();
                break;

            case 3:
                mCallback.onOTMenuClicked();
                break;

            case 4:
                mCallback.onSpeechTherapistClicked();
                break;

            case 5:
                mCallback.onIntegratedProvidersMenuClicked();
                break;

            case 6:
                mCallback.onSchoolMenuClicked();
                break;

            case 7:
                mCallback.onSetLocationMenuClicked();
                break;
        }
    }

    private void setActionBarArrowDependingOnFragmentsBackStack() {/*

        ActionBarActivity actionBarActivity = (ActionBarActivity)  mCallback;

        FragmentActivity activity = (FragmentActivity) mCallback;
        int backStackEntryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
        boolean flag = (backStackEntryCount == 0);
        mDrawerToggle.setDrawerIndicatorEnabled(flag);
        mDrawerToggle.setHomeAsUpIndicator(actionBarActivity.getV7DrawerToggleDelegate().getThemeUpIndicator());

        if(!flag)
         popBackStackToTop(activity);

        */

    }




    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,/* DrawerLayout object */
                toolbar,
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for acce    ssibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                //getActivity().invalidateOptionsMenu();
               // setActionBarArrowDependingOnFragmentsBackStack();
            }

            @Override
            public void onDrawerOpened(View drawerView) {


                super.onDrawerOpened(drawerView);


                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }

            }
        };




        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        ActionBarActivity ab = (ActionBarActivity)getActivity();

       mDrawerLayout.setDrawerListener(mDrawerToggle);
         mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });



    }


    public void createUpButton()
    {


        final FragmentActivity  activity = (FragmentActivity) getActivity();
        int backStackEntryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
        boolean flag = (backStackEntryCount == 0);

        if(!flag) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            ActionBarActivity ab = (ActionBarActivity) getActivity();
            mDrawerToggle.setHomeAsUpIndicator(ab.getV7DrawerToggleDelegate().getThemeUpIndicator());

            //This method only works when   mDrawerToggle.setDrawerIndicatorEnabled(false) is set to false;
            mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final FragmentActivity  activity = (FragmentActivity) getActivity();
                    int backStackEntryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
                    boolean flag = (backStackEntryCount == 1);
                    if(!flag)
                        activity.onBackPressed();
                    else
                        mDrawerToggle.setDrawerIndicatorEnabled(true);

                }


            });
        }else
        {
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            //mDrawerToggle.syncState();
        }

    }


    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }


    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {


        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }


                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {


            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }


        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
    }





}
