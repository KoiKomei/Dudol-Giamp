package com.ilmale.doodlejump;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ilmale.doodlejump.network.Client;

public class LoadActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoadActivity.class.getSimpleName();

    boolean isWifiConn = false;
    boolean isMobileConn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "onCreate");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_load);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d(LOG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(LOG_TAG, "Mobile connected: " + isMobileConn);

        if (!isWifiConn || !isMobileConn){
            onPause();
        }
        else {
            Intent intent = new Intent(this, MultiActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume(){
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }
}
