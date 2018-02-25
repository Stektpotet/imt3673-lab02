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

    private Runnable updateCallback;

    public FeedFetcherSignalReceiver(Runnable callback) {
        updateCallback = callback;
    }

    public Feed lastProcessedFeed;

    public static final String TAG = FeedFetcherSignalReceiver.class.getName();

    public static final String PARAM_FEED_FILE_PATH = TAG + ".extras.PARAM_FEED_FILE_PATH";

    @Override
    public void onReceive(Context context, Intent intent) {

        //TODO: use intentActions

        Bundle extras = intent.getExtras();

        String filePath = extras.getString(PARAM_FEED_FILE_PATH);

        Log.d(TAG + ".onReceive", "Recieved!");
        Toast.makeText(context,"TOAST from FeedFetcherSignalReceiver!", Toast.LENGTH_LONG).show();

        String itemMax = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(SettingsActivity.PREF_FEED_ENTRY_LIMIT, "10");

        new FeedProcessor().execute(filePath, itemMax);
    }

    public class FeedProcessor extends AsyncTask<String, Integer, Feed> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Feed doInBackground(String... strings) {
            //strings[0] filePath
            Feed result = null;
            String filePath = strings[0];
            int itemMax = Integer.valueOf(strings[1]);
            Log.i(TAG + ".Processor.work.filePath", filePath);
            Log.i(TAG + ".Processor.work.itemMax", strings[1] + " - " + itemMax);
            try {
                InputStream inStream = new FileInputStream(filePath);
                result = FeedParser.parse(inStream,itemMax);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result == null) {
                Log.e(TAG + ".Processor.work.result", "Failed getting feed parsed");
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Feed feed) {
            super.onPostExecute(feed);
            lastProcessedFeed = feed;
            updateCallback.run();
        }
    }
}