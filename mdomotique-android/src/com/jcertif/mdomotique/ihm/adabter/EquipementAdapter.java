package com.jcertif.mdomotique.ihm.adabter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.services.MDomotiqueApplication;
import com.jcertif.mdomotique.services.MDomotiqueManager;
import com.jcertif.mdomotique.services.Parametres;

public class EquipementAdapter extends BaseAdapter {
	
	private Context context;
	private MDomotiqueManager mDomotiqueManager;
	private MDomotiqueApplication mDomotiqueApplication;
 
	public EquipementAdapter(Context context) {
		this.context = context;
		this.mDomotiqueManager = MDomotiqueManager.getInstance();
		this.mDomotiqueApplication =  (MDomotiqueApplication) context.getApplicationContext();
	}
 
	public View getView(final int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView = inflater.inflate(R.layout.single_equipement, parent, false);
		
		final ProgressBar loading = (ProgressBar) gridView.findViewById(R.id.loading);
		final ImageView img = (ImageView) gridView.findViewById(R.id.img);

		TextView textView = (TextView) gridView.findViewById(R.id.title);
		textView.setText(mDomotiqueManager.getListEquipements().get(position).getName());
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				if (result != null) {
//					BitmapDrawable background = new BitmapDrawable(result);
					img.setImageBitmap(result);
				} 

				loading.setVisibility(View.GONE);
				img.setVisibility(View.VISIBLE);
				result = null;
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListEquipements().get(position).getEquipementCategory().getImg()));  
			}
		}.execute("");
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return mDomotiqueManager.getListEquipements().size();
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}
