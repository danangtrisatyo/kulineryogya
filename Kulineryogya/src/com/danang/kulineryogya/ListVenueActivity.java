package com.danang.kulineryogya;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListVenueActivity extends TabActivity
{
	private Button btnback;
	protected ListAdapter adapter;
	protected ListView numberList;
	String[] menuArray; 
	protected Cursor cursor;
	SQLHelper dbHelper;
	TabHost thost;
	FrameLayout flayout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabview);

		this.numberList = (ListView) this.findViewById(R.id.ListView01);

		Intent i = getIntent();

		//String nama = i.getStringExtra("nama");
		//String pos_latitude = "-7.607861"; // i.getStringExtra("pos_latitude");
		//String pos_longitude = "110.203750"; //i.getStringExtra("pos_longitude");

		String pos_latitude = i.getStringExtra("pos_latitude");
		String pos_longitude = i.getStringExtra("pos_longitude");

		//url = url + "?latitude=" + pos_latitude + "&longitude=" + pos_longitude;
		//url = url + "?latitude=-7.607861&longitude=110.203750";

		//xResult = getRequest(url); // + "?nama=" + mynama.toString());
		try {
			//parse();
		} catch (Exception e) {
			e.printStackTrace();
		}


		//numberList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, menuArray));

		//setListAdapter(new MyCustomAdapter(numberList, R.layout.rowpilihan, menuArray));

		dbHelper = new SQLHelper(this);

		SQLiteDatabase db = dbHelper.getReadableDatabase();

		cursor = db.rawQuery("SELECT * FROM kuliner",null);

		menuArray = new String[cursor.getCount()];
		cursor.moveToFirst();
		for (int cc=0; cc < cursor.getCount(); cc++)
		{
			cursor.moveToPosition(cc);
			if (cursor.getString(1).equals("") == false)
			{
				menuArray[cc] = cursor.getString(1).toString();
			}
			//menuArray[cc] = cursor.getString(1);
		}

		numberList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, menuArray));

		numberList.setSelected(true);
		numberList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String selection = menuArray[arg2]; //.getItemAtPosition(arg2).toString();
				//Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
				Intent i = null;
				i = new Intent(ListVenueActivity.this, DetailActivity.class);

				i.putExtra("nama", String.valueOf(menuArray[arg2].toString()));

				startActivity(i);

			}});
		thost = getTabHost();
		TabSpec tspec = thost.newTabSpec("tab1");
		tspec.setIndicator("Map");
		Context ctx = this.getApplicationContext();
		Intent x = new Intent(ctx, PetaActivity.class);
		tspec.setContent(x);
		thost.addTab(tspec);
		thost.addTab(thost.newTabSpec("tab2").setIndicator("List").setContent(R.id.ListView01));

	}


	private View.OnClickListener ondaftar=new View.OnClickListener() {
		public void onClick(View v) {
			Intent i = null;
			i = new Intent(ListVenueActivity.this, DetailActivity.class);

			i.putExtra("pos_latitude", "-7.75492");
			i.putExtra("pos_longitude", "110.374138");

			startActivity(i);
		}
	};

	Intent ii = null;

	private View.OnClickListener onlocation=new View.OnClickListener() {
		public void onClick(View v) {

			ii = new Intent(ListVenueActivity.this, KulineryogyaActivity.class);
			startActivity(ii);
		}
	};

	private View.OnClickListener onabout=new View.OnClickListener() {
		public void onClick(View v) {
			Intent iii = null;
			iii = new Intent(ListVenueActivity.this, KulineryogyaActivity.class);
			//iii = new Intent(MainActivity.this, InputDataActivity.class);
			startActivity(iii);
		}
	};

	private View.OnClickListener onexit=new View.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
}
