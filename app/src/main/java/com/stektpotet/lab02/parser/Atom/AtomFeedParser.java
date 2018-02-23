package com.stektpotet.lab02.parser.Atom;

import com.stektpotet.lab02.parser.FeedParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by halvor on 22.02.18.
 */

public class AtomFeedParser {

    public static AtomFeed parse(XmlPullParser parser, int maxEntries) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, AtomFeed.TAG_FEED);
        String title = "", updated = "", id = "", link = "";
        ArrayList<AtomEntry> entries = new ArrayList<>();

        while(parser.nextTag() == XmlPullParser.START_TAG && entries.size() < maxEntries) {
            String tag = parser.getName();
            switch (tag) {
                case AtomFeed.TAG_TITLE:
                    title = FeedParser.readText(parser,tag); break;
                case AtomFeed.TAG_LINK:
                    link  = FeedParser.readText(parser, tag); break;
                case AtomFeed.TAG_ENTRY:
                    entries.add(readEntry(parser));
                default:
                    FeedParser.skipTag(parser);
            }
        }
        return new AtomFeed(title,link, entries);
    }


    private static AtomEntry readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {
        String title = "";
        String link = "";

        parser.require(XmlPullParser.START_TAG, null, AtomEntry.TAG_ENTRY);
        while (parser.nextTag() == XmlPullParser.START_TAG) {
            String tag = parser.getName();
            switch (tag) {
                case AtomEntry.TAG_ID:
                    link = FeedParser.readText(parser, tag);
                case AtomEntry.TAG_TITLE:
                    title = FeedParser.readText(parser, tag);
            }
        }

        return new AtomEntry(title, link);
    }
}