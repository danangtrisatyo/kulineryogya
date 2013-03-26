package com.danang.kulineryogya;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class KulineryogyaActivity extends Activity {
	Button btnEnter;
	protected Cursor cursor;
	SQLHelper dbHelper;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dbHelper = new SQLHelper(this);
        try {
 
        //	dbHelper.createDataBase();
        	Toast.makeText(this, "Success Accessing Database", Toast.LENGTH_LONG).show();
            
	 	} catch (Exception ioe) {
	 		Toast.makeText(this, "Error Create Database", Toast.LENGTH_LONG).show();
	 		throw new Error("Unable to create database");
	 		
	 	}
        
        btnEnter = (Button) findViewById(R.id.btnEnter);
		// daftarkan even onClick pada btnSimpan
        btnEnter.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i = new Intent(KulineryogyaActivity.this, ListVenueActivity.class);
				//i.putExtra("kategori", "mj");
				startActivity(i);
			}
		});
    }
}