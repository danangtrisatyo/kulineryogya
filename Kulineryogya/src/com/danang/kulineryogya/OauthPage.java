package com.danang.kulineryogya;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.*;

//import com.sun.corba.se.spi.orbutil.fsm.Action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OauthPage extends Activity {
	String APIKEY = "cjggRuUAKe7zOajdoPKTPA";
	String APISECRET = "iUwdMje24pISGoY5hORTtSkEQ9RNBb8swuGAi66ho";
	String CALLBACK = "oauth://twitter";
	Button next;
	SharedPreferences shpref;
	OAuthService service;
	Token requestToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oauth);
		initControls();
		System.setProperty("java.net.useSystemProxies", "true");

		service = new ServiceBuilder().provider(TwitterApi.class).apiKey(APIKEY).apiSecret(APISECRET).callback(CALLBACK).build();

		final WebView webview = (WebView) findViewById(R.id.webViewz);
		final TextView res = (TextView) findViewById(R.id.textView2);

		WebSettings ws = webview.getSettings();
		ws.setJavaScriptEnabled(true);

		requestToken = service.getRequestToken();
		String authURL = service.getAuthorizationUrl(requestToken);

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				// check for our custom callback protocol otherwise use default
				// behavior
				if (url.startsWith("oauth")) {
					// authorization complete hide webview for now.
					//webview.setVisibility(View.GONE);

					Uri uri = Uri.parse(url);
					String verifier = uri.getQueryParameter("oauth_verifier");
					Verifier v = new Verifier(verifier);

					// save this token for practical use.
					Token accessToken = service.getAccessToken(requestToken, v);

					shpref = getSharedPreferences("prefs", 0);
					SharedPreferences.Editor editor = shpref.edit();

					editor.putString("aToken", accessToken.getToken());
					editor.putString("aSecret", accessToken.getSecret());

					// The requestToken is saved for use later on to verify the OAuth request.
					// See onResume() below
					editor.putString("requestToken", requestToken.getToken());
					editor.putString("requestSecret", requestToken.getSecret());

					editor.commit();



					//if (uri.getHost().equals("twitter")) {

					//						OAuthRequest req = new OAuthRequest(Verb.POST,
					//								"https://api.twitter.com/1/statuses/update.json");
					//						req.addBodyParameter("status", "");
					//						service.signRequest(accessToken, req);
					//						Response response = req.send();
					//webview.setVisibility(View.GONE);
					//res.setText(response.getBody());


					//}

					webview.setVisibility(View.GONE);
					res.setText("Klik Next");
					return true;
				}

				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		webview.loadUrl(authURL);
	}

	private void initControls()
	{
		next = (Button)findViewById(R.id.next);

		final Intent intent = new Intent(getApplicationContext(),TimelineActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		next.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick (View v) 
			{
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume()
	{
		super.onResume();

		Intent i = getIntent();

		if(i != null) 
		{
			Uri uri = i.getData();
			if(uri != null)
			{
				String oauthVerifier = uri.getQueryParameter("oauth_verifier");

				Verifier verifier = new Verifier(oauthVerifier);

				requestToken = new Token(shpref.getString("requestToken", null), shpref.getString("requestSecret", null));

				Token accessToken = service.getAccessToken(requestToken, verifier);

				// Save the access token.
				SharedPreferences.Editor editor = shpref.edit();
				editor.remove("requestToken");
				editor.remove("requestSecret");
				editor.putString("aToken", accessToken.getToken());
				editor.putString("aSecret", accessToken.getSecret());
				editor.commit();

				// Start the film list activity.
				final Intent intent = new Intent(this,TimelineActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		}
	} 
}
