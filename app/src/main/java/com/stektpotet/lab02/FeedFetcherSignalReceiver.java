package com.stektpotet.lab02;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.stektpotet.lab02.parser.Feed;
import com.stektpotet.lab02.parser.FeedParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by halvor on 23.02.18.
 */

public class FeedFetcherSignalReceiver extends BroadcastReceiver {

    public static final String TAG = FeedFetcherSignalReceiver.class.getName();


    interface FeedFetcherCallbacks {

        void onProcessFeedStart();
        void onProcessedFeedFinished();
        void onProcessedFeedFailed();
        void onProgress(int value);
    }
    FeedFetcherCallbacks mCallbacks;

    public Feed lastProcessedFeed;
    public FeedFetcherSignalReceiver(FeedFetcherCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    public static final String ACTION_FEED_FETCH_COMPLETE = "com.stektpotet.lab02.action.FEED_FETCH_COMPLETE";
    public static final String ACTION_FEED_FETCH_FIND_CACHE = "com.stektpotet.lab02.action.FEED_FETCH_FIND_CACHE";

    public static final String PARAM_FEED_FILE_PATH = TAG + ".extras.PARAM_FEED_FILE_PATH";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String itemMax = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(SettingsActivity.PREF_FEED_ENTRY_LIMIT, "10");

        switch (intent.getAction())
        {
            case ACTION_FEED_FETCH_COMPLETE:
                String filePath = extras.getString(PARAM_FEED_FILE_PATH);
                new FeedProcessor().execute(filePath, itemMax);
                break;
            case ACTION_FEED_FETCH_FIND_CACHE:

                File feedCacheDir = new File(context.getCacheDir(), "feed");
                if(!feedCacheDir.exists() || !feedCacheDir.isDirectory()) {
                    break;
                }
                File[] files = feedCacheDir.listFiles(new FileFilter() {
                    public boolean accept(File file) {
                        return file.isFile();
                    }
                });

                if(files.length > 0) {
                    File mostRecent = files[0];

                    for(File f : files) {
                        if(f.lastModified() > mostRecent.lastModified()) {
                            mostRecent = f;
                        }
                    }
                    new FeedProcessor().execute(mostRecent.getPath(), itemMax);
                    break;
                }
                mCallbacks.onProcessedFeedFailed();
                break;
        }

        Log.d(TAG + ".onReceive", "Recieved!");


    }

    public class FeedProcessor extends AsyncTask<String, Integer, Feed> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCallbacks.onProcessFeedStart();
        }

        @Override
        protected Feed doInBackground(String... strings) {
            //strings[0] filePath
            publishProgress(25);

            Feed result = null;
            String filePath = strings[0];
            int itemMax = Integer.valueOf(strings[1]);
            Log.i(TAG + ".Processor.work.filePath", filePath);
            Log.i(TAG + ".Processor.work.itemMax", strings[1] + " - " + itemMax);
            try {
                Thread.sleep(10);
                InputStream inStream = new FileInputStream(filePath);
                publishProgress(50);
                result = FeedParser.parse(inStream,itemMax);
                Thread.sleep(10);
                publishProgress(75);
                Thread.sleep(10);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result == null) {
                Log.e(TAG + ".Processor.work.result", "Failed getting feed parsed");
                mCallbacks.onProcessedFeedFailed();
            }
            publishProgress(0);
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mCallbacks.onProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Feed feed) {
            super.onPostExecute(feed);
            lastProcessedFeed = feed;
            mCallbacks.onProcessedFeedFinished();
        }
    }
}