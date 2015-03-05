package com.livehospital.specialkidinfomd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class LocationFragment extends Fragment {

    String  mfromMenu = null;


    private OnLocationSelectedListener mCallback;

    public interface OnLocationSelectedListener {
        public void onLocationSelected(String location, String fromMenu);
    }


    // List view
    private ListView lv;

    // Listview Adapter
    private ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnLocationSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        GlobalState globalState = (GlobalState)getActivity().getApplicationContext();
        mfromMenu = globalState.getServiceProviderType();



        View rootView = inflater.inflate(R.layout.location_fragment,container, false);


        //To move to String array
        // Listview Data
        final String products[] = {"bangalore", "Delhi", "Pune", "Hyderabad"};

        lv = (ListView) rootView.findViewById(R.id.list_view);

        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String)products[position];

                // Show Alert
                Toast.makeText(getActivity().getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

                mCallback.onLocationSelected(itemValue,LocationFragment.this.mfromMenu);


            }
        });

        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.location_single_item, R.id.product_name, products);
        lv.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                LocationFragment.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        return rootView;


















































































































































































































































































































    }









}
