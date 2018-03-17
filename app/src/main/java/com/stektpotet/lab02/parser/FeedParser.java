package com.stektpotet.lab02.parser;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;

import com.stektpotet.lab02.parser.Atom.AtomFeed;
import com.stektpotet.lab02.parser.Atom.AtomFeedParser;
import com.stektpotet.lab02.parser.RSS.RSSFeed;
import com.stektpotet.lab02.parser.RSS.RSSFeedParser;

/**
 * FeedP - Created by halvor on 19.02.18.
 */
public class FeedParser {

    public static final String TAG = FeedParser.class.getName();

    @Nullable
    public static Feed parse(InputStream in, int maxItems) throws  XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    switch (parser.getName())
                    {
                        case AtomFeed.TAG_FEED:
                            Log.d("FEED_PARSING.parse", "Parsing as ATOM feed!");
                            return AtomFeedParser.parse(parser, maxItems);
                        case RSSFeed.TAG_RSS:
                            Log.d("FEED_PARSING.parse", "Parsing as RSS feed!");
                            return RSSFeedParser.parse(parser, maxItems);
                    }
                }

            }
        } finally {
            in.close();
        }
        return null;
    }


    public static String readText(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        String text = "";
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, tag);
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.getText();
            Log.d("FEED_PARSING.read_text."+tag, text);
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, XmlPullParser.NO_NAMESPACE, tag);
        return text;
    }

    public static String readAttribute(XmlPullParser parser, String tag, String attribute) throws XmlPullParserException, IOException {
            parser.nextTag();
        return parser.getAttributeValue(null, attribute);
    }

    /**
     * @param parser - current parser in the state where the current tag is the one you want to skip
     * @throws XmlPullParserException
     * @throws IOException
     *
     * See <a href="https://developer.android.com/training/basics/network-ops/xml.html#skip">
     */
    public static void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException(parser.getName() + " TAG: " + parser.getEventType() +"\n" + parser.getText());
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    Log.d("FEED_PARSING.skip_tag."+parser.getName(), ""+parser.getText());
                    break;
            }
        }
    }
}

