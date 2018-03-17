package com.stektpotet.lab02.parser.Atom;

import android.util.Log;

import com.stektpotet.lab02.parser.FeedParser;
import com.stektpotet.lab02.parser.RSS.RSSEntry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;


public class AtomFeedParser {

    public static AtomFeed parse(XmlPullParser parser, int maxEntries) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, AtomFeed.TAG_FEED);
        String title = "", updated = "", id = "", link = "";
        ArrayList<AtomEntry> entries = new ArrayList<>();

        while(parser.nextTag() == XmlPullParser.START_TAG && entries.size() < maxEntries) {
            String tag = parser.getName();

            Log.d("PARSE_PLEASE_FEED", tag);
            switch (tag) {
                case AtomFeed.TAG_TITLE:
                    title = FeedParser.readText(parser,tag); break;
                case AtomFeed.TAG_ID:
                    link  = FeedParser.readText(parser, tag); break;
                case AtomFeed.TAG_ENTRY:
                    entries.add(readEntry(parser)); break;
                default:
                    FeedParser.skipTag(parser);
            }
        }
        return new AtomFeed(entries,title,link);
    }


    private static AtomEntry readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {
        String[] args = new String[3];

        parser.require(XmlPullParser.START_TAG, null, AtomEntry.TAG_ENTRY);

        while (parser.nextTag() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            Log.d("PARSE_PLEASE", tag);
            switch (tag) {
                case AtomEntry.TAG_TITLE:
                    args[0] = FeedParser.readText(parser, tag); break;
                case AtomEntry.TAG_LINK:
//                    args[1] = FeedParser.readText(parser, tag); break;
                    args[1] = FeedParser.readAttribute(parser, tag, "href"); break;
                case AtomEntry.TAG_SUMMARY:
                    Log.d("PARSE_PLEASE_SUMMARY", tag);
                    args[2] = FeedParser.readText(parser,tag);
                    Log.d("PARSE_PLEASE_SUMMARY", args[2]);
                    break;
                default:
                    FeedParser.skipTag(parser);
            }
        }

        return new AtomEntry(args[0], args[1], args[2]);
    }
}
