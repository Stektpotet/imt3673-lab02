package com.stektpotet.lab02;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class FeedFetcherService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.stektpotet.lab02.action.FOO";
    public static final String ACTION_BAZ = "com.stektpotet.lab02.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.stektpotet.lab02.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.stektpotet.lab02.extra.PARAM2";

    public FeedFetcherService() {
        super("FeedFetcherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction() == Intent.ACTION_GET_CONTENT) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if(connectivityManager.getNetworkInfo(connectivityManager.getActiveNetwork()).isConnected()) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String url = sharedPreferences.getString(SettingsActivity.FeedPreferenceFragment.PREF_FEED_SOURCE, null);

                Log.d("SERVICE.FEED_FETCH", url);

                try {
                    InputStream inStream = new URL(url).openConnection().getInputStream();
                    File feedFile = inputStreamToFile(inStream);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(),"Unable to fetch feed, no internet connection!", Toast.LENGTH_LONG).show();
            }



            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }


    private File inputStreamToFile(InputStream inputStream) {
        File file = null;
        FileOutputStream writeFileStream;
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer); //dump everything into the buffer

            // TODO: DELETE PREVIOUS BEFORE WRITE IF EXISTS
            file = File.createTempFile("feed_cache", null, getApplicationContext().getCacheDir());
            writeFileStream = new FileOutputStream(file);

            writeFileStream.write(buffer); //dump the buffer over into the file
            writeFileStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

//    private File cacheFeedToFile(Context context, String url) {
//        File file;
//        try {
//            String fileName = Uri.parse(url).getLastPathSegment();
//            file = File.createTempFile(fileName, null, context.getCacheDir());
//        } catch (IOException e) {
//            // Error while creating file
//        }
//        return file;
//    }



    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
