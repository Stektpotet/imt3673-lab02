package com.stektpotet.lab02.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by halvor on 22.02.18.
 */

public interface FeedEntry<T extends FeedEntry<? super T>> {

    //REQUIRED FOR BOTH FEED AND ENTRY
    String TAG_ID = "id";
    String TAG_TITLE = "title";
    String TAG_CATEGORY = "category";
    String TAG_AUTHOR = "author";

    //T readEntry(XmlPullParser parser) throws IOException, XmlPullParserException;
// TODO: look into possibilities here, this method should be interfaced, but as a static...
    // For now, let's just pretend it's interfaced.
}
