package com.stektpotet.lab02.parser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * AtomFeedModel -
 */
public class AtomFeedModel {

    public String id;
    public String title;
    public String updated;


    public List<Entry> entries = new ArrayList<>();
    public AtomFeedModel() {}

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

    public static final String TAG_ENTRY_CONTENT = "content";
/*
========================================================================================
*/

    public static void ParseFeed(XmlPullParser parser) {

    }

}