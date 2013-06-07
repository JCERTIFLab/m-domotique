package com.jcertif.jcertifhome.interfaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jcertif.jcertifhome.R;

public class EquipementAdapter extends BaseAdapter {
	private Context context;
	private final String[] mobileValues;
 
	public EquipementAdapter(Context context, String[] mobileValues) {
		this.context = context;
		this.mobileValues = mobileValues;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView = inflater.inflate(R.layout.single_equipement, null);

		TextView textView = (TextView) gridView.findViewById(R.id.title);
		textView.setText(mobileValues[position]);
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return mobileValues.length;
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
