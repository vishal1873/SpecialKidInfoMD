package com.livehospital.specialkidinfomd;

import android.app.Application;

public class GlobalState extends Application {


    private String serviceProviderType;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getServiceProviderType() {
        return serviceProviderType;
    }

    public void setServiceProviderType(String serviceProviderType) {
        this.serviceProviderType = serviceProviderType;
    }



}