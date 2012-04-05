package com.senecaapp.rssreader;

import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;

import com.senecaapp.R;

public class RssReaderActivity extends ListActivity {
    /** Called when the activity is first created. */
	
	private static final String FEED_URI = "http://news.google.ca/news?pz=1&cf=all&ned=ca&hl=en&output=rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.feedlist);
        setListAdapter(Adapters.loadCursorAdapter(this, R.xml.rss_feed,
                "content://xmldocument/?url=" + Uri.encode(FEED_URI)));
        getListView().setOnItemClickListener(new UrlIntentListener());
    }
}