package com.jcertif.mdomotique.ihm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.ihm.adabter.PageIndicator;
import com.jcertif.mdomotique.ihm.adabter.RoomsAdapter;
import com.jcertif.mdomotique.ihm.adabter.TitlePageIndicator;
import com.jcertif.mdomotique.services.MDomotiqueManager;

public class ListRooms extends FragmentActivity{
	
	private RoomsAdapter mAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;
	private MDomotiqueManager mDomotiqueManager;
	private LinearLayout menu, content, loading, action_bar;
	private TextView user_name, managementRooms, managementUsers, logout;
	private ImageView settings;
	private boolean subMenuVisble, isSelected;
	private int btnSelected;
	private Handler mHandler;
	private Timer titleTimer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_rooms);
        
        mDomotiqueManager = MDomotiqueManager.getInstance();
        
        titleTimer = new Timer();
		mHandler = new Handler();

        menu 		= (LinearLayout) findViewById(R.id.menu);
        content 	= (LinearLayout) findViewById(R.id.content);
        loading 	= (LinearLayout) findViewById(R.id.loading);
        action_bar  = (LinearLayout) findViewById(R.id.action_bar);
        
        user_name 		= (TextView) findViewById(R.id.user_name);
        managementRooms = (TextView) findViewById(R.id.managementRooms);
        managementUsers = (TextView) findViewById(R.id.managementUsers);
        logout 			= (TextView) findViewById(R.id.logout);
        
        settings 	= (ImageView) findViewById(R.id.settings);

        mIndicator 	= (TitlePageIndicator)findViewById(R.id.indicator);
        
        mPager 		= (ViewPager)findViewById(R.id.pager);
        
        user_name.setText(mDomotiqueManager.getUserConnected().getFirstname()+" "+mDomotiqueManager.getUserConnected().getName());
        
        subMenuVisble = false;
        isSelected = false;

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!subMenuVisble){
            		subMenuVisble = true;
            		setSubMenuVisible();
            	}else{
            		subMenuVisble = false;
            		setSubMenuInvisible();
            	}           	
            }
        });
        
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected)
            		enableLogout();            	
            }
        });
        
        managementUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected)
            		enableManagementUsers();
            }
        });
        
        managementRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected)
            		enableManagementRooms();
            }
        });
        
        new Thread(){
			public void run(){
				
				while(!mDomotiqueManager.isParsingRoomsFinish()){}				
				while(!mDomotiqueManager.isParsingRoomCategoryFinish()){}
				
    	        ListRooms.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						mAdapter = new RoomsAdapter(getSupportFragmentManager());
 		    	        mPager.setAdapter(mAdapter);
 		    	        mIndicator.setViewPager(mPager);
 		    	        
 		    	        loading.setVisibility(View.GONE);
 		    	        content.setVisibility(View.VISIBLE);
 					}
    			});
			}
		}.start();
		
		Animation animation = new TranslateAnimation(0, 0, -300, 0);
    	animation.setDuration(1000);
    	animation.setFillAfter(true);
    	action_bar.startAnimation(animation);
    	action_bar.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(
        getIntent().getIntExtra("anim id in", R.anim.right_in),
        getIntent().getIntExtra("anim id out", R.anim.right_out));
    }
    
    private void setSubMenuVisible(){
    	
    	Animation animation = new TranslateAnimation(0, 0, -500, 0);
    	animation.setDuration(1000);
    	animation.setFillAfter(true);
    	menu.startAnimation(animation);
    	menu.setVisibility(View.VISIBLE);
    	
    	Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    	settings.startAnimation(rotation);

    }
    
    private void setSubMenuInvisible(){
    	Animation animation = new TranslateAnimation(0, 0, 0, -500);
    	animation.setDuration(1000);
    	animation.setFillAfter(true);
    	menu.startAnimation(animation);
    	menu.setVisibility(View.INVISIBLE);
    	
    	Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    	settings.startAnimation(rotation);
    }
    
    private void enableLogout() {
    	isSelected = true;
    	logout.setBackgroundColor(Color.rgb(79, 129, 189));
    	logout.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 1;
		refreshTimer();
	}
    
    private void disableLogout(){
    	setSubMenuInvisible();
    	subMenuVisble = false;
    	isSelected = false;
    	logout.setBackgroundColor(Color.rgb(255, 255, 255));
    	logout.setTextColor(Color.rgb(79, 129, 189));
    	
    	exit();
    }
    
    private void enableManagementUsers() {
    	isSelected = true;
    	managementUsers.setBackgroundColor(Color.rgb(79, 129, 189));
    	managementUsers.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 3;
		refreshTimer();
	}
    
    private void disableManagementUsers(){
 
    	setSubMenuInvisible();
    	
    	new Thread(){
    		public void run(){
    			try{
    				sleep(1000);
    			}catch(Exception e){}
    			
    			ListRooms.this.runOnUiThread(new Runnable() {
 					@Override public void run(){

 				    	subMenuVisble = false;
 				    	isSelected = false;
 				    	managementUsers.setBackgroundColor(Color.rgb(255, 255, 255));
 				    	managementUsers.setTextColor(Color.rgb(79, 129, 189));
 				    	
 				    	Intent intent = new Intent(ListRooms.this, ManagementUsers.class);
 						intent.putExtra("anim id in", R.anim.right_in);
 						intent.putExtra("anim id out", R.anim.right_out);
 				        startActivity(intent);
 				        overridePendingTransition(R.anim.left_in, R.anim.left_out);
 				    	finish();
 						
 					}
    			});
    			
    		}
    	}.start();
    }
    
    private void enableManagementRooms() {
    	isSelected = true;
    	managementRooms.setBackgroundColor(Color.rgb(79, 129, 189));
    	managementRooms.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 4;
		refreshTimer();
	}
    
    private void disableManagementRooms(){
 
    	setSubMenuInvisible();
    	
    	new Thread(){
    		public void run(){
    			try{
    				sleep(1000);
    			}catch(Exception e){}
    			
    			ListRooms.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 				    	
 				    	subMenuVisble = false;
 				    	isSelected = false;
 				    	managementRooms.setBackgroundColor(Color.rgb(255, 255, 255));
 				    	managementRooms.setTextColor(Color.rgb(79, 129, 189));
 				    	
 				    	Intent intent = new Intent(ListRooms.this, ManagementRooms.class);
 						intent.putExtra("anim id in", R.anim.right_in);
 						intent.putExtra("anim id out", R.anim.right_out);
 				        startActivity(intent);
 				        overridePendingTransition(R.anim.left_in, R.anim.left_out);
 				    	finish();
 						
 					}
    			});
    			
    		}
    	}.start();
    	
    }
    
    private void refreshTimer(){
		stopTimer();
		startTitleTimeOut(750);
	}
	
    private void stopTimer(){
		titleTimer.cancel(); 
		titleTimer.purge();
		titleTimer = null;
	}
	
    private void startTitleTimeOut (int timeOut){
		
		titleTimer = new Timer();
		TimerTask titleTimerTask = new TimerTask(){
			public void run(){	
				mHandler.post(mUpdateResults);
			}
		};

		titleTimer.schedule(titleTimerTask, timeOut);
	}
	
    private Runnable mUpdateResults = new Runnable() {
		 public void run() {
			 switch(btnSelected){
			 case 1:
				 disableLogout();
				 break;
			 case 3:
				 disableManagementUsers();
				 break;
			 case 4:
				 disableManagementRooms();
				 break;
			 }
			 
		 }
	};
	
	private void exit(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("D�connexion");
		alertDialog.setMessage("Est ce que vous etes sur ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(ListRooms.this, Authentification.class);
				intent.putExtra("anim id in", R.anim.left_in);
				intent.putExtra("anim id out", R.anim.left_out);
		        startActivity(intent);
		        overridePendingTransition(R.anim.right_in, R.anim.right_out);
		    	finish();
				
				return;
			} }); 
		alertDialog.setButton2("Non", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;
			}});


		alertDialog.show();
	}
}