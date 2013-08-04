package com.jcertif.mdomotique.services;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Parametres {
	
	public static String nomDomaine = "";

	public static String urlBase;
	
	public static String getAllRooms;
	public static String deleteRoom;
	public static String updateRoom;
	public static String addRoom;
	
	public static String getAllRoomsCategories;
	
	public static String getAllUsers;
	public static String addUser;
	public static String updateUser;
	public static String deleteUser;
	public static String authentification;
	
	public static String getEquipementsByRoom;
	public static String deleteEquipement;
	public static String addEquipement;
	public static String updateEquipement;
	public static String executAction;
	
	public static String getAllEquipementCategories;
	
	
	public static void setUrls(){
		
		urlBase = nomDomaine+"/mdomotique/rest/";
		
		getAllRooms = urlBase+"rooms/getAllRooms";
		deleteRoom = urlBase+"rooms/delete/";
		updateRoom = urlBase+"rooms/update";
		addRoom = urlBase+"rooms/add";
		
		getAllRoomsCategories = urlBase+"typepieces/getAllTypes";
		
		getAllUsers = urlBase+"users/getAllUsers";
		addUser = urlBase+"users/add";
		updateUser = urlBase+"users/update";
		deleteUser = urlBase+"users/delete/";
		authentification = urlBase+"users/auth/";
		
		getEquipementsByRoom = urlBase+"equipements/getEquipementsByRoom/";
		deleteEquipement = urlBase+"equipements/delete/";
		addEquipement = urlBase+"equipements/add";
		updateEquipement = urlBase+"equipements/update";
		executAction = urlBase+"equipements/action/";
		
		getAllEquipementCategories = urlBase+"typeequipements/getAllTypesEquipements";
	}
	
	
	public static String getAuthentificationURL(String login, String password){
		return authentification+login+"/"+password;
	}
	
	public static String getImgURL(String url){
		return nomDomaine+url;
	}
	
	public static String postData(List<NameValuePair> nameValuePairs, String url) {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity resEntity = response.getEntity();
			String str = "";
			if (resEntity != null)    
				str = EntityUtils.toString(resEntity);

			return str;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	} 

}
