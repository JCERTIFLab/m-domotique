package com.jcertif.mdomotique.android.ihm;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.actionbarsherlock.app.SherlockActivity;
import com.jcertif.mdomotique.android.R;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
