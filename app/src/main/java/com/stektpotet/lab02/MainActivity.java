package com.stektpotet.lab02;

import android.content.ContentProvider;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stektpotet.lab02.parser.Feed;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private FloatingActionButton mFetchButton;
    private ListView mFeedPostList;
    private TextView mFeedTitle, mFeedDescription;

    FeedListAdapter mFeedPostListAdapter;

    public static final String KEY_ACTIVE_FEED = "ActiveFeed";
    private FeedFetcherClock fetcherClock;

    private FeedFetcherSignalReceiver mFeedFetcherSignalReceiver;
    private Feed mActiveFeed;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFeedPostList = findViewById(R.id.main_list_feed);
        mFeedPostList.setOnItemClickListener(mFeedPostListClickListener);

        mFeedPostListAdapter = new FeedListAdapter(getApplicationContext(), R.layout.feed_list_item);
        mFeedPostList.setAdapter(mFeedPostListAdapter);

        mFetchButton = findViewById(R.id.main_fab_fetch);
        mFetchButton.setOnClickListener(mFetchButtonClickListener);

        mFeedTitle = findViewById(R.id.main_txt_feed_title);
        mFeedDescription = findViewById(R.id.main_txt_feed_desc);

        //Set up intent...
//        feedFetcherServiceIntent = new Intent(MainActivity.this, FeedFetcherService.class);
//        feedFetcherServiceIntent.setAction(Intent.ACTION_GET_CONTENT); //TODO verify this action
//        feedFetcherServiceIntent.setFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
//        startService(feedFetcherServiceIntent);
        mFeedFetcherSignalReceiver = new FeedFetcherSignalReceiver(
                new Runnable() {
                    @Override
                    public void run() {
                        mActiveFeed = mFeedFetcherSignalReceiver.lastProcessedFeed;
                        if(mActiveFeed != null) {
                            updateMetaDataFields();
                        }
                    }
                }
        );
        registerReceiver(mFeedFetcherSignalReceiver, new IntentFilter(FeedFetcherService.ACTION_FEED_FETCH_COMPLETE));

        startFetchClock();

        refreshFeed();
    }


    private void startFetchClock() {

        String frequency = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString(SettingsActivity.PREF_FEED_ENTRY_FREQUENCY, "10");

        fetcherClock = new FeedFetcherClock(getApplicationContext());
        Intent feedFetchIntent = new Intent(getApplicationContext(), FeedFetcherService.class);
        feedFetchIntent.setAction(FeedFetcherService.ACTION_FEED_FETCH);
        fetcherClock.setAlarm(feedFetchIntent,Long.valueOf(frequency)*6000L);

        Toast.makeText(this, "Alarm set!", Toast.LENGTH_LONG).show();
    }

    private void stopFetchClock() {
        fetcherClock.cancelAlarm();
    }

    //TODO look into using a Singleton for app preferences for all activities to get access
    // OR
    //TODO look into using onResume for updating preference state in activity


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void updateMetaDataFields() {
        mFeedTitle.setText(mActiveFeed.title);
        mFeedDescription.setText(mActiveFeed.link);
        mFeedPostListAdapter.clear();
        mFeedPostListAdapter.addAll(mActiveFeed.elements);
    }

    private View.OnClickListener mFetchButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            refreshFeed();
        }
    };

    private AdapterView.OnItemClickListener mFeedPostListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent feedEntryDisplayIndent = new Intent(MainActivity.this, DisplayActivity.class);
            feedEntryDisplayIndent.putExtra(KEY_ACTIVE_FEED, mActiveFeed);
            startActivity(new Intent(MainActivity.this, DisplayActivity.class));
        }
    };

    /**
     * Refresh the RSS/Atom feed list.
     **/
    private void refreshFeed() {
        //TODO Fetch the feed
        Toast.makeText(getBaseContext(), "Fetching...",Toast.LENGTH_LONG).show();

        Intent feedFetcherIntent = new Intent(this, FeedFetcherService.class);
        feedFetcherIntent.setAction(FeedFetcherService.ACTION_FEED_FETCH);
        startService(feedFetcherIntent); //kick start a feed fetch
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                //OPEN PREFERENCES ACTIVITY
                return true;

            case R.id.action_refresh:
                refreshFeed();
                return true;
            case R.id.action_about:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mFeedFetcherSignalReceiver); //not needed, but for cleanliness we'll make it clear this is happening.
    }
}
