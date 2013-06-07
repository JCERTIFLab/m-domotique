package com.jcertif.jcertifhome.interfaces.ihm;

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

import com.jcertif.jcertifhome.R;
import com.jcertif.jcertifhome.interfaces.adapter.PageIndicator;
import com.jcertif.jcertifhome.interfaces.adapter.PiecesAdapter;
import com.jcertif.jcertifhome.interfaces.adapter.TitlePageIndicator;
import com.jcertif.jcertifhome.persistance.Piece;
import com.jcertif.jcertifhome.services.JCertifManager;

public class ListPieces extends FragmentActivity{
	
	private PiecesAdapter mAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;
	private JCertifManager jCertifManager;
	private LinearLayout menu;
	private TextView user_name, add_room, update_room, delete_room, logout;
	private ImageView settings;
	private boolean subMenuVisble, isSelected;
	private int btnSelected;
	private Handler mHandler;
	private Timer titleTimer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pieces);
        
        titleTimer = new Timer();
		mHandler = new Handler();

        menu 		= (LinearLayout) findViewById(R.id.menu);
        user_name 	= (TextView) findViewById(R.id.user_name);
        add_room 	= (TextView) findViewById(R.id.add_room);
        update_room = (TextView) findViewById(R.id.update_room);
        delete_room = (TextView) findViewById(R.id.delete_room);
        logout 		= (TextView) findViewById(R.id.logout);
        settings 	= (ImageView) findViewById(R.id.settings);
        
        jCertifManager = JCertifManager.getInstance();
        
        user_name.setText("Firas GABSI");
        
        subMenuVisble = false;
        isSelected = false;
        
        Piece piece1 = new Piece();
        piece1.setId(0);
        piece1.setName("Salon");
        
        Piece piece2 = new Piece();
        piece2.setId(1);
        piece2.setName("Chambre");
        
        Piece piece3 = new Piece();
        piece3.setId(2);
        piece3.setName("Cuisine");
        
        jCertifManager.listPieces.add(piece1);
        jCertifManager.listPieces.add(piece2);
        jCertifManager.listPieces.add(piece3);

        mAdapter = new PiecesAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        
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
        
        delete_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected)
            		enableDeleteRoom();
            }
        });
        
        update_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected)
            		enableUpdateRoom();
            }
        });
        
        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected)
            		enableAddRoom();
            }
        });

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
	
    private void enableDeleteRoom() {
    	isSelected = true;
    	delete_room.setBackgroundColor(Color.rgb(79, 129, 189));
    	delete_room.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 2;
		refreshTimer();
	}
    
    private void disableDeleteRoom(){
 
    	setSubMenuInvisible();
    	subMenuVisble = false;
    	isSelected = false;
    	delete_room.setBackgroundColor(Color.rgb(255, 255, 255));
    	delete_room.setTextColor(Color.rgb(79, 129, 189));
    }
    
    private void enableUpdateRoom() {
    	isSelected = true;
    	update_room.setBackgroundColor(Color.rgb(79, 129, 189));
    	update_room.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 3;
		refreshTimer();
	}
    
    private void disableUpdateRoom(){
 
    	setSubMenuInvisible();
    	subMenuVisble = false;
    	isSelected = false;
    	update_room.setBackgroundColor(Color.rgb(255, 255, 255));
    	update_room.setTextColor(Color.rgb(79, 129, 189));
    }
    
    private void enableAddRoom() {
    	isSelected = true;
    	add_room.setBackgroundColor(Color.rgb(79, 129, 189));
    	add_room.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 4;
		refreshTimer();
	}
    
    private void disableAddRoom(){
 
    	setSubMenuInvisible();
    	subMenuVisble = false;
    	isSelected = false;
    	add_room.setBackgroundColor(Color.rgb(255, 255, 255));
    	add_room.setTextColor(Color.rgb(79, 129, 189));
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
			 case 2:
				 disableDeleteRoom();
				 break;
			 case 3:
				 disableUpdateRoom();
				 break;
			 case 4:
				 disableAddRoom();
				 break;
			 }
			 
		 }
	};
	
	private void exit(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Déconnexion");
		alertDialog.setMessage("Est ce que vous êtes sûr ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(ListPieces.this, Authentification.class);
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