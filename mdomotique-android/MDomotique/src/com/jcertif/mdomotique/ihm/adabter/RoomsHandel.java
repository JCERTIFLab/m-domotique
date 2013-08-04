package com.jcertif.mdomotique.ihm.adabter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.ihm.ListEquipements;
import com.jcertif.mdomotique.services.MDomotiqueApplication;
import com.jcertif.mdomotique.services.MDomotiqueManager;
import com.jcertif.mdomotique.services.Parametres;

public final class RoomsHandel extends Fragment {
    
	private static final String KEY_CONTENT = "TestFragment:Content";
	private MDomotiqueApplication mDomotiqueApplication;
	private MDomotiqueManager mDomotiqueManager;

    public static RoomsHandel newInstance(String content) {
    	RoomsHandel fragment = new RoomsHandel();
        
        fragment.mContent = content;

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDomotiqueApplication =  (MDomotiqueApplication) getActivity().getApplicationContext();
        mDomotiqueManager =  MDomotiqueManager.getInstance();
        
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	LinearLayout mViewGroupe = (LinearLayout) inflater.inflate(R.layout.room,null);
    	
    	final ImageView img 	= (ImageView) mViewGroupe.findViewById(R.id.imgRoom);
    	final ProgressBar loading = (ProgressBar) mViewGroupe.findViewById(R.id.loading);
    	
    	new AsyncTask<String, Long, Bitmap>() {
			protected void onPostExecute(final Bitmap result) {
				getActivity().runOnUiThread(new Runnable() {
 					@Override public void run(){
 						
 						if(result != null)
 							img.setImageBitmap(result);
 						
 						img.setVisibility(View.VISIBLE);
 						loading.setVisibility(View.GONE);
 					}
				});
						
				this.cancel(true);
 					
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				Log.i("test","URL : "+Parametres.getImgURL(mDomotiqueManager.getListRooms().get(Integer.parseInt(mContent)).getRoomCategory().getImg()));
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListRooms().get(Integer.parseInt(mContent)).getRoomCategory().getImg()));    
			}
		}.execute("");
    	
    	img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDomotiqueManager.setRoomSelected(mDomotiqueManager.getListRooms().get(Integer.parseInt(mContent)));
				Intent intent = new Intent(getActivity(), ListEquipements.class);
				intent.putExtra("anim id in", R.anim.up_in);
				intent.putExtra("anim id out", R.anim.up_out);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.down_in, R.anim.down_out);
                getActivity().finish();
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
