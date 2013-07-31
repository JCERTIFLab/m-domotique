package com.jcertif.mdomotique.ihm.adabter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.persistance.Room;
import com.jcertif.mdomotique.services.MDomotiqueApplication;
import com.jcertif.mdomotique.services.MDomotiqueManager;
import com.jcertif.mdomotique.services.Parametres;

public class RoomAdapter extends ArrayAdapter<Room>{
	
	private MDomotiqueManager mDomotiqueManager;
	private MDomotiqueApplication mDomotiqueApplication;

	public RoomAdapter(Context context, int textViewResourceId, ArrayList<Room> objects){
		super(context, textViewResourceId, objects);
		
		mDomotiqueManager = MDomotiqueManager.getInstance();
		mDomotiqueApplication =  (MDomotiqueApplication) context.getApplicationContext();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent){

		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.single_room, null);

		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView type = (TextView) convertView.findViewById(R.id.type);
		final LinearLayout img = (LinearLayout) convertView.findViewById(R.id.img);
		
		name.setText(mDomotiqueManager.getListRooms().get(position).getName());
		type.setText(mDomotiqueManager.getListRooms().get(position).getRoomCategory().getName());

		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				if (result != null) {
					BitmapDrawable background = new BitmapDrawable(result);
					img.setBackgroundDrawable(background);
				} 

				result = null;
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListRooms().get(position).getRoomCategory().getImg()));  
			}
		}.execute("");

		return convertView;
	}
}