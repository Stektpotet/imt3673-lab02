package com.stektpotet.lab02;

import android.content.ContentProvider;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFetchButton;
    private ListView mFeedPostList;
    private TextView mFeedTitle, mFeedDescription;


    private String feedSourceURL;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFeedPostList = findViewById(R.id.main_list_feed);
        mFeedPostList.setOnItemClickListener(mFeedPostListClickListener);

        mFetchButton = findViewById(R.id.main_fab_fetch);
        mFetchButton.setOnClickListener(mFetchButtonClickListener);

        mFeedTitle = findViewById(R.id.main_txt_feed_title);
        mFeedDescription = findViewById(R.id.main_txt_feed_desc);

        //grab the source url from preferences
        feedSourceURL = PreferenceManager.getDefaultSharedPreferences(this).getString("feed_src", "");
        mFeedDescription.setText(feedSourceURL);

        //Set up intent...
        Intent feedFetcherServiceIntent = new Intent(MainActivity.this, FeedFetcherService.class);
        feedFetcherServiceIntent.setAction(Intent.ACTION_GET_CONTENT); //TODO verify this action
        feedFetcherServiceIntent.setFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
        feedFetcherServiceIntent.setData(Uri.parse(feedSourceURL));

//        startService(feedFetcherServiceIntent);

        refreshFeed();
    }

    //TODO look into using a Singleton for app preferences for all activities to get access
    // OR
    //TODO look into using onResume for updating preference state in activity


    @Override
    protected void onResume() {
        super.onResume();

    }

//    private void updateMetaDataFields() {
//        mFeedDescription.setText();
//        mFeedDescription.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("feed_src", ""));
//    }

    private View.OnClickListener mFetchButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            refreshFeed();
        }
    };

    private AdapterView.OnItemClickListener mFeedPostListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            startActivity(new Intent(MainActivity.this, DisplayActivity.class));
        }
    };

    /**
     * Refresh the RSS/Atom feed list.
     **/
    private void refreshFeed() {
        //TODO Fetch the feed

        Toast.makeText(getBaseContext(), "Fetching...",Toast.LENGTH_LONG).show();
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
}
