package com.danang.kulineryogya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "kuliner.db";
	private static final int DATABASE_VERSION = 1;
	
	// Table name
	public static final String TABLE = "data";

	// Columns
	public static final String number = "number";
	public static final String name = "name";

	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE kuliner ( id_venue integer primary key autoincrement, nama_venue text null, alamat text null, deskripsi text null, review text null, foto text null, latitude text null, longitude text null);";
		Log.d("Data", "onCreate: " + sql);
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (1, 'RM. Tio Ciu Bang Sinyo', 'Jl. Moses Gatotkaca (Depok), Sleman, DI Yogyakarta', 'Restoran cina yang disajikan dengan hotplate', 'Menu andalan itu cumi goreng tepung. Dan untuk kwe tiauw rasanya biasa aja, tidak spesial. Untuk harga sekitar Rp 15.000 sampai Rp 25.000 an. Warung makannya kalau malam (pas pergi ke sana) selalu ramai.', NULL, '-7.7764360658247', '110.39045333862305');";
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (2, 'il Mondo Pizza', 'Jalan Cendrawasih No. 21A (Demangan), Yogyakarta, DI Yogyakarta', 'resto yang menyuguhkan cita rasa asing khas Negara asalnya yaitu Negara Italia', 'Harga pizza berkisaran Rp 40 – 50 ribu, dan perlu diketahui oleh temen-temen saat akan memesan pizza di ala resto iL Mondo ini. Tidak ada menu pizza ukuran personal/ small, melainkan ukuran besar.', NULL, '-7.781198354544704', '110.39131164550781');";
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (3, 'Bakmi Mbah Mo', 'Code, Bantul, Bantul, Yogyakarta', 'Salah satu bakmi legendaris yang ada di yogyakarta', 'Model mie godog jawa ala mbah Mo bukan yang versi “nyemek” tapi kuah yang melimpah, campuran telur bebek dan kaldu dari ayam kampung memberikan sentuhan rasa yang nyamleng, tekstur mie yang dipilih jenis mie kecil dan kenyal.', NULL, '-7.8875559211385005', '110.34836761754497');";
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (4, 'Mie Jowo Pak Paino', 'Wirogunan (Jl. Bintaran Wetan Rw 01 Rt 03), Yogyakarta, DIY', 'Warung bakmi pak paino baru di buka tahun 2005, namun rasa dari bakmi pak paino ini sendiri bisa bikin pak SBY ketagihan.', 'Untuk menikmati bakmi godog dengan jeruk hangat kita cukup membayar Rp. 20.000 begitu juga dengan nasi goreng dan es teh nya. Harga makanan disini tergantung dari permintaan tambahan lauk nya.', NULL, '-7.803003519743304', '110.37499174448628');";
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (5, 'Pondok Cabe', 'Jalan C Simanjuntak No. 41 B, Yogyakarta, DI Yogyakarta', 'Sesuai dengan namanya, speciality dari rumah makan ini adalah Sambal tentunya, ada 15 sambal yang tersaji di daftar menu.', 'Nyari tempat makan murah di jogja sebenernya mudah, apalagi kota ini terkenal dengan sebuatan \"Kota Pelajar\" dimana pangsa pasar terbesar tentunya adalah para pelajar dan mahasiswa yang kantongnya pas-pasan. Salah satunya pondok cabe.', NULL, '-7.777116396095054', '110.3735876083374');";
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (6, 'Roti Bakar Beverly Hills', 'Jl. Prof. Dr. Yohanes No.V/1035 (Sagan), Yogyakarta', 'Salah satu tempat makan roti bakar yang dibuka sejak tahun 1994. Dengan mengusung konsep kafe, menawarkan aneka menu roti bakar, jagung bakar, dan pisang bakar.', 'Untuk menunya tidak hanya roti bakar, ada juga pisang bakar, ada mie rebus dengan kuah susu murni yang menjadi andalan disini.', NULL, '-7.7790400039440435', '110.37957075637465');";
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (7, 'Lesehan Terang Bulan', 'Jalan Jenderal Ahmad Yani 105 (Malioboro), Yogyakarta, DI Yogyakarta', 'Salah satu lesehan malam di malioboro yang menjadi buruan wisatawan di jogja.', 'Menu utamanya adalah ayam dan burung dara goreng. Tapi juga tersedia gudeg dan nasi liwet.Biaya makan sekitar Rp 20000 ke 30000. Sambil makan, kita ditemani oleh pengamen-pengamen.', NULL, '-7.796632900540383', '110.36556243896484');";
		db.execSQL(sql);
		sql = "INSERT INTO `kuliner` (`id_venue`, `nama_venue`, `alamat`, `deskripsi`, `review`, `foto`, `latitude`, `longitude`) VALUES (8, 'Balé Raos Royal Cuisine Restaurant', 'Magangan Kulon 1 Kraton Yogyakarta, Yogyakarta, DI Yogyakarta', 'Bale Raos terletak di seputar Kraton Yogyakarta, dalam nuansa arsitek Jawa, bangunan Joglo yang berdiri dengan agung, serta aura Kraton yang kental', 'Juru masak yang terlatih akan menyuguhkan hidangan bercita rasa tinggi yaitu masakan favourite dari Sri Sultan Hamengku Buwono VII sampai ke Sultan Hamengku Buwono X, serta berbagai hidangan keluarga Kraton Yogyakarta.', NULL, '-7.808565914860904', '110.36322140704239');";
		db.execSQL(sql);
		//Log.d("Data", "onCreate: " + sql);
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
