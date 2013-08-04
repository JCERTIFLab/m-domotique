package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.persistance.Room;
import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.services.Parametres;

public class RoomsParseur extends RESTRequets{

	public ArrayList<Room> getRooms(){

        JSONArray categories = null;
        JSONObject json = doGet(Parametres.getAllRooms);

        ArrayList<Room> listRooms = new ArrayList<Room>();

        if(json != null){
	        try {
	            categories = json.getJSONArray("piece");
	            int sizeRooms = categories.length();
	
	            for(int i = 0; i < sizeRooms; i++){
	
	                JSONObject jsonObject = categories.getJSONObject(i);
	                Room room = new Room();
	                room.setId(jsonObject.getInt("id"));
	                room.setName(jsonObject.getString("nom"));
	
	                JSONObject category = jsonObject.getJSONObject("typePieceId");
	                RoomCategory roomCategory = new RoomCategory();
	                roomCategory.setId(category.getInt("id"));
	                roomCategory.setName(category.getString("nom"));
	                roomCategory.setImg(category.getString("imf"));
	                room.setRoomCategory(roomCategory);
	
	                listRooms.add(room);
	
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
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
