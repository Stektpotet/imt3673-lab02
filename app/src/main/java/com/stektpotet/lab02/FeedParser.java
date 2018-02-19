package com.stektpotet.lab02;

import org.xml.sax.EntityResolver;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Xml;

/**
 * FeedP - Created by halvor on 19.02.18.
 */
public class FeedParser {

    private static final String RSS_2 = "rss";
    private static final String ATOM = "feed";


    public List parse(InputStream in) throws  XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            switch (parser.getName())
            {
                case RSS_2:
                    return readFeedRSS(parser);
                case ATOM:
                    return readFeedAtom(parser);
                default:
                    throw new XmlPullParserException("Feed Type provided is not readable by parser...");
                    return null;
            }
        } finally {
            in.close();
        }
    }

    private List readFeedRSS(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            // Starts by looking for the entry tag
            if (tag.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private AtomFeed readFeedAtom(XmlPullParser parser) throws XmlPullParserException, IOException {


        return entries;
    }

    public static class RSSReader {

    }

    public static class AtomReader {

    }


    /**
     * Class representation of an Atom feed
     */
    public class AtomFeed {

        public final String id;
        public final String title;
        public final String updated;
        public List<Entry> entries = new ArrayList<>();

        public AtomFeed(String id, String title, String updated) {
            this.id=id; this.title=title; this.updated = updated;
        }

        public class Entry {
            public final String id;
            public final String title;
            public final String updated;

            public Entry(String id, String title, String updated) {
                this.id = id; this.title = title; this.updated = updated;
            }

        }

        public static final String TAG_FEED = "feed";

        //REQUIRED FOR BOTH FEED AND ENTRY
        public static final String TAG_ID = "id";
        public static final String TAG_TITLE = "title";
        public static final String TAG_UPDATED = "updated";

        public static final String TAG_AUTHOR = "author";
        public static final String TAG_LINK = "link";
        public static final String TAG_CATEGORY = "category";
        public static final String TAG_CONTRIBUTOR = "contributor";
        public static final String TAG_FEED_GENERATOR = "generator";
        public static final String TAG_FEED_ICON = "icon";
        public static final String TAG_FEED_LOGO = "logo";
        public static final String TAG_RIGHTS = "rights";
        public static final String TAG_FEED_SUBTITLE = "subtitle";

        public static final String TAG_ENTRY = "entry";

        public static final String TAG_ENTRY_ = "content";
/*
========================================================================================
*/

    }
}
