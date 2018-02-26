package com.stektpotet.lab02.parser;

import android.os.Parcel;
import android.os.Parcelable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by halvor on 22.02.18.
 */

public abstract class FeedEntry implements Parcelable {

    //REQUIRED FOR BOTH FEED AND ENTRY
    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_LINK = "link";
    public static final String TAG_CATEGORY = "category";
    public static final String TAG_AUTHOR = "author";

    public final String title;
    public final String link;

    protected FeedEntry(String title, String link) {
        this.title = title;
        this.link = link;
    }


    protected FeedEntry(Parcel in) {
        title = in.readString();
        link = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(link);
    }

    //T readEntry(XmlPullParser parser) throws IOException, XmlPullParserException;
// TODO: look into possibilities here, this method should be interfaced, but as a static...
    // For now, let's just pretend it's interfaced.
}
