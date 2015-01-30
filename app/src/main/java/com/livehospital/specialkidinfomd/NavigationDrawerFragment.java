package com.livehospital.specialkidinfomd;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
        public void onSchoolMenuClicked();
        public void onABAMenuClicked();
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
    }


    public static List<Information> getData() {
        //load only static data inside a drawer
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_number1, R.drawable.ic_number2, R.drawable.ic_number3, R.drawable.ic_number4,R.drawable.ic_number4};
        String[] titles = {"ABA Providers", "OT Providers", "Speech Therapist", "Special Schools","Set Location"};
        Information current = new Information();
        current.iconId = icons[0];
        current.title = titles[0];
        data.add(current);

        current = new Information();
        current.iconId = icons[1];
        current.title = titles[1];
        data.add(current);

        current = new Information();
        current.iconId = icons[2];
        current.title = titles[2];
        data.add(current);

        current = new Information();
        current.iconId = icons[3];
        current.title = titles[3];
        data.add(current);

        current = new Information();
        current.iconId = icons[4];
        current.title = titles[4];
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
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
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
            }
        }));

        return layout;


    }


    public void invokeMenu(int position) {
        switch (position) {
            case 0:
                mCallback.onABAMenuClicked();
                break;

            case 3:
                mCallback.onSchoolMenuClicked();
                break;

            case 4:
                mCallback.onSetLocationMenuClicked();
                break;

        }
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
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                //getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerOpened(View drawerView) {


                super.onDrawerOpened(drawerView);


                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                //getActivity().invalidateOptionsMenu();
            }
        };


        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
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

