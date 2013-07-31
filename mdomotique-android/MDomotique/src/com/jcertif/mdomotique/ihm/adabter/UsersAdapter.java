package com.jcertif.mdomotique.ihm.adabter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.persistance.User;
import com.jcertif.mdomotique.services.MDomotiqueManager;

public class UsersAdapter extends ArrayAdapter<User>{
	
	private MDomotiqueManager mDomotiqueManager;

	public UsersAdapter(Context context, int textViewResourceId, ArrayList<User> objects){
		super(context, textViewResourceId, objects);
		
		mDomotiqueManager = MDomotiqueManager.getInstance();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent){

		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.single_user, null);

		TextView name = (TextView) convertView.findViewById(R.id.name);
		
		name.setText(mDomotiqueManager.getListUsers().get(position).getFirstname()+" "+mDomotiqueManager.getListUsers().get(position).getName());

		return convertView;
	}
}