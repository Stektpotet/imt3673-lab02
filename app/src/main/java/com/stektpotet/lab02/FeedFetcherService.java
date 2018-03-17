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
import android.provider.ContactsContract;
import android.util.DebugUtils;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;


/**
 * An {@link IntentService}
 * that handles fetching the feed [RSS2/Atom] from a url, and storing it in a cache file.
 */
public class FeedFetcherService extends IntentService {

    private static final String TAG = FeedFetcherService.class.getName();

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FEED_FETCH = "com.stektpotet.lab02.action.FEED_FETCH";

    public FeedFetcherService() {
        super("FeedFetcherService");
    }

    private PendingIntent repeatFetchIntent;

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.getAction() == null) {
            intent.setAction("NULL"); //RIP
            Log.w(TAG + ".handleIntent", "Started service without an action!!!");
        }
        switch (intent.getAction()) {
            case ACTION_FEED_FETCH:
                handleFeedFetch();
                break;
            default:
                Log.d(TAG, "Cannot handle intent with unsupported action: " + intent.getAction());
        }
    }

    private void handleFeedFetch() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            String prefFeedSource = sharedPreferences.getString(SettingsActivity.PREF_FEED_SOURCE, getResources().getString( R.string.pref_feed_default_src));

            Log.d(TAG+".onReceive.feedSource", prefFeedSource);

            try {
                URL feedSource = new URL(prefFeedSource);

                InputStream inStream = feedSource.openConnection().getInputStream();
                File feedFile = cacheFeed(inStream);
                inStream.close();
                if(feedFile != null) {
                    broadcastCompletion(feedFile.getPath());
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Toast.makeText(getApplicationContext(),"Unable to fetch feed, no internet connection!", Toast.LENGTH_LONG).show();

    }
    private void broadcastCompletion(String cachedFilePath) {
        Intent completionIntent = new Intent();

        completionIntent.putExtra(FeedFetcherSignalReceiver.PARAM_FEED_FILE_PATH, cachedFilePath);

        completionIntent.setAction(FeedFetcherSignalReceiver.ACTION_FEED_FETCH_COMPLETE);
        sendBroadcast(completionIntent);
    }

    private File cacheFeed(InputStream inputStream) {
        File file = null;
        FileOutputStream writeFileStream;
        try {

            File feedCacheDir = new File(getApplicationContext().getCacheDir(), "feed");
            if(!feedCacheDir.exists()) {
                feedCacheDir.mkdir();
            }
            file = File.createTempFile("feed_cache", null, feedCacheDir);
            writeFileStream = new FileOutputStream(file);
            Log.d(TAG + ".writeFile.location", file.getAbsolutePath());
            int read = 0;
            byte[] bytes = new byte[1024*10];

            while ((read = inputStream.read(bytes)) != -1) {
                writeFileStream.write(bytes, 0, read);
            }
            writeFileStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
