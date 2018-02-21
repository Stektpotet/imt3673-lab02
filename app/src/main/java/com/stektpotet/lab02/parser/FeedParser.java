//package com.stektpotet.lab02.parser;
//
//import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParser;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//
//import android.util.Log;
//import android.util.Xml;
//
//import javax.xml.XMLConstants;
//
///**
// * FeedP - Created by halvor on 19.02.18.
// */
//public class FeedParser {
//
//    private static final String NAMESPACE = null;
//
//    private static final String RSS_2 = "rss";
//    private static final String ATOM = "feed";
//
//    public static final String ATOM_TAG_FEED = "feed";
//    public static final String ATOM_TAG_ENTRY = "entry";
//
//    //REQUIRED FOR BOTH FEED AND ENTRY
//    public static final String ATOM_TAG_ID = "id";
//    public static final String ATOM_TAG_TITLE = "title";
//    public static final String ATOM_TAG_UPDATED = "updated";
//
//
//    public AtomFeedModel parse(InputStream in) throws  XmlPullParserException, IOException {
//
//        Hashtable<String, ArrayList<String>> tags = new Hashtable<>();
//        String tagName;
//        XmlPullParser parser = Xml.newPullParser();
//
//        try {
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            parser.setInput(in, NAMESPACE);
//
//            while (parser.nextTag() != XmlPullParser.END_DOCUMENT) {
//                tagName = parser.getName();
//                if(tags.containsKey(tagName)) {
//                    tags.get(tagName).add(parser.getText());
//                } else {
//                    ArrayList<String> l = new ArrayList<>();
//                    l.add(parser.getText());
//                    tags.put(tagName, l);
//                }
//
//            }
//        } finally {
//            in.close();
//        }
//    }
//
//    private Hashtable<String, ArrayList<String>> enlistDepth(XmlPullParser parser, int depth) {
//        while(parser.getDepth() == depth) {
//            if(parser.next() )
//        }
//    }
//
//
//}
//
