package com.jcertif.jcertifhome.interfaces.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcertif.jcertifhome.R;
import com.jcertif.jcertifhome.interfaces.ihm.Home;
import com.jcertif.jcertifhome.services.JCertifManager;

public final class PiecesHandel extends Fragment {
    
	private static final String KEY_CONTENT = "TestFragment:Content";

    public static PiecesHandel newInstance(String content) {
        PiecesHandel fragment = new PiecesHandel();
        
        fragment.mContent = content;

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	LinearLayout mViewGroupe = (LinearLayout) inflater.inflate(R.layout.pieces,null);
    	ImageView img = (ImageView) mViewGroupe.findViewById(R.id.img);
    	
    	if(mContent.equals("Salon"))
    		img.setImageResource(R.drawable.salon);
    	else if(mContent.equals("Cuisine"))
    		img.setImageResource(R.drawable.cuisine);
    	else
    		img.setImageResource(R.drawable.chambre);
    	
    	img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				JCertifManager.getInstance().pieceSelected = mContent;
				Intent intent = new Intent(getActivity(), Home.class);
		        startActivity(intent);
			}
		});

        return mViewGroupe;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
