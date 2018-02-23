package com.stektpotet.lab02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by halvor on 23.02.18.
 */

public class FeedFetcherSignalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        /*

            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String url = sharedPreferences.getString(SettingsActivity.FeedPreferenceFragment.PREF_FEED_SOURCE, null);

                Log.d("SERVICE.FEED_FETCH", url);

                try {
                    InputStream inStream = new URL(url).openConnection().getInputStream();
                    File feedFile = inputStreamToFile(inStream);
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            } else {
                Toast.makeText(getApplicationContext(),"Unable to fetch feed, no internet connection!", Toast.LENGTH_LONG).show();
            }

        */
        Log.d("RECIEVER.broadcast.receive", "WATWAT");
        Toast.makeText(context,"TOAST from FeedFetcherSignalReceiver!", Toast.LENGTH_LONG).show();
    }
}
