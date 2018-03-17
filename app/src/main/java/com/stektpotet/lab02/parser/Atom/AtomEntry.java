package com.stektpotet.lab02.parser.Atom;

import android.os.Parcel;

import com.stektpotet.lab02.parser.FeedEntry;
import com.stektpotet.lab02.parser.FeedParser;
import com.stektpotet.lab02.parser.RSS.RSSEntry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by halvor on 22.02.18.
 */

public class AtomEntry extends FeedEntry {

    public static final String TAG_ENTRY = "entry";
    public static final String TAG_SUMMARY = "summary";
    public static final String TAG_CONTRIBUTOR = "contributor";
    public static final String TAG_RIGHTS = "rights";

    AtomEntry(String title, String link, String summary) {
        super(title, link, summary);
    }

    public AtomEntry(Parcel in) { super(in); }
    public static final Creator<AtomEntry> CREATOR = new Creator<AtomEntry>() {
        @Override
        public AtomEntry createFromParcel(Parcel in) {
            return new AtomEntry(in);
        }

        @Override
        public AtomEntry[] newArray(int size) {
            return new AtomEntry[size];
        }
    };
}
