package com.danang.kulineryogya;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.*;
import android.view.ContextMenu.*;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;


public class TimelineActivity extends ListActivity {
	
	String CALLBACK = "oauth://twitter";
	String ATOKEN_FILENAME = "af";
	String ASECRET_FILENAME = "as";
	StringBuffer at = new StringBuffer("");
	StringBuffer as = new StringBuffer("");
	String APIKEY = "3LhMz5mcDrdDsZuMchyew";
	String APISECRET = "bmvpsViHcc9T60dC2Z6AhFrIGx20hHgM3lo9B8ZI";
	
	SharedPreferences prefs;
	OAuthService service;
	Token accessToken;
	Token requestToken;
	/** Called when the activity is first created. */
	String MY_APP_TAG = "com.jogjaupdate.android";
		
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.test_list);
       /* String[] items = new String[] { "Foo", "Bar", "Fizz", "Bin", "ea", "ea", "ea", "ea" }; 
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.tweet_row, R.id.text1, items);
        setListAdapter(adapter);*/
        setListAdapter(new ArrayAdapter<String>(this, R.layout.tweet_row, this.fetchTwitterTimeline("Ridwan")));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        registerForContextMenu(getListView());
    }
   
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
    	super.onListItemClick(l,v,position,id);
    	final String selection = this.getListAdapter().getItem(position).toString();
    	final CharSequence[] dialogitem = {"Reply", "Reply All", "Retweet"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(TimelineActivity.this);
        builder.setTitle("What do you want to do?");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				switch(item){
				case 0 :
					Intent reply = new Intent(getApplicationContext(), NewTweet.class); //KulineryogyaActivity.class); 
					reply.putExtra("data", selection);
			    	startActivity(reply);
					break;
				case 1 :
					Toast.makeText(getApplicationContext(), "reply all", Toast.LENGTH_SHORT).show();
					break;
				case 2 :
					Toast.makeText(getApplicationContext(), "retweet", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
        builder.create().show();
    }	
      
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater mi = getMenuInflater();
    	mi.inflate(R.menu.main_menu, menu);
    return true;
    }*/
   
    public String Query(String s) {
    	{
    	    OAuthRequest request = new OAuthRequest(Verb.GET, s);
    	    service.signRequest(accessToken, request);
    	    Response response = request.send();
    	    String text = response.getBody();
    	    return text;
    	}

    }
    
    public OAuthService getService(){
    	return service;
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