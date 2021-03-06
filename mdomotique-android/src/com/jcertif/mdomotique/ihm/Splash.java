package com.jcertif.mdomotique.ihm;

import java.net.InetAddress;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.com.parseurs.EquipementCategoriesParseur;
import com.jcertif.mdomotique.com.parseurs.RoomCategoriesParseur;
import com.jcertif.mdomotique.com.parseurs.RoomsParseur;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.services.MDomotiqueApplication;
import com.jcertif.mdomotique.services.MDomotiqueManager;
import com.jcertif.mdomotique.services.OutilsInternet;
import com.jcertif.mdomotique.services.Parametres;

public class Splash extends Activity {

    private MDomotiqueManager mDomotiqueManager;
    private MDomotiqueApplication mDomotiqueApplication;
    private int time = 2000;
    private String adr;
    private EditText adresse;
    private Button add;
    private ImageView refresh;
    private LinearLayout loading;
    
    public final String PREFS_NAME = "serveur";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        refresh = (ImageView) findViewById(R.id.refresh);
        loading = (LinearLayout) findViewById(R.id.loading);

        mDomotiqueManager = MDomotiqueManager.getInstance();
        mDomotiqueApplication =  (MDomotiqueApplication) getApplicationContext();
		
        if (mDomotiqueApplication.mMemoryCache == null)
			mDomotiqueApplication.mMemoryCache = new LruCache<String, Bitmap>(mDomotiqueApplication.cacheSize);

		mDomotiqueApplication.setmMemoryCache(mDomotiqueApplication.mMemoryCache);
        
        if(OutilsInternet.isNetworkAvailable(Splash.this)){
        	new Thread(){
        		public void run(){
        			try{
        				sleep(1000);
        			}catch(Exception e){}

                	loading();
        		}
        	}.start();
        }else{
        	loading.setVisibility(View.VISIBLE);
        	refresh.setVisibility(View.VISIBLE);
        }
        
        refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(OutilsInternet.isNetworkAvailable(Splash.this)){
					time = 1000;
					loading.setVisibility(View.GONE);
		        	refresh.setVisibility(View.GONE);
					loading();
				}
			}
		});
      
    }
    
    private void parsingFlux() {
    	new Thread(){
			public void run(){
				mDomotiqueManager.setListRooms(new RoomsParseur().getRooms());
				mDomotiqueManager.setParsingRoomsFinish(true);
			}
		}.start();
		
		new Thread(){
			public void run(){
				mDomotiqueManager.setListRoomsCategories(new RoomCategoriesParseur().getRoomCategories());
				if(mDomotiqueManager.getListRoomsCategories().isEmpty()){
					mDomotiqueManager.setListInitialRoomsCategories();
					RoomCategoriesParseur roomCategoriesParseur = new RoomCategoriesParseur();
					for(RoomCategory roomCategory : mDomotiqueManager.getListInitialRoomsCategories())
						roomCategoriesParseur.addRoomCategory(roomCategory);
				}
				mDomotiqueManager.setParsingRoomCategoryFinish(true);

			}
		}.start();
		
		new Thread(){
			public void run(){
				mDomotiqueManager.setListEquipementCategories(new EquipementCategoriesParseur().getEquipementCategories());
				if(mDomotiqueManager.getListEquipementCategories().isEmpty()){
					mDomotiqueManager.setListInitialEquipementCategories();
					EquipementCategoriesParseur equipementCategoriesParseur = new EquipementCategoriesParseur();
					for(EquipementCategory equipementCategory : mDomotiqueManager.getListInitialEquipementCategories())
						equipementCategoriesParseur.addEquipementCategory(equipementCategory);
				}
				mDomotiqueManager.setParsingEquipementCategoryFinish(true);
			}
		}.start();
	}

	public void loading(){
    	
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    String adr = settings.getString("adresse", "");
		
    	if(adr.length()>0){
    		String adresse = adr.substring(7, adr.length()-5);
    		if(testAdresse(adresse)){
	    		Parametres.nomDomaine = adr;  
	    		Parametres.setUrls();
				traitement();
    		}else{
    			Splash.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 		        		ShowDialog();
 					}
    			});
    		}
    	}else{
			Splash.this.runOnUiThread(new Runnable() {
					@Override public void run(){
		        		ShowDialog();
					}
			});
		}
    	
    }
	
	public void ShowDialog(){
		final Dialog dialog = new Dialog(Splash.this);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_add_adresse);
		dialog.setTitle("Parmetre du serveur");
		adresse = (EditText) dialog.findViewById(R.id.adresse);
		 
		add  	= (Button) dialog.findViewById(R.id.add);  
		
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
			
				if(adresse.getText().toString().length()>0){		
					adr = adresse.getText().toString();
					if(testAdresse(adr)){
						dialog.dismiss();
						AlertDialog alertDialog = new AlertDialog.Builder(Splash.this).create();
						alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
						alertDialog.setTitle("Le serveur est : "+adr);
						alertDialog.setMessage(getResources().getString(R.string.confirmation));
						alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								time = 2000;
								Parametres.nomDomaine = "http://"+adr+":8181";
					    		Parametres.setUrls();
					    		saveData();
								traitement();
								return;
							} 
						}); 
						alertDialog.setButton2("Non", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								return;
							}
						});
	
						alertDialog.show();
					}else{
						showMessage("Veuillez saisir une adresse valide");
						adresse.setText("");
					}
				}else{
					showMessage("Veuillez saisir l'adresse du serveur");
				}
				
			}
		});

		dialog.show();
	}
	
	private void saveData(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("adresse", Parametres.nomDomaine);
	    editor.commit();
	}
	
	private void traitement(){
		parsingFlux();
    	
    	new Thread(){
        	@Override
        	public void run(){
        		try{
        			sleep(time);
        		}catch(Exception e){}
        		
        		Intent intent = new Intent(Splash.this, Authentification.class);
				intent.putExtra("anim id in", R.anim.right_in);
				intent.putExtra("anim id out", R.anim.right_out);
		        startActivity(intent);
		        overridePendingTransition(R.anim.left_in, R.anim.left_out);
		    	finish();
        		
        	}
        }.start();
	}
	
	private boolean testAdresse(String hostName){
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(hostName);
			return inetAddress.isReachable(5000);
		} catch (Exception e) {
			return false;
		}	 
	}
	
	private void showMessage(String msg) {
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1000){
			finish();
		}
	}
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			exitApp();
			return true;
		}
		return false;
	}

	private void exitApp(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		alertDialog.setTitle("Quitter");
		alertDialog.setMessage(getResources().getString(R.string.confirmation));
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
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