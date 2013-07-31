package com.jcertif.mdomotique.services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class OutilsInternet {

	/*** check network*/
	  public static boolean HttpTest(final Activity mActivity)
	  {
		  boolean rep;
		  if( !isNetworkAvailable( mActivity) )
			  rep = false;
	      else
	    	  rep = true;
	          
	      return rep;
	 }   
	    
	  public static boolean isNetworkAvailable( Activity mActivity ) 
	  { 
	          Context context = mActivity.getApplicationContext();
	          ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          
	          if (connectivity == null) 
	          {   
	        Log.d("isNetworkAvailable"," connectivity is null");
	        return false;
	          } 
	          else 
	          {  
	        Log.d("isNetworkAvailable"," connectivity is not null");
	        
	        NetworkInfo[] info = connectivity.getAllNetworkInfo();   
	        if (info != null) 
	        {   
	                Log.d("isNetworkAvailable"," info is not null");
	                for (int i = 0; i <info.length; i++) 
	                { 
	                        if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                        {
	                                return true; 
	                        }
	                        else
	                                Log.d("isNetworkAvailable"," info["+i+"] is not connected");
	             }     
	         } 
	        else
	                Log.d("isNetworkAvailable"," info is null");
	     }   
	          return false;
	 }   
}
