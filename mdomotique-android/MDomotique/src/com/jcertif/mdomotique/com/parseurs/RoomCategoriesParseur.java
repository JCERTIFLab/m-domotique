package com.jcertif.mdomotique.com.parseurs;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.services.Parametres;

public class RoomCategoriesParseur extends RESTRequets{

	public ArrayList<RoomCategory> getRoomCategories(){
		
        ArrayList<RoomCategory> listRoomCategories = new ArrayList<RoomCategory>();
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(Parametres.getAllRoomsCategories);
		httpget.addHeader("Content-Type", "application/xml");
		HttpResponse response;

		try {
			response = httpclient.execute(httpget);
		    HttpEntity entity = response.getEntity();

	        StatusLine responseStatus = response.getStatusLine();
	        int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;

	        if(statusCode==200){
	        	
	        	if (entity != null) {
	        		
	        		InputStream instream = entity.getContent();
	        		
	                Document doc = XMLfunctions.XMLfromString(convertStreamToString(instream));   
	                    
	            	NodeList nodes = doc.getElementsByTagName("typePiece");

	            					
	            	int nbrMax = nodes.getLength();
	            	for (int i = 0; i < nbrMax; i++) {						
	            		
	            		Element element = (Element)nodes.item(i);
	            		
	            		RoomCategory roomCategory = new RoomCategory();
	            		
		                roomCategory.setId(Integer.parseInt(XMLfunctions.getValue(element, "id")));
		                roomCategory.setName(XMLfunctions.getValue(element, "nom"));
		                roomCategory.setImg(XMLfunctions.getValue(element, "imf"));
		
		                listRoomCategories.add(roomCategory);
	                        
	            	}	
	            	
	        	}   
	        
	        }

		} catch (Exception e) {
		        e.printStackTrace();
		} 
		
        return listRoomCategories;
		
	}
	
	public boolean addRoomCategory(RoomCategory roomCategory){
		
		boolean reponse = false;   
        try {
        	JSONObject roomCategoryObject=new JSONObject();
        	roomCategoryObject.put("id", roomCategory.getId());
        	roomCategoryObject.put("nom", roomCategory.getName());
        	roomCategoryObject.put("imf", roomCategory.getImg());
        	
        	JSONObject json = doPost(Parametres.addRoomCategory, roomCategoryObject);
            if(json.getString("state").equals("ok"))
            	reponse = true;	            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
		return reponse;
		
	}

}
