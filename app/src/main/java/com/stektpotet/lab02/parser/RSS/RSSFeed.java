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
    public static final String TAG_DESCRIPTION = "description";

    public final String description;

    RSSFeed(ArrayList<RSSEntry> list, String... args) {
        super(list, args[0], args[1]);
        this.description = args[2];
    }


    protected RSSFeed(Parcel in) {
        super(in);
        description = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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
