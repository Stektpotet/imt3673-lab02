package com.stektpotet.lab02;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.stektpotet.lab02.parser.Feed;
import com.stektpotet.lab02.parser.FeedEntry;

public class DisplayActivity extends AppCompatActivity {

    public static final String TAG = DisplayActivity.class.getName();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private FeedEntryPagerAdapter mFeedEntryPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ProgressBar mProgressBar;
    private Feed mActiveFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mProgressBar = findViewById(R.id.display_progressBar);
        mViewPager = findViewById(R.id.display_container);

        Bundle extras = getIntent().getExtras();

        mActiveFeed = (Feed)extras.getParcelable(MainActivity.KEY_ACTIVE_FEED);

        setupActionBar();

        mFeedEntryPagerAdapter = new FeedEntryPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mFeedEntryPagerAdapter);
        mViewPager.setCurrentItem(extras.getInt(MainActivity.KEY_ITEM_INDEX));

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setActionBarTitle(String newTitle) {
        getSupportActionBar().setTitle(newTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class FeedEntryDisplayFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_ENTRY_URL = "entry_url";
        private static final String ARG_ENTRY_TITLE = "entry_title";

        private String mTitle;
        private String mLink;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FeedEntryDisplayFragment newInstance(FeedEntry entry) {
            FeedEntryDisplayFragment fragment = new FeedEntryDisplayFragment();
            Bundle args = new Bundle();
            args.putString(ARG_ENTRY_TITLE, entry.title);
            args.putString(ARG_ENTRY_URL, entry.link);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mTitle = getArguments().getString(ARG_ENTRY_TITLE);
            mLink = getArguments().getString(ARG_ENTRY_URL);
            Log.d(TAG + ".onCreate", "title: " +mTitle + "\nlink: " + mLink);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_display, container, false);
            WebView webView = rootView.findViewById(R.id.frag_display_entry_view);

            final DisplayActivity activity = (DisplayActivity) getActivity();

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    String title = ((FeedEntry)activity.mActiveFeed.elements.get(activity.mViewPager.getCurrentItem())).title;
                    activity.setActionBarTitle(title);
                }
            });

            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    activity.mProgressBar.setProgress(newProgress);
                    if(newProgress == 100) {
                        activity.mProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        activity.mProgressBar.setVisibility(View.VISIBLE);
                    }
                }
            });
            webView.loadUrl(mLink);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class FeedEntryPagerAdapter extends FragmentPagerAdapter {


        public FeedEntryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FeedEntryDisplayFragment.newInstance((FeedEntry) mActiveFeed.elements.get(position));
        }

        @Override
        public int getCount() {
            return mActiveFeed.elements.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ((FeedEntry)mActiveFeed.elements.get(position)).title;
        }
    }
}
