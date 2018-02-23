package com.stektpotet.lab02.parser.RSS;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.stektpotet.lab02.parser.FeedEntry;
import com.stektpotet.lab02.parser.FeedParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by halvor on 22.02.18.
 */

public class RSSEntry extends FeedEntry implements Parcelable {

    public static final String TAG_ITEM = "item";

    RSSEntry(String title, String link) {
        super(title, link);
    }

    public RSSEntry(Parcel in) { super(in); }
    public static final Creator<RSSEntry> CREATOR = new Creator<RSSEntry>() {
        @Override
        public RSSEntry createFromParcel(Parcel in) {
            return new RSSEntry(in);
        }

        @Override
        public RSSEntry[] newArray(int size) {
            return new RSSEntry[size];
        }
    };
}
