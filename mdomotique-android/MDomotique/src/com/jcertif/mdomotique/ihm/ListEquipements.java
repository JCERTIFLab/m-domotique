package com.jcertif.mdomotique.ihm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.com.parseurs.EquipementsParseur;
import com.jcertif.mdomotique.ihm.adabter.EquipementAdapter;
import com.jcertif.mdomotique.persistance.Equipement;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.services.MDomotiqueApplication;
import com.jcertif.mdomotique.services.MDomotiqueManager;
import com.jcertif.mdomotique.services.Parametres;

public class ListEquipements extends Activity{

	private ImageView img, img_equipement, close;
	private Spinner typeEquipement;
	private ToggleButton etat;
	private EditText name, description, num_pin;
	private ProgressBar loading, loading_img; 
	private GridView list_equipement;
	private boolean isSelected, etatEquipement, showForm;
	private LinearLayout interpteur;
	private Button on, off, action;
	private LinearLayout edit, del, exit, controle, back, add, forum;
	private TextView controle_txt , edit_txt, del_txt, exit_txt, room_name, name_equipement;
	private ProgressDialog progressDialog;
	private Equipement equipementSelected;
	private MDomotiqueManager mDomotiqueManager;
	private MDomotiqueApplication mDomotiqueApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_equipements);
		
		mDomotiqueManager = MDomotiqueManager.getInstance();
		mDomotiqueApplication =  (MDomotiqueApplication) getApplicationContext();
		
		room_name 	= (TextView) findViewById(R.id.room_name);
		name_equipement = (TextView) findViewById(R.id.name_equipement);
		
		etat 		= (ToggleButton) findViewById(R.id.etat);
		
		typeEquipement = (Spinner) findViewById(R.id.typeEquipement);
		
		interpteur = (LinearLayout) findViewById(R.id.interpteur);
		
		img 		= (ImageView) findViewById(R.id.img);
		img_equipement	= (ImageView) findViewById(R.id.img_equipement);
		close		= (ImageView) findViewById(R.id.close);
		
		name 		= (EditText) findViewById(R.id.name);
		description = (EditText) findViewById(R.id.description);
		num_pin 	= (EditText) findViewById(R.id.num_pin);
		
		on 		= (Button) findViewById(R.id.on);
		off 	= (Button) findViewById(R.id.off);
		action 	= (Button) findViewById(R.id.action);

		back 		= (LinearLayout) findViewById(R.id.back);
		add 		= (LinearLayout) findViewById(R.id.settings);
		forum 		= (LinearLayout) findViewById(R.id.forum);
		
		loading 	= (ProgressBar) findViewById(R.id.loading);
		loading_img = (ProgressBar) findViewById(R.id.loading_img);
		
		list_equipement 	= (GridView) findViewById(R.id.list_equipement);

		isSelected = false;	
		showForm = false;
		
		room_name.setText(mDomotiqueManager.getRoomSelected().getName());
		
		back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		isSelected = true;
            		if(showForm){
            			returnInterface();
            			showForm = false;
            			equipementSelected = null;
            			add.setVisibility(View.VISIBLE);
            		}else{
            			Intent intent = new Intent(ListEquipements.this, ListRooms.class);
    					intent.putExtra("anim id in", R.anim.down_in);
    					intent.putExtra("anim id out", R.anim.down_out);
    	                startActivity(intent);
    	                overridePendingTransition(R.anim.up_in, R.anim.up_out);
    	                finish();
            		}
            		
            		isSelected = false;
            	}
            }
        });
		
		action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		isSelected = true;
            		managementEquipement();
            	}
            }
        });
		
		close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	Animation animation = new TranslateAnimation(0, 0, 0, 500);
            	animation.setDuration(1000);
            	animation.setFillAfter(true);
            	interpteur.startAnimation(animation);
            	interpteur.setVisibility(View.GONE);
            }
        });
		
		on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		isSelected = true;
            		on();
            	}
            }
        });
		
		off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		isSelected = true;
            		off();
            	}
            }
        });
		
		add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		isSelected = true;
            		showAddForm();
            		isSelected = false;
            	}
            }
        });
		 
		list_equipement.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				equipementSelected = mDomotiqueManager.getListEquipements().get(position);
				Log.i("Test", "equipement : "+equipementSelected.getName());
				equipementSelected.setRoom(mDomotiqueManager.getRoomSelected());
				ShowDialog();
			}
		});
		
		etat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked)
			    	etatEquipement = true;
			    else
			    	etatEquipement = false;
			}
		}); 
		
		new Thread(){
			public void run(){
				
				ListEquipements.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						
 						mDomotiqueManager.setListEquipements(new ArrayList<Equipement>());
 						mDomotiqueManager.setListEquipements(new EquipementsParseur().getAllEquipements(mDomotiqueManager.getRoomSelected().getId()));
 						
 						list_equipement.setAdapter(new EquipementAdapter(getApplicationContext()));
 						
 						loading.setVisibility(View.GONE);
 						list_equipement.setVisibility(View.VISIBLE);
 					}
				});
			}
		}.start();
		
		setSpinner();
		
	 }
	
	public void returnInterface(){
	
		Animation animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_right);
		animation.reset();
		list_equipement.clearAnimation();
		list_equipement.startAnimation(animation);
		list_equipement.setVisibility(View.VISIBLE);
		
		animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_left);
		animation.reset();           	
		forum.clearAnimation();
		forum.startAnimation(animation);
		forum.setVisibility(View.GONE);
		
	}
	
	private void managementEquipement(){
		if(equipementSelected==null){
			new Thread(){
				public void run(){
					
					Equipement equipement = new Equipement();
					equipement.setName(name.getText().toString());
					equipement.setDescription(description.getText().toString());
					equipement.setPin(Integer.parseInt(num_pin.getText().toString()));
					equipement.setState(etatEquipement);
					equipement.setRoom_id(mDomotiqueManager.getRoomSelected().getId());
					equipement.setEquipement_type_id(mDomotiqueManager.getListEquipementCategories().get(typeEquipement.getSelectedItemPosition()).getId());
					equipement.setEquipementCategory(mDomotiqueManager.getListEquipementCategories().get(typeEquipement.getSelectedItemPosition()));
					equipement.setRoom(mDomotiqueManager.getRoomSelected());
					
					if(new EquipementsParseur().addEquipement(equipement)){						
						ListEquipements.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
								showMessage(getResources().getString(R.string.equipement_added));
		 					}
						});
						
//						mDomotiqueManager.getListEquipements().add(equipement);
						updateEquipementList();
						
					}else{
						ListEquipements.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
								showMessage(getResources().getString(R.string.equipement_not_add));
		 					}
						});
					}
				}
			}.start();
		}else{
	
			new Thread(){
				public void run(){

					Log.i("Test", "equipement 2 : "+equipementSelected.getName());
					
					equipementSelected.setName(name.getText().toString());
					equipementSelected.setDescription(description.getText().toString());
					equipementSelected.setPin(Integer.parseInt(num_pin.getText().toString()));
					equipementSelected.setState(etatEquipement);
					equipementSelected.setEquipement_type_id(mDomotiqueManager.getListEquipementCategories().get(typeEquipement.getSelectedItemPosition()).getId());
					
					if(new EquipementsParseur().updateEquipement(equipementSelected)){						
						ListEquipements.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						showMessage(getResources().getString(R.string.equipement_update));
		 					}
						});
						
//						mDomotiqueManager.getListEquipements().add(positionEquipement, equipementSelected);
						updateEquipementList();
						
					}else{
						ListEquipements.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						showMessage(getResources().getString(R.string.equipement_not_update));
		 					}
						});
					}
				}
			}.start();
		}
		
		Animation animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_right);
		animation.reset();
		loading.clearAnimation();
		loading.startAnimation(animation);
		loading.setVisibility(View.VISIBLE);
		
		animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_left);
		animation.reset();           	
		forum.clearAnimation();
		forum.startAnimation(animation);
		forum.setVisibility(View.GONE);
		
		equipementSelected = null;
		isSelected = false;
		showForm = false;
	}
	
	private void setSpinner(){
		List<String> list = new ArrayList<String>();
		
		for(EquipementCategory equipementCategory : mDomotiqueManager.getListEquipementCategories())
			list.add(equipementCategory.getName());
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeEquipement.setAdapter(dataAdapter);
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				if (result != null) {
					BitmapDrawable background = new BitmapDrawable(result);
					img.setBackgroundDrawable(background);
					loading_img.setVisibility(View.INVISIBLE);
				} 

				result = null;
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListEquipementCategories().get(0).getImg()));  
			}
		}.execute("");
		
		typeEquipement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id) {
		        loading_img.setVisibility(View.VISIBLE);
		        
		        new AsyncTask<String, Long, Bitmap>() {

					protected void onPostExecute(Bitmap result) {
						if (result != null) {
							BitmapDrawable background = new BitmapDrawable(result);
							img.setBackgroundDrawable(background);
							loading_img.setVisibility(View.INVISIBLE);
						} 

						result = null;
						this.cancel(true);
					}

					protected Bitmap doInBackground(String... params) {
						return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListEquipementCategories().get(pos).getImg()));  
					}
				}.execute("");
		        
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
	}
	
	public void ShowDialog(){
		final Dialog dialog = new Dialog(ListEquipements.this);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_contextuel_equipement);
		dialog.setTitle(equipementSelected.getName());

		edit = (LinearLayout) dialog.findViewById(R.id.editEquipement); 
		del  = (LinearLayout) dialog.findViewById(R.id.del); 
		exit = (LinearLayout) dialog.findViewById(R.id.exit);
		controle = (LinearLayout) dialog.findViewById(R.id.controle);
		
		controle_txt = (TextView) dialog.findViewById(R.id.controle_txt); 
		edit_txt		= (TextView) dialog.findViewById(R.id.edit_txt); 
		del_txt  	= (TextView) dialog.findViewById(R.id.del_txt); 
		exit_txt 	= (TextView) dialog.findViewById(R.id.exit_txt);
		
		controle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				controle.setBackgroundColor(Color.rgb(79, 129, 189));
				controle_txt.setTextColor(Color.rgb(255, 255, 255));
				showControleDialog();
				dialog.dismiss();
			}
		});
		
		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit.setBackgroundColor(Color.rgb(79, 129, 189));
				edit_txt.setTextColor(Color.rgb(255, 255, 255));
				showEditForm();
				dialog.dismiss();
			}
		});
		
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				del.setBackgroundColor(Color.rgb(79, 129, 189));
				del_txt.setTextColor(Color.rgb(255, 255, 255));
				removeEquipement();
				dialog.dismiss();
			}
		});
		
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit.setBackgroundColor(Color.rgb(79, 129, 189));
				exit_txt.setTextColor(Color.rgb(255, 255, 255));
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	
	private void showControleDialog(){
		
		Animation animation = new TranslateAnimation(0, 0, 500, 0);
    	animation.setDuration(1000);
    	animation.setFillAfter(true);
    	interpteur.startAnimation(animation);
    	interpteur.setVisibility(View.VISIBLE);
		
		name_equipement.setText(equipementSelected.getName());
		if(equipementSelected.isState()){
			on.setEnabled(false);
			off.setEnabled(true);
		}else{
			on.setEnabled(true);
			off.setEnabled(false);
		}
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				if (result != null)
					img_equipement.setImageBitmap(result); 

				result = null;
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(equipementSelected.getEquipementCategory().getImg()));  
			}
		}.execute("");
			
	}
	
	private void on(){	
		
		progressDialog=new ProgressDialog(this);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Exï¿½cution ....");
		progressDialog.show();
		
    	new Thread(){
    		public void run(){
    			if(new EquipementsParseur().ExecuteAction(equipementSelected.getPin(), 1)){
    	    		equipementSelected.setState(true);
    	    		new EquipementsParseur().updateEquipement(equipementSelected);
    	    		ListEquipements.this.runOnUiThread(new Runnable() {
	 					@Override public void run(){
	 	    	    		showMessage(equipementSelected.getName()+" active");
	 					}
    	    		});
    	    	}else{
    	    		ListEquipements.this.runOnUiThread(new Runnable() {
	 					@Override public void run(){
	 						showMessage(getResources().getString(R.string.wrong_action));
	 					}
    	    		});
    	    	}
    			
    			ListEquipements.this.runOnUiThread(new Runnable() {
 					@Override public void run(){					
 						progressDialog.dismiss();						
 						Animation animation = new TranslateAnimation(0, 0, 0, 500);
 				    	animation.setDuration(1000);
 				    	animation.setFillAfter(true);
 				    	interpteur.startAnimation(animation);
 				    	interpteur.setVisibility(View.GONE);
 					}
	    		});
    			
    			try{
    				sleep(1000);
    			}catch(Exception e){}
    	    	
    	    	isSelected = false;
    		}
    	}.start();
	}
	
	private void off(){
		
		progressDialog=new ProgressDialog(this);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Execution ....");
		progressDialog.show();

    	new Thread(){
    		public void run(){
    			if(new EquipementsParseur().ExecuteAction(equipementSelected.getPin(), 0)){
    	    		equipementSelected.setState(false);
    	    		new EquipementsParseur().updateEquipement(equipementSelected);
    	    		ListEquipements.this.runOnUiThread(new Runnable() {
	 					@Override public void run(){
	 	    	    		showMessage(equipementSelected.getName()+" desactivï¿½"); 
	 					}
    	    		});
    	    	}else{
    	    		ListEquipements.this.runOnUiThread(new Runnable() {
	 					@Override public void run(){
	 						showMessage(getResources().getString(R.string.wrong_action));
	 					}
    	    		});
    	    	}
    			
    			ListEquipements.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						progressDialog.dismiss();
 						Animation animation = new TranslateAnimation(0, 0, 0, 500);
 		    	    	animation.setDuration(1000);
 		    	    	animation.setFillAfter(true);
 		    	    	interpteur.startAnimation(animation);
 		    	    	interpteur.setVisibility(View.GONE);
 					}
	    		});
    			
    			try{
    				sleep(1000);
    			}catch(Exception e){}
    	    	
    	    	isSelected = false;
    		}
    	}.start();
    	
	}
	
	private void showEditForm(){
		action.setText("Modifier");
		name.setText(equipementSelected.getName());
		description.setText(equipementSelected.getDescription());
		num_pin.setText(equipementSelected.getPin()+"");
		etat.setChecked(equipementSelected.isState());
		
		int pos = 0;
		for(pos=0; pos<mDomotiqueManager.getListRoomsCategories().size(); pos++){
			if(mDomotiqueManager.getListEquipementCategories().get(pos).getId()==equipementSelected.getEquipementCategory().getId())
				break;
		}
		
		typeEquipement.setSelection(pos);
		
		loading_img.setVisibility(View.VISIBLE);
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				if (result != null) {
					BitmapDrawable background = new BitmapDrawable(result);
					img.setBackgroundDrawable(background);
					loading_img.setVisibility(View.INVISIBLE);
				} 
				
				img.setVisibility(View.VISIBLE);
				loading_img.setVisibility(View.GONE);

				result = null;
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(equipementSelected.getEquipementCategory().getImg()));  
			}
		}.execute("");

		Animation animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_right);
		animation.reset();
		forum.clearAnimation();
		forum.startAnimation(animation);
		forum.setVisibility(View.VISIBLE);
		
		animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_left);
		animation.reset();           	
		list_equipement.clearAnimation();
		list_equipement.startAnimation(animation);
		list_equipement.setVisibility(View.GONE);		
		
		showForm = true;
	}
	
	protected void showAddForm() {
		
		action.setText("Ajouter");
		add.setVisibility(View.INVISIBLE);
		name.setText("");
		description.setText("");
		num_pin.setText("");
		etat.setChecked(false);
		typeEquipement.setSelection(0);
		
		loading_img.setVisibility(View.VISIBLE);
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				if (result != null) {
					BitmapDrawable background = new BitmapDrawable(result);
					img.setBackgroundDrawable(background);
				} 

				img.setVisibility(View.VISIBLE);
				loading_img.setVisibility(View.GONE);

				result = null;
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListEquipementCategories().get(0).getImg()));  
			}
		}.execute("");
		
		Animation animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_right);
		animation.reset();
		forum.clearAnimation();
		forum.startAnimation(animation);
		forum.setVisibility(View.VISIBLE);
		
		animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_left);
		animation.reset();           	
		list_equipement.clearAnimation();
		list_equipement.startAnimation(animation);
		list_equipement.setVisibility(View.GONE);
		
		showForm = true;
		
	}
	
	protected void removeEquipement() {
		AlertDialog alertDialog = new AlertDialog.Builder(ListEquipements.this).create();

		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Supprimer");
		alertDialog.setMessage(getResources().getString(R.string.ask_delete_room));
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new Thread(){
					@Override
					public void run(){
						ListEquipements.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						if(new EquipementsParseur().RemoveEquipement(equipementSelected.getId())){
		 							showMessage(getResources().getString(R.string.equipement_deleted));
		 							
		 							forum.setVisibility(View.GONE);
		 							
		 							Animation animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_left);
		 							animation.reset();
		 							list_equipement.clearAnimation();
		 							list_equipement.startAnimation(animation);
		 							list_equipement.setVisibility(View.GONE);
		 							
		 							animation = AnimationUtils.loadAnimation(ListEquipements.this, R.anim.translate_right);
		 							animation.reset();
		 							loading.clearAnimation();
		 							loading.startAnimation(animation);
		 							loading.setVisibility(View.VISIBLE);
		 							
		 							mDomotiqueManager.getListRooms().remove(equipementSelected);
		 							
		 							updateEquipementList();
		 						}else
		 							showMessage(getResources().getString(R.string.equipement_not_deleted));
		 					}
						});
					}
				}.start();
				return;
			} }); 
		alertDialog.setButton2("Non", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;
			}});


		alertDialog.show();
	}
	
	private void showMessage(String msg) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(ListEquipements.this);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
	private void updateEquipementList(){
		
		mDomotiqueManager.setListEquipements(new ArrayList<Equipement>());
		mDomotiqueManager.setListEquipements(new EquipementsParseur().getAllEquipements(mDomotiqueManager.getRoomSelected().getId()));
		
		Log.i("test","Size : "+mDomotiqueManager.getListEquipements().size());
		
		new Thread(){
			public void run(){
				
				try{
					sleep(1000);
				}catch(Exception e){}
				
				ListEquipements.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						
 						list_equipement.setAdapter(new EquipementAdapter(getApplicationContext()));
 						
 						loading.setVisibility(View.GONE);
 						list_equipement.setVisibility(View.VISIBLE);
            			add.setVisibility(View.VISIBLE);
 					}
				});
			}
		}.start();
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
		alertDialog.setMessage("Est ce que vous êtes sur ?");
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
