# Lab 02: Simple RSS reader

**NOTE**:
In my Atom/RSS-parser I've only considered the tags I myself considered valuable for this lab. meaning ID/Link, summary/description, title + feed title and url.

## The idea

Create an application that allows the user to read content from any RSS feed. The app will consist of 3 activities: one with the list of items (ListView, for selecting content), one for article content display (for reading content), and User Preferences (for user to specify the preferences). 

## Preferences

The user should be able to specify in the preferences the URL to the RSS feed (RSS2.0-based or Atom-based), and, the limiting number of items that should be displayed in the ListView (10, 20, 50, 100), and the frequency at which the app fetches the content (10min, 60min, once a day). The app will fetch the RSS feed and populate the list UP to the limit number. When user clicks on a particular item, a detailed view should be shown, with the content of the article for that item. 

## Checklist

- [x] The git repository URL is correctly provided, such that command works: `git clone <url>` 

- [x] The code is well, logically organised and structured into appropriate classes. Everything should be in a single package.

- [x] It is clear to the user what RSS feed formats are supported (RSS2.0 and/or Atom)
- [x] The user can go to Preferences and set the URL of the RSS feed.
- [x] The user can go to Preferences and set the feed item limit.
- [x] The user can go to Preferences and set the feed refresh frequency.
- [x] The user can see the list of items from the feed on the home Activity ListView.
- [x] The user can go to a particular item by clicking on it. The content will be displayed in newly open activity. The back button puts the user back onto the main ListView activity to select another item. 
- [x] The user can press the back button from the main activity to quit the app. 
- [x] When the content article has graphics, it is rendered correctly. 

## Additional features I added
 - Swipable WebView, As the feed is aleardy parsed into parcelable items I figured I might as well send the list on into the display activity so that you can swipe between the articles listed in the main activity. This involved making an additional adapter for a viewpager that also is hooked up with a few callbacks to the webview to update things like the menubar-title.
 - I'm taking  use of android studio's template preference activity, this means it follows a lot of standard design and that the code in general follows standards for preference implementation (things like updating the summary of a preference item when changing the preference etc.).
 - I added URL helpers for users, which basically means the app guesses the actual URL for the user if the user has not written a fully _valid_ URL. example: www.nrk.no/trondelag/toppsaker.rss -> http://www.nrk.no/trondelag/toppsaker.rss as, strictly speaking, a scheme must be provided for an URL to be considered valid.
## Hints

Make sure that the logic for the fetching of articles is done by the app automatically with the frequency given by the user Preferences. How would you schedule it? 

_IntentService_

How would you prefetch the articles? 

For testing purposes, add a button to the main activity (the one with the list), to FORCE a fetch upon press of the button. Final app should not have the "fetch" button.

The content of the article should use WebView such that graphics of the content article is rendered correctly, if the content was an URL. If the content was a plain text, a simple text view can be used.

Make sure you use appropriate facilities (a library?) to help you parsing XML content. Should the user specify if the feed is in one format or another, or can the app detect it automatically? Can you use a library that parses RSS2.0 and Atom? What would be the benefit? What would be the limitation?
