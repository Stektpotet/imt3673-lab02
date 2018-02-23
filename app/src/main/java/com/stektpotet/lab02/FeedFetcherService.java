package com.stektpotet.lab02;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.stektpotet.lab02.parser.Feed;
import com.stektpotet.lab02.parser.FeedEntry;
import com.stektpotet.lab02.parser.FeedParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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

    private PendingIntent repeatFetchIntent;

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction() == Intent.ACTION_GET_CONTENT) {

            Intent fetchIntent = new Intent(this, FeedFetcherSignalReceiver.class);
            repeatFetchIntent = PendingIntent.getBroadcast(this, 0,fetchIntent, 0);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Log.d("Something", sharedPreferences.getString(SettingsActivity.FeedPreferenceFragment.PREF_FEED_ENTRY_FREQUENCY, ""));
//            int feedRefreshFrequency = Integer.parseInt(sharedPreferences.getString(SettingsActivity.FeedPreferenceFragment.PREF_FEED_ENTRY_FREQUENCY, "1000"));

            Log.d("RECIEVER.init", "WATWAT");
            startBroadcast(10000);


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

    private void startBroadcast(int interval) {
        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
        Log.d("RECIEVER.broadcast.send", "WATWAT");
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, repeatFetchIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }


    private File inputStreamToFile(InputStream inputStream) {
        File file = null;
        FileOutputStream writeFileStream;
        try {
            // TODO: DELETE PREVIOUS BEFORE WRITE IF EXISTS
            file = File.createTempFile("feed_cache", null, getApplicationContext().getCacheDir());
            writeFileStream = new FileOutputStream(file);
            Log.d("FILEWRITE.location", file.getAbsolutePath());
            int read = 0;
            byte[] bytes = new byte[4096];

            while ((read = inputStream.read(bytes)) != -1) {
                writeFileStream.write(bytes, 0, read);
            }
            writeFileStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

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
