package com.stektpotet.lab02.parser.Atom;

import com.stektpotet.lab02.parser.Feed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * AtomFeed -
 */
public class AtomFeed implements Feed<AtomFeed> {

    public static final String TAG_FEED = "feed";
    public static final String TAG_GENERATOR = "generator";
    public static final String TAG_ICON = "icon";
    public static final String TAG_LOGO = "logo";
    public static final String TAG_RIGHTS = "rights";
    public static final String TAG_SUBTITLE = "subtitle";



    public final URI    id      = null;
    public final String title   = null;
    public final String updated = null;

    public class Entry {
        public String id;
        public String title;
        public String updated;
        public String link;

        public List<String> Categories;
        public List<String> Contributor;
        public Entry() {}
        public Entry(String id, String title, String updated) {
            this.id = id; this.title = title; this.updated = updated;
        }

    }

    public List<Entry> entries = new ArrayList<>();
    private AtomFeed(String title, String link) /*Insert additional parameters*/ {

    }

    public static AtomFeed read(XmlPullParser parser, int maxItems) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, TAG_FEED);
        String title = "", updated = "", id = "", link = "";


        while(parser.nextTag() == XmlPullParser.START_TAG) {
            String tag = parser.getName();
            switch (tag) {
                case TAG_TITLE:
                    title = readText(parser,TAG_TITLE); break;
                case TAG_LINK:
                    link = readText(parser, TAG_LINK); break;
                default:
                    skip(parser);
            }
        }
        return new AtomFeed(title,link);
    }

    private static String readText(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        String text = "";
        parser.require(XmlPullParser.START_TAG, null, tag);
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.getText();
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, null, tag);
        return text;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

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
/*
========================================================================================
*/


}