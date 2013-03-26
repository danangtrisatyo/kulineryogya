package com.danang.kulineryogya;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class usingSQLHelper extends Activity {
	mySqlHelper dbHelper;
	private EditText ed_num;
	private EditText ed_name;
    protected Cursor cursor;
	private int id=-1;
    protected ListAdapter adapter;
    protected ListView numberList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db);
        this.ed_num = (EditText) this.findViewById(R.id.editText_num);
        this.ed_name = (EditText) this.findViewById(R.id.editText_name);
        this.numberList = (ListView) this.findViewById(R.id.ListView01);
        dbHelper = new mySqlHelper(this);
        numberList.setSelected(true);
        numberList.setOnItemClickListener(new OnItemClickListener() {
           	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
           		SQLiteDatabase db = dbHelper.getReadableDatabase();
           		cursor = db.rawQuery("SELECT * FROM data",null);
           		cursor.moveToPosition(arg2);
           		ed_num.setText(cursor.getString(1));
           		ed_name.setText(cursor.getString(2));
           		id=cursor.getInt(0);
			}});
        view();
    }
    private void addData(String num,String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
        db.execSQL("insert into "+ mySqlHelper.TABLE +" values(null,'"+num+"','"+name+"')");
        }
        catch(Exception e)
        {
        	ed_num.setText(e.toString());
        }
      }
    public void but_inClick(View v)
    {
    	addData(ed_num.getText().toString(),ed_name.getText().toString());
    	view();
    }
    public void but_DelClick(View v)
    {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	try{
    		if(id!=-1)
    		{
    			db.execSQL("delete from "+ mySqlHelper.TABLE +" where number='" +ed_num.getText().toString()+ "'");
    			view();
    		}
        }
    	catch(Exception e)
        {
        	ed_num.setText(e.toString());
        }
    }
    public void but_EditClick(View v)
    {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	try{
    		if(id!=-1)
    		{
    			db.execSQL("update data set number='"+ed_num.getText().toString()+"',name='"+ed_name.getText().toString()+"' where _id="+id);
    			view();
    		}
        }
    	catch(Exception e)
        {
        	ed_num.setText(e.toString());
        }
    }
    private void view()
    {
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
    	try{
    		cursor = db.rawQuery("SELECT * FROM data",null);
    		adapter = new SimpleCursorAdapter(
        			this, 
        			R.layout.view2, 
        			cursor, 
        			new String[] {"number","name"}, 
        			new int[] {R.id.number,R.id.name});
    		
    		numberList.setAdapter(adapter);}
            catch(Exception e)
            {
            	ed_num.setText(e.toString());
            }
    }
}