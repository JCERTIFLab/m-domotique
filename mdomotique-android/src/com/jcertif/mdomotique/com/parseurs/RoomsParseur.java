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
import com.jcertif.mdomotique.persistance.Room;
import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.services.Parametres;

public class RoomsParseur extends RESTRequets{

	public ArrayList<Room> getRooms(){
		
		ArrayList<Room> listRooms = new ArrayList<Room>();
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpGet httpget = new HttpGet(Parametres.getAllRooms);
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
	                String result= convertStreamToString(instream);

	                Document doc = XMLfunctions.XMLfromString(result);   
	                    
	            	NodeList nodes = doc.getElementsByTagName("piece");

	            	int nbrMax = nodes.getLength();
	            	for (int i = 0; i < nbrMax; i++) {						
	            		
	            		Element element = (Element)nodes.item(i);
	            			
	            		Room room = new Room();
	            			
	            		room.setId(Integer.parseInt(XMLfunctions.getValue(element, "id")));
	            		room.setName(XMLfunctions.getValue(element, "nom"));  

	                    listRooms.add(room);
	                        
	            	}	            		
	            	
	            	nodes = doc.getElementsByTagName("typePieceId");
	            		
	            	nbrMax = nodes.getLength();
	            	for (int i = 0; i < nbrMax; i++) {			
	            			
	            		Element element = (Element)nodes.item(i);
	            			
	            		RoomCategory roomCategory = new RoomCategory();
	            			
	            		roomCategory.setId(Integer.parseInt(XMLfunctions.getValue(element, "id")));
	            		roomCategory.setName(XMLfunctions.getValue(element, "nom"));
	            		roomCategory.setImg(XMLfunctions.getValue(element, "imf"));
	            			
	            		listRooms.get(i).setRoomCategory(roomCategory);
	            			
	            	}	
	            	
	        	}   
	        
	        }

		} catch (Exception e) {
		        e.printStackTrace();
		} 
		
		return listRooms;
		
	}
	
	public boolean RemoveRoom(int idRoom){
		
		boolean reponse = false;   
        try {
        	
        	JSONObject json = doPost(Parametres.deleteRoom+idRoom, new JSONObject());
            if(json.getString("state").equals("ok"))
            	reponse = true;	   
        	         
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return reponse;
		
	}
	
	public boolean addRoom(Room room){
		
		boolean reponse = false;   
        try {
        	JSONObject roomObject=new JSONObject();
        	
        	JSONObject categoryObject=new JSONObject();
        	categoryObject.put("id", room.getRoomCategory().getId());
        	categoryObject.put("nom", room.getRoomCategory().getName());
        	categoryObject.put("imf", room.getRoomCategory().getImg());
        	roomObject.put("typePieceId", categoryObject);
        	
        	roomObject.put("nom", room.getName());
        	
        	JSONObject json = doPost(Parametres.addRoom, roomObject);
            if(json.getString("state").equals("ok"))
            	reponse = true;	            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
		return reponse;
		
	}
	
	public boolean updateRoom(Room room){

		boolean reponse = false;   
        try {
        	JSONObject roomObject=new JSONObject();
        	
        	JSONObject categoryObject=new JSONObject();
        	categoryObject.put("id", room.getRoomCategory().getId());
        	categoryObject.put("nom", room.getRoomCategory().getName());
        	categoryObject.put("imf", room.getRoomCategory().getImg());
        	roomObject.put("typePieceId", categoryObject);
        	
        	roomObject.put("nom", room.getName());
        	roomObject.put("id", room.getId());
        	
        	JSONObject json = doPost(Parametres.updateRoom, roomObject);
            if(json.getString("state").equals("ok"))
            	reponse = true;	            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return reponse;
		
	}

}
