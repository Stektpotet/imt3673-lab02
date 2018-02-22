package com.stektpotet.lab02.parser;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import android.util.Xml;

import com.stektpotet.lab02.parser.Atom.AtomFeed;

/**
 * FeedP - Created by halvor on 19.02.18.
 */
public class FeedParser {

    private static final String NAMESPACE = null;

    private static final String RSS_2 = "rss";
    private static final String ATOM = "feed";


    public Feed parse(InputStream in, int maxItems) throws  XmlPullParserException, IOException {

        Hashtable<String, ArrayList<String>> tags = new Hashtable<>();
        String tagName;
        XmlPullParser parser = Xml.newPullParser();

        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, NAMESPACE);

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    switch (parser.getName())
                    {
                        case AtomFeed.TAG_FEED:
                            return AtomFeed.read(parser, maxItems);
                    }
                }

            }
        } finally {
            in.close();
        }
        return null;
    }

}

