package com.stektpotet.lab02.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by halvor on 22.02.18.
 */

public interface Feed<T extends Feed<? super T>> {

    //Common tags appearing in both RSS and Atom
    String TAG_TITLE = "title";
    String TAG_LINK = "link";

//    T read(XmlPullParser parser, int maxItems) throws XmlPullParserException, IOException;
// TODO: look into possibilities here, this method should be interfaced, but as a static...
    // For now, let's just pretend it's interfaced.
}
