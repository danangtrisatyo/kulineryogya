package com.danang.kulineryogya;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.attr;
import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
    /** Called when the activity is first created. */
	final String CONTENT = "NARRATOR: It was known as the place\r\nwhere Noah left his Ark.\r\nAn African paradise teeming\r\nwith amazing creatures.\r\n(Hissing)\r\n(Snorting)\r\nBut then came war, with a park\r\ncalled Gorongosa caught in the crossfire.\r\n(Gunfire)\r\nOnly ghostly remnants of its wildlife survived.\r\n(Gunshot)\r\nBut now rangers and conservationists\r\nare fighting to bring back";
	//TweetParser tp = new TweetParser();
	protected Cursor cursor;
	SQLHelper dbHelper;
	Button button1;
	String nama;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        //result = tp.parseTL(this.getResources().openRawResource(R.raw.kulyogfavorite));
        //List<String> keys = new ArrayList<String>(result.keySet());
        String tweets = null;
        //for (String key: keys) {
        //	tweets += "@" + result.get("user.screen_name") + " " + result.get("text") + "\r\n";
        //}
        initialize();
        
        Intent i = getIntent();
		
		nama = i.getStringExtra("nama");
		
		dbHelper = new SQLHelper(this);
        
        SQLiteDatabase db = dbHelper.getReadableDatabase();
 		
 		cursor = db.rawQuery("SELECT * FROM kuliner WHERE nama_venue = '" + nama + "'",null);
        
        //menuArray = new String[cursor.getCount()];
		cursor.moveToFirst();
		if (cursor.getCount() > 0)
		{
			cursor.moveToPosition(0);
			TextView namaresto = (TextView) findViewById(R.id.namaresto);
			namaresto.setText(cursor.getString(1).toString());
			TextView dkonten = (TextView) findViewById(R.id.dkonten);
			dkonten.setText(cursor.getString(3).toString());
		}
		
		ArrayList<String> hsl = this.fetchTwitterTimeline("jogja");
		String semua = "";
		for (int z=0;z<hsl.size()-1;z++)
		{
			semua += hsl.get(z) + "\n";
		}
		
		TextView rkykonten = (TextView) findViewById(R.id.rkykonten);
		rkykonten.setText(semua);
		
		button1 = (Button) findViewById(R.id.button1);
		// daftarkan even onClick pada btnSimpan
		button1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i = new Intent(DetailActivity.this, PetaActivity.class);
				i.putExtra("nama", nama);
				startActivity(i);
			}
		});
    }
	
	public void initialize(){
		final TextView deskripsi = (TextView) findViewById(R.id.Deskripsi);
        final TextView review = (TextView) findViewById(R.id.revkulyog);
        TextView dkonten = (TextView) findViewById(R.id.dkonten);
        TextView rkykonten = (TextView) findViewById(R.id.rkykonten);

       
        //rkykonten.setText(tweets);
        dkonten.setText(CONTENT);
        final ScrollView sc1 = (ScrollView) findViewById(R.id.scrollView1);
        final ScrollView sc2 = (ScrollView) findViewById(R.id.scrollView2);
        //sc1.setVisibility(View.GONE);
        //sc2.setVisibility(View.GONE);
        
        deskripsi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sc1.getVisibility()==View.GONE) {
					sc1.setVisibility(View.VISIBLE);
					deskripsi.setBackgroundResource(R.drawable.headerup);
					deskripsi.setMaxLines(Integer.MAX_VALUE);
				} else {
					sc1.setVisibility(View.GONE);
					deskripsi.setBackgroundResource(R.drawable.headerdown);
					deskripsi.setMaxLines(1);
				}
			}
		});
        review.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sc2.getVisibility()==View.GONE) {
					sc2.setVisibility(View.VISIBLE);
					review.setMaxLines(Integer.MAX_VALUE);
					review.setBackgroundResource(R.drawable.headerup);
				} else {
					sc2.setVisibility(View.GONE);
					review.setMaxLines(1);
					review.setBackgroundResource(R.drawable.headerdown);
				}
			}
		});
 
	}
	
	public ArrayList<String> fetchTwitterTimeline(String query) {
        ArrayList<String> listItems = new ArrayList<String>();
        
        String host = "search.twitter.com";
       	String twitterURL = "http://"+host+"/search.json?q=%23"+query+"&rpp=25&include_entities=true&result_type=recent";
    	
        	            
    //-----------------------------------------------------------------------------------           
        try {
        	
        	HttpClient client = new DefaultHttpClient();
            BasicHttpContext localContext = new BasicHttpContext();
            HttpHost targetHost = new HttpHost(host, 80, "http");
            HttpGet httpget = new HttpGet(twitterURL);
            httpget.setHeader("Content-Type", "application/json");
            
            HttpResponse hresponse = client.execute(targetHost, httpget, localContext);
            
            HttpEntity entity = hresponse.getEntity();
            
        	
            Object content = EntityUtils.toString(entity);
            Log.d("Test", "OK: " + content.toString());
            
            JSONObject jo1 = new JSONObject(content.toString());
            JSONArray ja = jo1.getJSONArray("results");
            
            for(int i = 1; i <= ja.length(); i++){
    			JSONObject jo = ja.getJSONObject(i);
    			listItems.add("@" + jo.getString("from_user")+ " : \n" +jo.getString("text"));
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
        
        return listItems;
       }
}
