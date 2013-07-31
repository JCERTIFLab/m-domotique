package com.jcertif.mdomotique.ihm.adabter;

import com.jcertif.mdomotique.services.MDomotiqueManager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RoomsAdapter extends FragmentPagerAdapter {
	
	private MDomotiqueManager mDomotiqueManager;
	private int Size;

    public RoomsAdapter(FragmentManager fm) {
        super(fm);
        mDomotiqueManager = MDomotiqueManager.getInstance();
        Size = mDomotiqueManager.getListRooms().size();
    }

    @Override
    public Fragment getItem(int position) {
        return RoomsHandel.newInstance((position % Size)+"");
    }

    @Override
    public int getCount() {
        return mDomotiqueManager.getListRooms().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mDomotiqueManager.getListRooms().get(position % Size).getName();
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
        	Size = count;
            notifyDataSetChanged();
        }
    }
    
}