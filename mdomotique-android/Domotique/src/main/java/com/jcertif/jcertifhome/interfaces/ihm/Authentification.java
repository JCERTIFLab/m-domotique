package com.jcertif.jcertifhome.interfaces.ihm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcertif.jcertifhome.R;
import com.jcertif.jcertifhome.services.JCertifApplication;

public class Authentification extends Activity{
	
	private LinearLayout connecter, quitter;
	private boolean isSelected;
	private Handler mHandler;
	private Timer titleTimer;
	private int btnSelected;
	private JCertifApplication jCertifApplication;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentification);
		
		titleTimer = new Timer();
		mHandler = new Handler();
		
        jCertifApplication =  (JCertifApplication) getApplicationContext();
		
		connecter = (LinearLayout) findViewById(R.id.connecter);
		quitter   = (LinearLayout) findViewById(R.id.quitter);
		
		isSelected = false;
		
		if (jCertifApplication.mMemoryCache == null)
			jCertifApplication.mMemoryCache = new LruCache<String, Bitmap>(jCertifApplication.cacheSize);

		jCertifApplication.setmMemoryCache(jCertifApplication.mMemoryCache);
		
		connecter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					enableConnecter();
				}
			}
		});
		
		quitter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					enableQuitter();
				}
			}
		});
		
	}
	
	private void enableQuitter(){
		quitter.setBackgroundResource(R.drawable.btn_on);
		btnSelected = 1;
		refreshTimer();
	}
	
	private void enableConnecter(){
		connecter.setBackgroundResource(R.drawable.btn_on);
		btnSelected = 2;
		refreshTimer();
	}
	
	void refreshTimer(){
		stopTimer();
		startTitleTimeOut(750);
	}
	
	void stopTimer(){
		titleTimer.cancel(); 
		titleTimer.purge();
		titleTimer = null;
	}
	
	void startTitleTimeOut (int timeOut){
		
		titleTimer = new Timer();
		TimerTask titleTimerTask = new TimerTask(){
			public void run(){	
				mHandler.post(mUpdateResults);
			}
		};

		titleTimer.schedule(titleTimerTask, timeOut);
	}
	
	final Runnable mUpdateResults = new Runnable() {
		 public void run() {
			 switch(btnSelected){
			 case 1:
				 desableInscription();
				 break;
			 case 2:
				 desableConnecter();
				 break;
			 }
			 
		 }
	};
	
	private void desableConnecter(){
		showToast("Bienvenu Firas GABSI");
		connecter.setBackgroundResource(R.drawable.btn_off);
		Intent intent = new Intent(Authentification.this, ListPieces.class);
		intent.putExtra("anim id in", R.anim.right_in);
		intent.putExtra("anim id out", R.anim.right_out);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    	finish();
	}
	
	private void showToast(String msg) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	 
	private void desableInscription(){
		quitter.setBackgroundResource(R.drawable.btn_off);
		System.exit(0);
	}

}
