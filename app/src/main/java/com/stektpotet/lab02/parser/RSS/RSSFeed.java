package com.stektpotet.lab02.parser.RSS;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.stektpotet.lab02.parser.Feed;
import com.stektpotet.lab02.parser.FeedParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by halvor on 22.02.18.
 */

public class RSSFeed extends Feed<RSSEntry> implements Parcelable {

    public static final String TAG_RSS = "rss";
    public static final String TAG_CHANNEL = "channel";
    public static final String TAG_ITEM = RSSEntry.TAG_ITEM;

    RSSFeed(String title, String link, ArrayList<RSSEntry> list) {
        super(title, link, list);
    }

    protected RSSFeed(Parcel in) { super(in); }
    public static final Creator<RSSFeed> CREATOR = new Creator<RSSFeed>() {
        @Override
        public RSSFeed createFromParcel(Parcel in) {
            return new RSSFeed(in);
        }

        @Override
        public RSSFeed[] newArray(int size) {
            return new RSSFeed[size];
        }
    };
}
