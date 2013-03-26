package com.danang.kulineryogya;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

public class DataActivity  extends Activity {	
	protected Cursor cursor;
	SQLHelper dbHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);
        
        dbHelper = new SQLHelper(this);
        
        SQLiteDatabase db = dbHelper.getReadableDatabase();
 		
 		cursor = db.rawQuery("SELECT * FROM kuliner",null);
        
        //menuArray = new String[cursor.getCount()];
		cursor.moveToFirst();
		for (int cc=0; cc < cursor.getCount(); cc++)
		{
			cursor.moveToPosition(cc);
			Toast.makeText(DataActivity.this, cursor.getString(1).toString(), Toast.LENGTH_LONG).show();
			//if (cursor.getString(1).equals("") == false)
			//{
				//menuArray[cc] = cursor.getString(1).toString();
			//}
			//menuArray[cc] = cursor.getString(1);
		}
    }
}
