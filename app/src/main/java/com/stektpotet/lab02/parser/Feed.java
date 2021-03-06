package com.stektpotet.lab02.parser;

import android.os.Parcel;
import android.os.Parcelable;

import com.stektpotet.lab02.parser.Atom.AtomFeed;
import com.stektpotet.lab02.parser.RSS.RSSEntry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class Feed<T extends FeedEntry> implements Parcelable {

    //Common tags appearing in both RSS and Atom
    public static final String TAG_TITLE = "title";
    public static final String TAG_LINK = "link";

    public final String title;
    public final String link;
    public final List<T> elements;

    protected Feed(ArrayList<T> list, String title, String link) {
        this.elements = list;
        this.title = title;
        this.link = link;
    }

    protected Feed(Parcel in) {
        title = in.readString();
        link = in.readString();
        elements = in.readArrayList(RSSEntry.class.getClassLoader());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeList(elements);
    }
}
