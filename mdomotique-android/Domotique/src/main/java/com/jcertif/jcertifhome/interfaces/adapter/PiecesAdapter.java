package com.jcertif.jcertifhome.interfaces.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jcertif.jcertifhome.services.JCertifManager;

public class PiecesAdapter extends FragmentPagerAdapter {
	
	private JCertifManager jCertifManager;
	private int Size;

    public PiecesAdapter(FragmentManager fm) {
        super(fm);
        jCertifManager = JCertifManager.getInstance();
        Size = jCertifManager.listPieces.size();
    }

    @Override
    public Fragment getItem(int position) {
        return PiecesHandel.newInstance(jCertifManager.listPieces.get(position % Size).getName());
    }

    @Override
    public int getCount() {
        return jCertifManager.listPieces.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return jCertifManager.listPieces.get(position % Size).getName();
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
        	Size = count;
            notifyDataSetChanged();
        }
    }
}