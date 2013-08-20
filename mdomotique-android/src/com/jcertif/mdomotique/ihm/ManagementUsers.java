package com.jcertif.mdomotique.ihm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jcertif.mdomotique.R;
import com.jcertif.mdomotique.com.parseurs.UsersParseur;
import com.jcertif.mdomotique.ihm.adabter.UsersAdapter;
import com.jcertif.mdomotique.persistance.User;
import com.jcertif.mdomotique.services.MDomotiqueManager;

public class ManagementUsers extends Activity{
	
	private LinearLayout back, add, info_user;
	private TextView name;
	private ProgressBar loading;
	private Button addUser;
	private EditText nameUser, firstUser, loginUser, passwordUser;
	private ListView list_users;
	private boolean isSelected, showForm;
	private User userSelected;
	private MDomotiqueManager mDomotiqueManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.management_users);
		
		mDomotiqueManager = MDomotiqueManager.getInstance();
		
		userSelected = null;
		isSelected = false;
		showForm = false;
	    
		back 	  = (LinearLayout) findViewById(R.id.back);
		info_user = (LinearLayout) findViewById(R.id.info_user);
		add  	  = (LinearLayout) findViewById(R.id.add);
		
		name   = (TextView) findViewById(R.id.name);
		
		loading 	= (ProgressBar) findViewById(R.id.loading);
		
		nameUser  	  = (EditText) findViewById(R.id.nameUser);
		firstUser  	  = (EditText) findViewById(R.id.firstUser);
		loginUser  	  = (EditText) findViewById(R.id.loginUser);
		passwordUser  = (EditText) findViewById(R.id.passwordUser);
		
		addUser  	  = (Button) findViewById(R.id.addUser);
		
		list_users = (ListView) findViewById(R.id.list_users);
		
		name.setText(getResources().getString(R.string.management_users));
		
		back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		isSelected = true;
            		if(showForm){
            			
            			Animation animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_right);
            	        animation.reset();
            	        list_users.clearAnimation();
            	        list_users.startAnimation(animation);
            	        list_users.setVisibility(View.VISIBLE);

            	        animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_left);
            	        animation.reset();
            	        info_user.clearAnimation();
            	        info_user.startAnimation(animation);
            	        info_user.setVisibility(View.GONE);        	       

            			add.setVisibility(View.VISIBLE);
            			showForm = false;
            			userSelected = null;
            		}else{
						Intent intent = new Intent(ManagementUsers.this, ListRooms.class);
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
		
		addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!isSelected){
            		isSelected = true;
            		managementRoom();
            	}
            }
        });
				
		setContent();
		
		list_users.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	userSelected = mDomotiqueManager.getListUsers().get(position);
            	ShowDialog();
            }
        });
		
	}
	
	private void setContent(){
		
		mDomotiqueManager.setParsingUsersFinish(false);
		
		new Thread(){
			public void run(){
				mDomotiqueManager.setListUsers(new UsersParseur().getUsers());
				mDomotiqueManager.setParsingUsersFinish(true);
			}
		}.start();
		
		new Thread(){
			public void run(){
				while(!mDomotiqueManager.isParsingUsersFinish()){}
				
				ManagementUsers.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						
 						list_users.setAdapter(new UsersAdapter(getApplicationContext(), R.layout.single_user, mDomotiqueManager.getListUsers()));
 						
 						loading.setVisibility(View.GONE);
 						list_users.setVisibility(View.VISIBLE);
 					}
				});
			}
		}.start();
	}
	
	
	private void managementRoom(){
		        
        Animation animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_right);
        animation.reset();
        loading.clearAnimation();
        loading.startAnimation(animation);
        loading.setVisibility(View.VISIBLE);

        animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_left);
        animation.reset();
        info_user.clearAnimation();
        info_user.startAnimation(animation);
        info_user.setVisibility(View.GONE);  

		if(userSelected==null){
			new Thread(){
				public void run(){
					
					try{
						sleep(1000);
					}catch(Exception e){}
		
					User user = new User();
					user.setName(nameUser.getText().toString());
					user.setFirstname(firstUser.getText().toString());
					user.setLogin(loginUser.getText().toString());
					user.setPassword(passwordUser.getText().toString());
					
					if(new UsersParseur().addUser(user)){						
						ManagementUsers.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
								showMessage(getResources().getString(R.string.user_added));
								setContent();
		 					}
						});
					}else{
						ManagementUsers.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
								showMessage(getResources().getString(R.string.user_not_add));
		 					}
						});
					}
					
					userSelected = null;
					isSelected = false;					
				}
			}.start();
		}else{
	
			new Thread(){
				public void run(){
					
					try{
						sleep(1000);
					}catch(Exception e){}
					
					userSelected.setName(nameUser.getText().toString());
					userSelected.setFirstname(firstUser.getText().toString());
					userSelected.setPassword(passwordUser.getText().toString());
					userSelected.setLogin(loginUser.getText().toString());
					
					if(new UsersParseur().updateUser(userSelected)){						
						ManagementUsers.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						showMessage(getResources().getString(R.string.user_update));
								setContent();
		 					}
						});
						
					}else{
						ManagementUsers.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						showMessage(getResources().getString(R.string.user_not_update));
		 					}
						});
					}

					userSelected = null;
					isSelected = false;
				}
			}.start();
		}
		
		showForm = false;

	}
	
	public void ShowDialog(){
		final Dialog dialog = new Dialog(ManagementUsers.this);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_contextuel_room);
		dialog.setTitle(userSelected.getName());

		final LinearLayout edit = (LinearLayout) dialog.findViewById(R.id.edit); 
		final LinearLayout del  = (LinearLayout) dialog.findViewById(R.id.del); 
		final LinearLayout exit = (LinearLayout) dialog.findViewById(R.id.exit);
		
		final TextView edit_txt = (TextView) dialog.findViewById(R.id.edit_txt); 
		final TextView del_txt  = (TextView) dialog.findViewById(R.id.del_txt); 
		final TextView exit_txt = (TextView) dialog.findViewById(R.id.exit_txt);
		
		edit_txt.setText(getResources().getString(R.string.update_user));
		del_txt.setText(getResources().getString(R.string.delete_user));
		
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
				removeUser();
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
		addUser.setText("Ajouter");
		
		nameUser.setText("");
		firstUser.setText("");
		loginUser.setText("");
		passwordUser.setText("");

		Animation animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_right);
        animation.reset();
        info_user.clearAnimation();
        info_user.startAnimation(animation);
        info_user.setVisibility(View.VISIBLE);

        animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_left);
        animation.reset();
        list_users.clearAnimation();
        list_users.startAnimation(animation);
        list_users.setVisibility(View.GONE);
		
		showForm = true;
		
	}
	
	protected void showForm() {
		
		addUser.setText("Modifier");
		
		nameUser.setText(userSelected.getName());
		firstUser.setText(userSelected.getFirstname());
		loginUser.setText(userSelected.getLogin());
		passwordUser.setText(userSelected.getPassword());

		Animation animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_right);
        animation.reset();
        info_user.clearAnimation();
        info_user.startAnimation(animation);
        info_user.setVisibility(View.VISIBLE);

        animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_left);
        animation.reset();
        list_users.clearAnimation();
        list_users.startAnimation(animation);
        list_users.setVisibility(View.GONE);
		
		showForm = true;
		
	}

	protected void removeUser() {
		AlertDialog alertDialog = new AlertDialog.Builder(ManagementUsers.this).create();

		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Supprimer");
		alertDialog.setMessage(getResources().getString(R.string.ask_delete_user));
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new Thread(){
					@Override
					public void run(){
						ManagementUsers.this.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						if(new UsersParseur().RemoveUser(userSelected.getId())){
		 							showMessage(getResources().getString(R.string.user_deleted));

                                    Animation animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_left);
                                    animation.reset();
                                    loading.clearAnimation();
                                    loading.startAnimation(animation);
		 							loading.setVisibility(View.VISIBLE);

                                    animation = AnimationUtils.loadAnimation(ManagementUsers.this, R.anim.translate_left);
                                    animation.reset();
                                    list_users.clearAnimation();
                                    list_users.startAnimation(animation);
		 							list_users.setVisibility(View.GONE);

									setContent();
									showForm = false;
		 						}else
		 							showMessage(getResources().getString(R.string.user_not_deleted));
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

		Toast toast = new Toast(ManagementUsers.this);
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
