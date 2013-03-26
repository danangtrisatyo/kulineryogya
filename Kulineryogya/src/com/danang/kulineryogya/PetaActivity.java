package com.danang.kulineryogya;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class PetaActivity extends MapActivity {
	private MapView mapView;
	protected Cursor cursor;
	SQLHelper dbHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peta);
        
        mapView = (MapView) findViewById(R.id.mapView);
		mapView.displayZoomControls(true);
		
		double lat = -6.2532592788520005;
		double lng = 106.853239291777;
		GeoPoint point = new GeoPoint((int) (lat *1E6),(int) (lng * 1E6));
		mapView.getController().animateTo(point);
		
		mapView.getController().setZoom(15);
		mapView.setBuiltInZoomControls(true);
		
		Intent i = getIntent();
		
		String nama = i.getStringExtra("nama");
		
		dbHelper = new SQLHelper(this);
        
        SQLiteDatabase db = dbHelper.getReadableDatabase();
 		
 		cursor = db.rawQuery("SELECT * FROM kuliner WHERE nama_venue = '" + nama + "'",null);
        
        //menuArray = new String[cursor.getCount()];
		cursor.moveToFirst();
		if (cursor.getCount() > 0)
		{
			cursor.moveToPosition(0);
			
			lat = Double.parseDouble(cursor.getString(6).toString());
			lng = Double.parseDouble(cursor.getString(7).toString());
			point = new GeoPoint((int) (lat *1E6),(int) (lng * 1E6));
			mapView.getController().animateTo(point);
		
			List overlays = mapView.getOverlays();
	
			// first remove old overlay
			if (overlays.size() > 0) {
				for (Iterator iterator = overlays.iterator(); iterator.hasNext();) {
					iterator.next();
					iterator.remove();
				}
			}
	
			Drawable icon = getResources().getDrawable(R.drawable.marker);
			icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon
					.getIntrinsicHeight());
	
			// create my overlay and show it
			MyItemizedOverlay overlay = new MyItemizedOverlay(icon, this);
	
				icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon
						.getIntrinsicHeight());
				overlay = new MyItemizedOverlay(icon, this);
				//item = new OverlayItem(geopoint, list_lokasi.get(i).lokname, "Lat:"
				//		+ list_lokasi.get(i).lat + "\nLng:"
				//		+ list_lokasi.get(i).lng + "\n Jarak:" + String.valueOf(distance) +"m");
				OverlayItem item = new OverlayItem(point, cursor.getString(1).toString(), "");
				overlay.addItem(item);
				mapView.getOverlays().add(overlay);
			mapView.postInvalidate();
		
		}
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	//fungsi untuk mengurusi titik2 objek mau bersifat bagaimana misalnya ketika diklik
	public class MyItemizedOverlay extends ItemizedOverlay {

		private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
		private Drawable marker;
		private Context mContext;

		public MyItemizedOverlay(Drawable defaultMarker) {
			super(defaultMarker);
			// items = new ArrayList();
			marker = defaultMarker;
		}

		public MyItemizedOverlay(Drawable defaultMarker, Context context) {
			super(boundCenterBottom(defaultMarker));
			mContext = context;
		}

		@Override
		protected OverlayItem createItem(int index) {
			return (OverlayItem) items.get(index);
		}

		@Override
		public int size() {
			return items.size();

		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * com.google.android.maps.ItemizedOverlay#draw(android.graphics.Canvas,
		 * com.google.android.maps.MapView, boolean)
		 */
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			// boundCenterBottom(marker);

		}

		public void addItem(OverlayItem item) {
			items.add(item);
			populate();
		}
//ketika diklik ada 3 pilihan 
		@Override
		protected boolean onTap(int index) {
			final OverlayItem item = items.get(0);
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setTitle(item.getTitle());
			dialog.setMessage(item.getSnippet());
			dialog.setPositiveButton("Arahkan", new OnClickListener() {
				//arahkan atau direction 
				String loc1="isinya harysnya lokasi GPS";
				public void onClick(DialogInterface arg0, int arg1) {
					StringBuilder urlString = new StringBuilder(); 	  					
					String daddr = (item.getPoint().getLatitudeE6()/1E6)+","+(item.getPoint().getLongitudeE6()/1E6); 
					urlString.append("http://maps.google.com/maps?f=d&hl=en"); 
					urlString.append("&saddr="+loc1); 
					urlString.append("&daddr="+daddr);
					Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString.toString()));
					mContext.startActivity(i);
				}
			});
			//detail objek
			/*dialog.setNeutralButton("Detail", new OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					Intent i = null;
					i = new Intent(PetaActivity.this, ViewDataActivity.class);
			    	i.putExtra("nama", item.getTitle());
					
					startActivity(i);
				}
			});*/
			//close dialog
			dialog.setNegativeButton("Close", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
				}
			});
			dialog.show();
			return true;
		}
	}
}
