package com.jcertif.jcertifhome.interfaces.ihm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.jcertif.jcertifhome.R;
import com.jcertif.jcertifhome.interfaces.services.BaseActivity;
import com.jcertif.jcertifhome.interfaces.services.MenuFragment;
import com.jcertif.jcertifhome.services.JCertifManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class Home extends BaseActivity {
	
	private Fragment mContent;
	
	public Home() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		
		if (mContent == null){
			switch (JCertifManager.getInstance().menu_Selected){
			case 1:
				mContent = new Pieces();
				break;
			case 2:
				mContent = new Pieces();
				break;
			case 3:
				mContent = new Pieces();
				break;		
			}
			
		}
		
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();

		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);

	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		exit();
		return false;
	}

	private void exit(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Quitter");
		alertDialog.setMessage("Est ce que vous �tes s�r ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
				System.exit(0);
				return;
			} }); 
		alertDialog.setButton2("Non", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;
			}});


		alertDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1000){
			finish();
		}
	}

}
