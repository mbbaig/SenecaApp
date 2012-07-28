package com.senecaap.ttcinfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.senecaapp.R;

public class TTCActivity extends Activity {

	Spinner sp;
	Stop selectedStop;
	List<Stop> stops;
	TextView tv;
	Button submitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.listlayout);

		try {
			init();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void init() throws StreamCorruptedException, IOException,
			ClassNotFoundException {

		submitButton = (Button) findViewById(R.id.bSub);
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fetchBusInfo();
			}
		});

		tv = (TextView) findViewById(R.id.tvText);
		tv.setTextColor(Color.WHITE);

		sp = (Spinner) findViewById(R.id.busStopSpinner);
		sp.setAdapter(new MySpinnerAdapter());

	}

	private class MySpinnerAdapter implements SpinnerAdapter {

		@SuppressWarnings("unchecked")
		public MySpinnerAdapter() throws StreamCorruptedException, IOException, ClassNotFoundException {
			ObjectInputStream is = new ObjectInputStream(getAssets().open("stops_list.ser"));
			stops = (List<Stop>) is.readObject();
			is.close();
			selectedStop = stops.get(0);
		}

		@Override
		public int getCount() {
			return stops.size();
		}

		@Override
		public Object getItem(int position) {
			return stops.get(position);
		}

		@Override
		public long getItemId(int position) {
			return stops.get(position).getCode();
		}

		@Override
		public int getItemViewType(int position) {
			return android.R.layout.simple_spinner_dropdown_item;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView v = new TextView(getApplicationContext());
			v.setTextColor(Color.WHITE);
			v.setText(stops.get(position).getName());
			return v;

		}

		@Override
		public int getViewTypeCount() {

			return 1;
		}

		@Override
		public boolean hasStableIds() {

			return false;
		}

		@Override
		public boolean isEmpty() {

			return false;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {

		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {

		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			return this.getView(position, convertView, parent);

		}

	}

	public class FetchTask extends AsyncTask<Stop, String, Stop> {

		@Override
		protected Stop doInBackground(Stop... params) {

			try {
				params[0].fetchBusInfo();
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			return params[0];
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			tv.setText(values[0]);
		}

		@Override
		protected void onPostExecute(Stop result) {

			super.onPostExecute(result);

			StringBuffer output = new StringBuffer("No Bus Data");

			if (result.getDirections() != null) {

				for (Direction d : result.getDirections()) {

					if (d.getPredictions() != null) {
						output = new StringBuffer("");
						output.append("Route " + d.getBranch() + ": ");
					
						for (Prediction p : d.getPredictions()) {

							output.append(p.getMinutes() + ", ");
						}
					}

					output.replace(output.length() - 2, output.length(), "");
					output.append("<br/>");
				}

				tv.setText(Html.fromHtml(output.toString()));

			}
		}

	}

	public class MyListView extends ListView {

		public MyListView() throws StreamCorruptedException, IOException,
				ClassNotFoundException {
			super(getApplicationContext());

		}

	}

	public void fetchBusInfo() {

		new FetchTask().execute((Stop) sp.getSelectedItem());

	}

}