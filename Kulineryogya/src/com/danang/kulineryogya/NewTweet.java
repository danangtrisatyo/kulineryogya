package com.danang.kulineryogya;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class NewTweet extends Activity {
	
	EditText et;
	Button twit;
	SharedPreferences prefs;
	String APIKEY = "cjggRuUAKe7zOajdoPKTPA";
	String APISECRET = "iUwdMje24pISGoY5hORTtSkEQ9RNBb8swuGAi66ho";
	String CALLBACK = "oauth://twitter";
	OAuthService service;
	Token accessToken;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweetui);
        
        service = new ServiceBuilder().provider(TwitterApi.class).apiKey(APIKEY).apiSecret(APISECRET).build();
    	prefs = getSharedPreferences("prefs", 0);
    	accessToken = new Token(prefs.getString("aToken", null), prefs.getString("aSecret", null)); 
        
        Intent i = getIntent();
        Object o = i.getExtras().get("data");
        et = (EditText) findViewById(R.id.editText1);
        twit = (Button) findViewById(R.id.Tweet);
        
        et.setText("#Jogja " + o.toString());
        
        twit.setOnClickListener(new Button.OnClickListener() 
	    {
	        public void onClick (View v) 
	        {
	        	OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1/statuses/update.json");
	            request.addBodyParameter("status", et.getText().toString());
	            service.signRequest(accessToken, request);
	            request.send();
	            
	            Toast.makeText(getApplicationContext(), "Done", 3);
	            finish();
	        }
	    });

        
	}
}
