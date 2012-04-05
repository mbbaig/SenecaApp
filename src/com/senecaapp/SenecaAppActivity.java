package com.senecaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.senecaapp.gridview.GridViewActivity;
import com.senecaapp.rssreader.RssReaderActivity;

public class SenecaAppActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button rssreader = (Button) findViewById(R.id.rssreader);
        Button gridview = (Button) findViewById(R.id.gridview);
        
        rssreader.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent rss = new Intent();
				rss.setClass(SenecaAppActivity.this, RssReaderActivity.class);
				startActivity(rss);
			}
        	
        });
        
        gridview.setOnClickListener(new OnClickListener  () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent grid = new Intent();
				grid.setClass(SenecaAppActivity.this, GridViewActivity.class);
				startActivity(grid);
			}
        	
        });
    }
}