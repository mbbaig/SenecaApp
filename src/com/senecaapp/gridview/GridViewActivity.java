package com.senecaapp.gridview;

import com.senecaapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GridViewActivity extends Activity {
	
	private final CharSequence[] courses = { "course1", "course2", "course3", "course4", "course5" };
	private final CharSequence[] profs = { "prof1", "prof2", "prof3", "prof4", "prof5" };
	
	private String[] mThumbIds = {
            "","Monday","Tuesday","Wednesday","Thursday","Friday",
            "8:00","","","","","",
            "8:55","","","","","",
            "9:50","","","","","",
            "10:45","","","","","",
            "11:40","","","","","",
            "12:35","","","","","",
            "13:30","","","","","",
            "14:25","","","","","",
            "15:20","","","","","",
            "16:15","","","","","",
            "17:10","","","","","",
            "18:05","","","","",""
    };
	
	protected String text = "";
	protected int pos = 0;
	protected GridView gv;
	
    /** Called when the activity is first created.*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        GridView schedule = (GridView) findViewById(R.id.schedule);
        schedule.setBackgroundColor(Color.DKGRAY);
        
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mThumbIds );
        //gridview.setBackgroundResource(R.drawable.grid2);
        schedule.setAdapter(aa);

        schedule.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(HelloGridViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            	pos = position;
            	gv = (GridView) parent;
            	AlertDialog.Builder builder = new AlertDialog.Builder(GridViewActivity.this);
            	builder.setTitle("Pick a course");
            	builder.setItems(courses, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), courses[which], Toast.LENGTH_SHORT).show();
						text = courses[which].toString();
						AlertDialog.Builder builder2 = new AlertDialog.Builder(GridViewActivity.this);
		            	builder2.setTitle("Pick a Prof.");
		            	builder2.setItems(profs, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(), profs[which], Toast.LENGTH_SHORT).show();
								text += profs[which].toString();
								mThumbIds[pos] = text;
								gv.invalidateViews();
							}
						});
		            	AlertDialog alert2 = builder2.create();
		            	alert2.show();
					}
				});
            	AlertDialog alert = builder.create();
            	alert.show();
            }
        });
    }
}
