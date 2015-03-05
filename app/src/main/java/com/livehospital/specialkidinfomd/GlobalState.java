package com.livehospital.specialkidinfomd;

import android.app.Application;

public class GlobalState extends Application {


    private String serviceProviderType;

    public String getServiceProviderType() {
        return serviceProviderType;
    }

    public void setServiceProviderType(String serviceProviderType) {
        this.serviceProviderType = serviceProviderType;
    }



}