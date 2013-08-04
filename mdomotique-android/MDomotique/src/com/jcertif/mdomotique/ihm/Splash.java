package com.jcertif.mdomotique.ihm;

import java.net.InetAddress;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.Gravity;
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
import com.jcertif.mdomotique.com.parseurs.UsersParseur;
import com.jcertif.mdomotique.services.MDomotiqueApplication;
import com.jcertif.mdomotique.services.MDomotiqueManager;
import com.jcertif.mdomotique.services.ManagementFiles;
import com.jcertif.mdomotique.services.OutilsInternet;
import com.jcertif.mdomotique.services.Parametres;

public class Splash extends Activity {

    private MDomotiqueManager mDomotiqueManager;
    private MDomotiqueApplication mDomotiqueApplication;
    private int time = 2000;
    private String adr;
    private EditText adresse;
    private Button fermer, add;
    private ImageView refresh;
    private LinearLayout loading;
    
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
        	loading();
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
				mDomotiqueManager.setParsingRoomCategoryFinish(true);

			}
		}.start();
		
		new Thread(){
			public void run(){
				mDomotiqueManager.setListEquipementCategories(new EquipementCategoriesParseur().getEquipementCategories());
				mDomotiqueManager.setParsingEquipementCategoryFinish(true);
			}
		}.start();
		
		new Thread(){
			public void run(){
				mDomotiqueManager.setListUsers(new UsersParseur().getUsers());
				mDomotiqueManager.setParsingUsersFinish(true);
			}
		}.start();
	}

	public void loading(){
    	
		String adr = ManagementFiles.readData("serveur.txt");
		adr = "http://192.168.1.3:8084";
    	if(adr.length()>0){
    		Parametres.nomDomaine = adr;  
    		Parametres.setUrls();
			traitement();
    	}else
    		ShowDialog();
    	
    }
	
	public void ShowDialog(){
		final Dialog dialog = new Dialog(Splash.this);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_add_adresse);
		dialog.setTitle("Parmetre du serveur");
		adresse = (EditText) dialog.findViewById(R.id.adresse);
		 
		add  	= (Button) dialog.findViewById(R.id.add);  
		fermer  = (Button) dialog.findViewById(R.id.fermer); 
		
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
						alertDialog.setMessage("Est ce que vous etes sur ?");
						alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								time = 2000;
								Parametres.nomDomaine = "http://"+adr;
					    		Parametres.setUrls();
								ManagementFiles.writeData(Parametres.nomDomaine, "serveur.txt");
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
		
		fermer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.exit(0);
				dialog.dismiss();
			}
		});

		dialog.show();
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

}