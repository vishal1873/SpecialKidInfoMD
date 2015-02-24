package com.livehospital.specialkidinfomd;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ServiceProviderDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ServiceProviderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceProviderDetailFragment extends Fragment {






    private String mRemark;
    private String mProviderName;
    private String mProviderPhone;
    private String mProviderWebsite;
    private String mProviderEmailAddress;
    private String mProviderAddress;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceProviderDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceProviderDetailFragment newInstance(String param1, String param2) {
        ServiceProviderDetailFragment fragment = new ServiceProviderDetailFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ServiceProviderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRemark = getArguments().getString(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_REMARK);
            mProviderName = getArguments().getString(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_NAME);
            mProviderEmailAddress = getArguments().getString(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS);
            mProviderAddress = getArguments().getString(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_ADDRESS);
            mProviderPhone = getArguments().getString(SpecialKidInfoContract.ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.service_provider_detail_fragment, container, false);


        TextView providerNameView = (TextView) rootView.findViewById(R.id.provider_name);
        providerNameView.setText(mProviderName);

        TextView providerPhoneNumber = (TextView) rootView.findViewById(R.id.provider_phone_number);
        providerPhoneNumber.setText(mProviderPhone);

        TextView providerEmail = (TextView) rootView.findViewById(R.id.provider_email_address);
        providerEmail.setText(mProviderEmailAddress);

        TextView providerLocation = (TextView) rootView.findViewById(R.id.provider_address);
        providerLocation.setText(mProviderAddress);

        TextView providerRemarks = (TextView) rootView.findViewById(R.id.provider_remark);
        providerRemarks.setText(mRemark);



        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        /*
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
       }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
