package com.stektpotet.lab02.parser.RSS;

import android.util.Log;
import android.util.Xml;

import com.stektpotet.lab02.parser.FeedParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by halvor on 22.02.18.
 */
public class RSSFeedParser {

    public static RSSFeed parse(XmlPullParser parser, int maxItems) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, RSSFeed.TAG_RSS);
        String[] args = new String[5];
        ArrayList<RSSEntry> items = new ArrayList<>();

        parser.nextTag(); //Enter channel
        parser.require(XmlPullParser.START_TAG, null, RSSFeed.TAG_CHANNEL);

        while(parser.nextTag() != XmlPullParser.END_TAG && items.size() < maxItems) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            switch (tag) {
                case RSSFeed.TAG_TITLE:
                    args[0] = FeedParser.readText(parser,tag); break;
                case RSSFeed.TAG_LINK:
                    args[1] = FeedParser.readText(parser, tag); break;
                case RSSFeed.TAG_DESCRIPTION:
                    args[2]  = FeedParser.readText(parser, tag); break;
                case RSSFeed.TAG_ITEM:
                    items.add(readEntry(parser)); break;
                default:
                    FeedParser.skipTag(parser);
            }
        }
        return new RSSFeed(items, args);
    }

    private static RSSEntry readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {
        String[] args = new String[3];

        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, RSSEntry.TAG_ITEM);
        while (parser.nextTag() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            Log.d("FEED_PARSING.read_item."+tag, tag);
            switch (tag) {
//                case RSSEntry.TAG_ID:
//                    link = FeedParser.readText(parser, tag); break;
                case RSSEntry.TAG_TITLE:
                    args[0] = FeedParser.readText(parser, tag); break;
                case RSSEntry.TAG_LINK:
                    args[1] = FeedParser.readText(parser, tag); break;
                case RSSEntry.TAG_DESCRIPTION:
                    args[2] = FeedParser.readText(parser,tag); break;
                default:
                    FeedParser.skipTag(parser);
            }
        }

        return new RSSEntry(args);
    }
}
