package com.danang.kulineryogya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class mySqlHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "kuliner.db";
	private static final int DATABASE_VERSION = 1;
	
	// Table name
	public static final String TABLE = "data";

	// Columns
	public static final String number = "number";
	public static final String name = "name";

	public mySqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE kuliner ( id_venue integer primary key autoincrement, nama_venue text null, alamat text null, deskripsi text null, review text null, foto text null, latitude text null, longitude text null);";
		Log.d("Data", "onCreate: " + sql);
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (1, 'RM. Tio Ciu Bang Sinyo', 'Jl. Moses Gatotkaca (Depok), Sleman, DI Yogyakarta', 'Restoran cina yang disajikan dengan hotplate', 'Menu andalan itu cumi goreng tepung. Dan untuk kwe tiauw rasanya biasa aja, tidak spesial. Untuk harga sekitar Rp 15.000 sampai Rp 25.000 an. Warung makannya kalau malam (pas pergi ke sana) selalu ramai.', NULL, '-7.7764360658247', '110.39045333862305');";
		//Log.d("Data", "onCreate: " + sql);
		db.execSQL(sql);
		//String sql = "create table " + TABLE + "( _id"
		//+ " integer primary key autoincrement, " + number + " text not null, "
		//+ name + " text not null);";
		Log.d("Data", "onCreate: " + sql);
		//db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		
	}

}
