package com.jcertif.jcertifhome.interfaces.services;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.jcertif.jcertifhome.R;
import com.jcertif.jcertifhome.interfaces.ihm.Home;
import com.jcertif.jcertifhome.interfaces.ihm.Pieces;

public class MenuFragment extends ListFragment {

	private Fragment newContent;
	public String[] menuPrincipale = { "Salon", "Cuisine", "Chambre"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.row, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new IconicAdapter());
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		newContent = null;
		switch (position) {
		case 0:
			newContent = new Pieces();
			break;
		case 1:
			newContent = new Pieces();
			break;
		}		
		
		if (newContent != null)
			switchFragment(newContent);
	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof Home) {
			Home fca = (Home) getActivity();
			fca.getSupportActionBar().removeAllTabs();
			fca.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			fca.switchContent(fragment);
		}
	}
	
	@SuppressWarnings("rawtypes")
	class IconicAdapter extends ArrayAdapter {
		@SuppressWarnings("unchecked")
		IconicAdapter() {
			super(getActivity(), R.layout.single_item, menuPrincipale);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			View row = convertView;

			if (row==null) {                          
				LayoutInflater inflater=getActivity().getLayoutInflater();
				row=inflater.inflate(R.layout.single_item, parent, false);
			}

			TextView titre = (TextView) row.findViewById(R.id.title);
			titre.setText(menuPrincipale[position]);
			return (row);
		}
	}

}
