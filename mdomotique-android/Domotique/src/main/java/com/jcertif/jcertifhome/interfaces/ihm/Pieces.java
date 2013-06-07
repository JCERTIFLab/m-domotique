package com.jcertif.jcertifhome.interfaces.ihm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.jcertif.jcertifhome.R;
import com.jcertif.jcertifhome.interfaces.adapter.EquipementAdapter;
import com.jcertif.jcertifhome.services.JCertifManager;

public class Pieces extends SherlockFragment{
	
	private LinearLayout mViewGroupe;
	private GridView gridView;
	static final String[] MOBILE_OS = new String[] { 
		"Lamps 1", "Lamps 2","Port", "TV" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mViewGroupe	= (LinearLayout) inflater.inflate(R.layout.single_piece,null);
		gridView = (GridView) mViewGroupe.findViewById(R.id.gridView1);
		
		View customView = inflater.inflate(R.layout.title_layout, null);
	    
	    TextView titleTV = (TextView) customView.findViewById(R.id.bar_title);
	    titleTV.setText(JCertifManager.getInstance().pieceSelected);
	    titleTV.setTextSize(20);
		 
		gridView.setAdapter(new EquipementAdapter(getActivity(), MOBILE_OS));
 
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				
 
			}
		});

		return mViewGroupe;
	}
	
}
