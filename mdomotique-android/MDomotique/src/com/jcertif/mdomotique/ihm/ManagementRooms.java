package com.jcertif.mdomotique.ihm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.com.parseurs.RoomsParseur;
import com.jcertif.mdomotique.ihm.adabter.RoomAdapter;
import com.jcertif.mdomotique.persistance.Room;
import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.services.MDomotiqueApplication;
import com.jcertif.mdomotique.services.MDomotiqueManager;
import com.jcertif.mdomotique.services.Parametres;

public class ManagementRooms extends Activity{
	
	private LinearLayout back, add, add_room, content_img, loading_img;
	private ImageView img;
	private TextView name;
	private Button action;
	private ProgressBar loading;
	private EditText nameRoom;
	private Spinner typeRoom;
	private ListView list_rooms;
	private boolean isSelected, showForm;
	private Room roomSelected;
	private MDomotiqueApplication mDomotiqueApplication;
	private MDomotiqueManager mDomotiqueManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.management_rooms);
		
		mDomotiqueManager = MDomotiqueManager.getInstance();
		mDomotiqueApplication =  (MDomotiqueApplication) getApplicationContext();
		roomSelected = null;
		isSelected = false;
		showForm = false;
	    
		content_img = (LinearLayout) findViewById(R.id.content_img);
		back 	 = (LinearLayout) findViewById(R.id.back);
		add  	 = (LinearLayout) findViewById(R.id.add);
		add_room = (LinearLayout) findViewById(R.id.add_room);
		loading_img = (LinearLayout) findViewById(R.id.loading_img);
		
		name   = (TextView) findViewById(R.id.name);
		action = (Button) findViewById(R.id.action);

		img  	 = (ImageView) findViewById(R.id.img);
		
		loading 	= (ProgressBar) findViewById(R.id.loading);
		
		nameRoom  = (EditText) findViewById(R.id.nameRoom);
		typeRoom  = (Spinner) findViewById(R.id.typeRoom);
		
		list_rooms = (ListView) findViewById(R.id.list_rooms);
		
		name.setText(getResources().getString(R.string.management_rooms));
		
		back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		isSelected = true;
            		if(showForm){

                        Animation animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_right);
                        animation.reset();
                        list_rooms.clearAnimation();
                        list_rooms.startAnimation(animation);
            			list_rooms.setVisibility(View.VISIBLE);

                        animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_left);
                        animation.reset();
                        add_room.clearAnimation();
                        add_room.startAnimation(animation);
            			add_room.setVisibility(View.GONE);
            			
            			showForm = false;
            			roomSelected = null;
            			add.setVisibility(View.VISIBLE);
            		}else{
						Intent intent = new Intent(ManagementRooms.this, ListRooms.class);
						intent.putExtra("anim id in", R.anim.left_in);
						intent.putExtra("anim id out", R.anim.left_out);
		                startActivity(intent);
		                overridePendingTransition(R.anim.right_in, R.anim.right_out);
				    	finish();
            		}
            		
            		isSelected = false;
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
		
		action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		if(nameRoom.getText().toString().length()>0){
            			isSelected = true;
            			managementRoom();
            		}else{
            			showMessage("Veuillez saisir le nom de la piece");
            		}
            	}
            }
        });
		
		typeRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id) {
		    	
				loading_img.setVisibility(View.VISIBLE);
				content_img.setVisibility(View.GONE);
		        
		        new AsyncTask<String, Long, Bitmap>() {

					protected void onPostExecute(Bitmap result) {
						if (result != null) {
							img.setImageBitmap(result);
							loading_img.setVisibility(View.GONE);
							content_img.setVisibility(View.VISIBLE);
						} 

						result = null;
						this.cancel(true);
					}

					protected Bitmap doInBackground(String... params) {
						return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListRoomsCategories().get(pos).getImg()));  
					}
				}.execute("");
		        
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		new Thread(){
			public void run(){
				while(!mDomotiqueManager.isParsingRoomsFinish()){}
				
				ManagementRooms.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						
 						list_rooms.setAdapter(new RoomAdapter(getApplicationContext(), R.layout.single_room, mDomotiqueManager.getListRooms()));
 						
 						loading.setVisibility(View.GONE);
 						list_rooms.setVisibility(View.VISIBLE);
 					}
				});
			}
		}.start();
		
		list_rooms.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	roomSelected = mDomotiqueManager.getListRooms().get(position);
            	ShowDialog();
            }
        });
		
		setSpinner();
		
	 }
	
	private void setSpinner(){
		List<String> list = new ArrayList<String>();
		
		for(RoomCategory roomCategory : mDomotiqueManager.getListRoomsCategories())
			list.add(roomCategory.getName());
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeRoom.setAdapter(dataAdapter);

		loading_img.setVisibility(View.VISIBLE);
		content_img.setVisibility(View.GONE);
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				if (result != null) {
					img.setImageBitmap(result);
					loading_img.setVisibility(View.GONE);
					content_img.setVisibility(View.VISIBLE);
				} 

				result = null;
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListRoomsCategories().get(0).getImg()));  
			}
		}.execute("");
	}
	
	private void managementRoom(){
		
		Animation animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_left);
        animation.reset();
        loading.clearAnimation();
        loading.startAnimation(animation);
        loading.setVisibility(View.VISIBLE);

        animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_left);
        animation.reset();
        add_room.clearAnimation();
        add_room.startAnimation(animation);
        add_room.setVisibility(View.GONE);

		if(roomSelected==null){
			new Thread(){
				public void run(){
					
					try{
						sleep(1000);
					}catch(Exception e){}
		
					Room room = new Room();
					room.setName(nameRoom.getText().toString());
					room.setRoomCategory(mDomotiqueManager.getListRoomsCategories().get(typeRoom.getSelectedItemPosition()));
					
					if(new RoomsParseur().addRoom(room)){						
						ManagementRooms.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
								showMessage(getResources().getString(R.string.room_added));
		 						setContent();
		 						roomSelected = null;
		 					}
						});
					}else{
						ManagementRooms.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
								showMessage(getResources().getString(R.string.room_not_add));
		 					}
						});
					}
				}
			}.start();
		}else{
	
			new Thread(){
				public void run(){
					
					try{
						sleep(1000);
					}catch(Exception e){}
										
					roomSelected.setName(nameRoom.getText().toString());
					roomSelected.setTypeId(mDomotiqueManager.getListRoomsCategories().get(typeRoom.getSelectedItemPosition()).getId()+"");
					roomSelected.setRoomCategory(mDomotiqueManager.getListRoomsCategories().get(typeRoom.getSelectedItemPosition()));
					
					if(new RoomsParseur().updateRoom(roomSelected)){						
						ManagementRooms.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						showMessage(getResources().getString(R.string.room_update));
		 						setContent();
		 						roomSelected = null;
		 					}
						});
						
					}else{
						ManagementRooms.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						showMessage(getResources().getString(R.string.room_not_update));
		 					}
						});
					}
				}
			}.start();
		}

		showForm = false;
		isSelected = false;
	}
	
	public void ShowDialog(){
		final Dialog dialog = new Dialog(ManagementRooms.this);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_contextuel_room);
		dialog.setTitle(roomSelected.getName());

		final LinearLayout edit = (LinearLayout) dialog.findViewById(R.id.edit); 
		final LinearLayout del  = (LinearLayout) dialog.findViewById(R.id.del); 
		final LinearLayout exit = (LinearLayout) dialog.findViewById(R.id.exit);
		
		final TextView edit_txt = (TextView) dialog.findViewById(R.id.edit_txt); 
		final TextView del_txt  = (TextView) dialog.findViewById(R.id.del_txt); 
		final TextView exit_txt = (TextView) dialog.findViewById(R.id.exit_txt);
		
		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit.setBackgroundColor(Color.rgb(79, 129, 189));
				edit_txt.setTextColor(Color.rgb(255, 255, 255));
				showForm();
				dialog.dismiss();
			}
		});
		
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				del.setBackgroundColor(Color.rgb(79, 129, 189));
				del_txt.setTextColor(Color.rgb(255, 255, 255));
				removeRoom();
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
	
	protected void showAddForm() {
		
		add.setVisibility(View.INVISIBLE);
		
		action.setText("Ajouter");
		
		nameRoom.setText("");
		
		typeRoom.setSelection(0);
		
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
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(mDomotiqueManager.getListRoomsCategories().get(0).getImg()));  
			}
		}.execute("");

        Animation animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_right);
        animation.reset();
        add_room.clearAnimation();
        add_room.startAnimation(animation);
		add_room.setVisibility(View.VISIBLE);

        animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_left);
        animation.reset();
        list_rooms.clearAnimation();
        list_rooms.startAnimation(animation);
		list_rooms.setVisibility(View.GONE);
		
		showForm = true;
		
	}
	
	protected void showForm() {
		
		action.setText("Modifier");
		
		nameRoom.setText(roomSelected.getName());
		
		int pos = 0;
		for(pos=0; pos<mDomotiqueManager.getListRoomsCategories().size(); pos++){
			if(mDomotiqueManager.getListRoomsCategories().get(pos).getId()==roomSelected.getRoomCategory().getId())
				break;
		}
		
		typeRoom.setSelection(pos);
		
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
				return mDomotiqueApplication.ImageOperations(Parametres.getImgURL(roomSelected.getRoomCategory().getImg()));  
			}
		}.execute("");

        Animation animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_right);
        animation.reset();
        add_room.clearAnimation();
        add_room.startAnimation(animation);
		add_room.setVisibility(View.VISIBLE);

        animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_left);
        animation.reset();
        list_rooms.clearAnimation();
        list_rooms.startAnimation(animation);
		list_rooms.setVisibility(View.GONE);
		
		showForm = true;
		
	}

	protected void removeRoom() {
		AlertDialog alertDialog = new AlertDialog.Builder(ManagementRooms.this).create();

		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Supprimer");
		alertDialog.setMessage(getResources().getString(R.string.ask_delete_room));
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new Thread(){
					@Override
					public void run(){
						ManagementRooms.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						if(new RoomsParseur().RemoveRoom(roomSelected.getId())){
		 							showMessage(getResources().getString(R.string.room_deleted));

                                    Animation animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_right);
                                    animation.reset();
                                    loading.clearAnimation();
                                    loading.startAnimation(animation);
		 							loading.setVisibility(View.VISIBLE);

                                    animation = AnimationUtils.loadAnimation(ManagementRooms.this, R.anim.translate_left);
                                    animation.reset();
                                    list_rooms.clearAnimation();
                                    list_rooms.startAnimation(animation);
		 							list_rooms.setVisibility(View.GONE);
		 							
		 							mDomotiqueManager.getListRooms().remove(roomSelected);
		 							
		 							setContent();
		 						}else
		 							showMessage(getResources().getString(R.string.room_not_deleted));
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

		Toast toast = new Toast(ManagementRooms.this);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
	private void setContent(){
		
		mDomotiqueManager.setParsingRoomsFinish(false);
		
		new Thread(){
			public void run(){
				mDomotiqueManager.setListRooms(new ArrayList<Room>());
				mDomotiqueManager.setListRooms(new RoomsParseur().getRooms());
				mDomotiqueManager.setParsingRoomsFinish(true);
			}
		}.start();
		
		new Thread(){
			public void run(){
				
				while(!mDomotiqueManager.isParsingRoomsFinish()){}
				
				ManagementRooms.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						
 						list_rooms.setAdapter(new RoomAdapter(getApplicationContext(), R.layout.single_room, mDomotiqueManager.getListRooms()));
 						
 						loading.setVisibility(View.GONE);
 						list_rooms.setVisibility(View.VISIBLE);
 						
            			add.setVisibility(View.VISIBLE);
            			
            			roomSelected = null;
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
