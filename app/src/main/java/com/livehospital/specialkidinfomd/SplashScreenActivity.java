package com.livehospital.specialkidinfomd;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


public class SplashScreenActivity extends ActionBarActivity implements
        AsyncFirstTimeDataLoader.AsyncTaskFirstTimeDataLoaderCallback {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mProgressBar = (ProgressBar)this.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        new AsyncFirstTimeDataLoader(this).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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
    public void onAsyncTaskComplete() {
        mProgressBar.setVisibility(View.GONE);

        if (isDownloadOfDataRequired()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean isDownloadOfDataRequired() {

        //TODO This method needs to be implemented. It should take into consideration whether it is first time or not.
        // Download is required only for first time. Rest of the time the sync should work in background.
        return true;
    }
}
