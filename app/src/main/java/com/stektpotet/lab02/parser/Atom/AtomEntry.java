package com.stektpotet.lab02.parser.Atom;

import com.stektpotet.lab02.parser.FeedEntry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by halvor on 22.02.18.
 */

public class AtomEntry implements FeedEntry<AtomEntry> {

    public static final String TAG_ENTRY = "entry";
    public static final String TAG_CONTRIBUTOR = "contributor";
    public static final String TAG_RIGHTS = "rights";

    public static AtomEntry readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {

        parser.require(XmlPullParser.START_TAG, null, TAG_ENTRY);
        while (parser.nextTag() == XmlPullParser.START_TAG) {
            String tag = parser.getName();
            switch (tag) {
                case FeedEntry.TAG_ID:

                case FeedEntry.TAG_TITLE:
            }
        }

        return null;
    }
}
