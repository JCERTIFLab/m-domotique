package com.jcertif.mdomotique.ihm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.com.parseurs.UsersParseur;
import com.jcertif.mdomotique.persistance.User;
import com.jcertif.mdomotique.services.MDomotiqueManager;

public class Authentification extends Activity{
	
	private LinearLayout connecter, quitter, loading, content;
	private EditText login, password;
	private boolean isSelected;
	private Handler mHandler;
	private Timer titleTimer;
	private int btnSelected;
	
	private MDomotiqueManager mDomotiqueManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentification);
	
		titleTimer = new Timer();
		mHandler = new Handler();
		
        mDomotiqueManager =  MDomotiqueManager.getInstance();
		
		connecter = (LinearLayout) findViewById(R.id.connecter);
		quitter   = (LinearLayout) findViewById(R.id.quitter);
		loading   = (LinearLayout) findViewById(R.id.loading);
		content   = (LinearLayout) findViewById(R.id.authentification_menu);
		
		login     = (EditText) findViewById(R.id.login);
		password  = (EditText) findViewById(R.id.password);
		
		login.setText("root");
		password.setText("root");
		
		isSelected = false;

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
				 desableLeave();
				 break;
			 case 2:
				 desableConnecter();
				 break;
			 }
			 
		 }
	};
	
	private void desableConnecter(){
		
		connecter.setBackgroundResource(R.drawable.btn_off);
		isSelected = false;
		
		Animation animation = AnimationUtils.loadAnimation(Authentification.this, R.anim.translate_left);
		animation.reset();
		content.clearAnimation();
		content.startAnimation(animation);
		content.setVisibility(View.GONE);
		
		animation = AnimationUtils.loadAnimation(Authentification.this, R.anim.translate_right);
		animation.reset();
		loading.clearAnimation();
		loading.startAnimation(animation);
		loading.setVisibility(View.VISIBLE);
		
		new Thread(){
			public void run(){
				String loginTxt = login.getText().toString();
				String passwordTxt = password.getText().toString();
				
				try{
					sleep(1000);
				}catch(Exception e){}
				
				if(loginTxt.length()==0 || passwordTxt.length()==0){
					Authentification.this.runOnUiThread(new Runnable() {
	 					@Override public void run(){
	 						showToast(getResources().getString(R.string.missing_login_password));
	 					}
        			});
				}else{
					final User user = new UsersParseur().authentification(loginTxt, passwordTxt);
					if(user==null){
						Authentification.this.runOnUiThread(new Runnable() {
    	 					@Override public void run(){
    							showToast(getResources().getString(R.string.wrong_login_password));
    							login.setText("");
    							password.setText("");
    	 					}
            			});
					}else{
						
						Authentification.this.runOnUiThread(new Runnable() {
    	 					@Override public void run(){
    							showToast("Bienvenu "+user.getFirstname()+" "+user.getName());
    	 					}
            			});
						
						mDomotiqueManager.setUserConnected(user);
						connecter.setBackgroundResource(R.drawable.btn_off);
						Intent intent = new Intent(Authentification.this, ListRooms.class);
						intent.putExtra("anim id in", R.anim.right_in);
						intent.putExtra("anim id out", R.anim.right_out);
				        startActivity(intent);
				        overridePendingTransition(R.anim.left_in, R.anim.left_out);
				    	finish();
					}
				}
			}
		}.start();
		
	}
	
	private void showToast(String msg) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	 
	private void desableLeave(){
		quitter.setBackgroundResource(R.drawable.btn_off);
		System.exit(0);
	}
	
	 public static String getContents(String url) {
	        String contents ="";
	 
	  try {
	        URLConnection conn = new URL(url).openConnection();
	 
	        InputStream in = conn.getInputStream();
	        contents = convertStreamToString(in);
	   } catch (MalformedURLException e) {
	        Log.v("test","MALFORMED URL EXCEPTION");
	   } catch (IOException e) {
	        Log.e("test",e.getMessage());
	   }
	 
	  return contents;
	}
	 
	private static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
	 
	      BufferedReader reader = new BufferedReader(new    
	                              InputStreamReader(is, "UTF-8"));
	        StringBuilder sb = new StringBuilder();
	         String line = null;
	         try {
	                while ((line = reader.readLine()) != null) {
	                        sb.append(line + "\n");
	                }
	           } catch (IOException e) {
	                e.printStackTrace();
	           } finally {
	                try {
	                        is.close();
	                } catch (IOException e) {
	                        e.printStackTrace();
	                }
	            }
	        return sb.toString();
	  }

}
