package com.stektpotet.lab02.parser.Atom;

import android.os.Parcel;
import android.os.Parcelable;

import com.stektpotet.lab02.parser.Feed;
import com.stektpotet.lab02.parser.FeedParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * AtomFeed -
 */
public class AtomFeed extends Feed<AtomEntry> {

    public static final String TAG_FEED = "feed";
    public static final String TAG_ENTRY = AtomEntry.TAG_ENTRY;
    public static final String TAG_ID = "id";
    public static final String TAG_GENERATOR = "generator";
    public static final String TAG_ICON = "icon";
    public static final String TAG_LOGO = "logo";
    public static final String TAG_RIGHTS = "rights";
    public static final String TAG_SUBTITLE = "subtitle";

    AtomFeed(ArrayList<AtomEntry> entries, String... args) /*Insert additional parameters*/ {
        super(entries, args[0], args[1]);
    }

    protected AtomFeed(Parcel in) { super(in); }
    public static final Creator<AtomFeed> CREATOR = new Creator<AtomFeed>() {
        @Override
        public AtomFeed createFromParcel(Parcel in) {
            return new AtomFeed(in);
        }

        @Override
        public AtomFeed[] newArray(int size) {
            return new AtomFeed[size];
        }
    };
//
//
//    public static final String TAG_FEED = "feed";
//
//    //REQUIRED FOR BOTH FEED AND ENTRY
//    public static final String TAG_ID = "id";
//    public static final String TAG_TITLE = "title";
//    public static final String TAG_UPDATED = "updated";
//
//    public static final String TAG_AUTHOR = "author";
//    public static final String TAG_LINK = "link";
//    public static final String TAG_CATEGORY = "category";
//    public static final String TAG_CONTRIBUTOR = "contributor";
//    public static final String TAG_FEED_GENERATOR = "generator";
//    public static final String TAG_FEED_ICON = "icon";
//    public static final String TAG_FEED_LOGO = "logo";
//    public static final String TAG_RIGHTS = "rights";
//    public static final String TAG_FEED_SUBTITLE = "subtitle";
//
//    public static final String TAG_ENTRY = "entry";
//
//    public static final String TAG_ENTRY_CONTENT = "content";


}