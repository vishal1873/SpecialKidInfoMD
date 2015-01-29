package com.livehospital.specialkidinfomd.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SpecialKidInfoSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static SpecialKidInfoSyncAdapter syncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = new SpecialKidInfoSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}