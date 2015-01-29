package com.livehospital.specialkidinfomd.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * The service which allows the com.livehospital.specialkidinfomd.sync adapter framework to access the authenticator.
 */
public class SpecialKidInfoAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private SpecialKidInfoAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new SpecialKidInfoAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}