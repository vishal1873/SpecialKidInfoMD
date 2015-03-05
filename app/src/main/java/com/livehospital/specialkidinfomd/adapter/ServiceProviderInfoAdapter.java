package com.livehospital.specialkidinfomd.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.livehospital.specialkidinfomd.MainActivity;
import com.livehospital.specialkidinfomd.R;
import com.livehospital.specialkidinfomd.ServiceProviderInfoFragment;
import com.livehospital.specialkidinfomd.data.SpecialKidInfoContract.ServiceProviderInfo;


public class ServiceProviderInfoAdapter extends CursorAdapter {

    private Context context;



    public ServiceProviderInfoAdapter  (Context context, Cursor c, int flags){
        super(context, c, flags);
        this.context = context;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        //TODO: To add view holder.
        int layoutId = -1;



        layoutId = R.layout.special_provider_list_item;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {



        TextView providerNameView = (TextView) view.findViewById(R.id.provider_name);
        CharSequence providerName = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_NAME));
        providerNameView.setText(providerName);



        /*CharSequence landLineNumber = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_LAND_LINE_NUMBER));
        TextView providerLandLineView = (TextView) view.findViewById(R.id.provider_land_line_number);
        providerLandLineView.setText(landLineNumber);*/

        CharSequence emailAddress = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_EMAIL_ADDRESS));
        TextView providerEmailAddress = (TextView) view.findViewById(R.id.provider_email_address);
        providerEmailAddress.setText(emailAddress);



        CharSequence mobileNumber = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_MOBILE_NUMBER));
        TextView providerMobileNumber = (TextView) view.findViewById(R.id.provider_mobile_number);
        providerMobileNumber.setText(mobileNumber);


        CharSequence sublocation = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_SUB_lOCATION));
        TextView sublocationView = (TextView) view.findViewById(R.id.provider_sub_location);
        sublocationView.setText(sublocation);

        /*CharSequence website = cursor.getString(cursor.getColumnIndex(ServiceProviderInfo.COLUMN_SERVICE_PROVIDER_WEB_SITE));
        TextView providerWebsite = (TextView) view.findViewById(R.id.pr);
        providerWebsite.setText(website);
        */



    }




}