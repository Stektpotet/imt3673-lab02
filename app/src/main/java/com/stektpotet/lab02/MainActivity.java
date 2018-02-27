package com.stektpotet.lab02;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stektpotet.lab02.parser.Feed;
import com.stektpotet.lab02.parser.RSS.RSSFeed;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private FloatingActionButton mFetchButton;
    private ListView mFeedPostList;
    private TextView mFeedTitle, mFeedDescription;
    private ProgressBar mProgressBar;

    FeedListAdapter mFeedPostListAdapter;

    public static final String KEY_ACTIVE_FEED = "ActiveFeed";
    public static final String KEY_ITEM_INDEX = "FeedItemIndex";
    private FeedFetcherClock mFetcherClock;

    private FeedFetcherSignalReceiver mFeedFetcherSignalReceiver;
    private Feed mActiveFeed;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFeedPostList = findViewById(R.id.main_list_feed);
        mFeedPostList.setOnItemClickListener(mFeedPostListClickListener);

        mProgressBar = findViewById(R.id.main_progressBar);

        mFeedPostListAdapter = new FeedListAdapter(getApplicationContext(), R.layout.feed_list_item);
        mFeedPostList.setAdapter(mFeedPostListAdapter);

        mFetchButton = findViewById(R.id.main_fab_fetch);
        mFetchButton.setOnClickListener(mFetchButtonClickListener);

        mFeedTitle = findViewById(R.id.main_txt_feed_title);
        mFeedDescription = findViewById(R.id.main_txt_feed_desc);


        mFeedFetcherSignalReceiver = new FeedFetcherSignalReceiver(
                new FeedFetcherSignalReceiver.FeedFetcherCallbacks() {
                    @Override
                    public void onProcessFeedStart() {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onProcessedFeedFinished() {
                        mActiveFeed = mFeedFetcherSignalReceiver.lastProcessedFeed;
                        if(mActiveFeed != null) {
                            updateMetaDataFields();
                        }
                        mProgressBar.setVisibility(View.GONE);
                        Log.d(TAG+".callback", "Finished processing feed...");
                    }

                    @Override
                    public void onProcessedFeedFailed() {
                        Toast.makeText(getApplicationContext(), "Failed processing feed...", Toast.LENGTH_LONG).show();
                        Log.d(TAG+".callback", "Failed processing feed...");
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onProgress(int value) {
                        mProgressBar.setProgress(value);
                        Log.i(TAG, "progress: "+value);
                    }
                }
        );
        IntentFilter feedFetcherIntentFilter = new IntentFilter();
        feedFetcherIntentFilter.addAction(FeedFetcherSignalReceiver.ACTION_FEED_FETCH_COMPLETE);
        feedFetcherIntentFilter.addAction(FeedFetcherSignalReceiver.ACTION_FEED_FETCH_FIND_CACHE);

        registerReceiver(mFeedFetcherSignalReceiver, feedFetcherIntentFilter);
        startFetchClock();

        Intent openCachedFeedIntent = new Intent(FeedFetcherSignalReceiver.ACTION_FEED_FETCH_FIND_CACHE);
        sendBroadcast(openCachedFeedIntent);
    }


    private void startFetchClock() {
        String frequency = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString(SettingsActivity.PREF_FEED_ENTRY_FREQUENCY, "10");

        mFetcherClock = new FeedFetcherClock(getApplicationContext());
        Intent feedFetchIntent = new Intent(getApplicationContext(), FeedFetcherService.class);
        feedFetchIntent.setAction(FeedFetcherService.ACTION_FEED_FETCH);
        mFetcherClock.setAlarm(feedFetchIntent,Long.valueOf(frequency)*60000L);
    }

    private void stopFetchClock() {
        mFetcherClock.cancelAlarm();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().getAction() == SettingsActivity.ACTION_UPDATED_SETTINGS) {
            refreshFeed();
            stopFetchClock();
            startFetchClock();
        }
    }

    private void updateMetaDataFields() {
        mFeedTitle.setText(mActiveFeed.title);
        mFeedDescription.setText(mActiveFeed.link);
        if(mActiveFeed instanceof RSSFeed) {
            RSSFeed feed =(RSSFeed) mActiveFeed;
            if(!feed.description.equals("")){
                mFeedDescription.setText(feed.description);
            }
        }

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
            feedEntryDisplayIndent.putExtra(KEY_ITEM_INDEX, i);
            startActivity(feedEntryDisplayIndent);
        }
    };
    /**
     * Refresh the RSS/Atom feed list.
     **/
    private void refreshFeed() {
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
                Toast.makeText(getBaseContext(), "Not Implemented!",Toast.LENGTH_LONG).show();
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
