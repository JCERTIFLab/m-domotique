package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.com.XMLfunctions;
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
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.deleteRoom+idRoom));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
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
        	
        	Log.i("test","str :: "+roomObject.toString());
        	
        	JSONObject json = doPost(Parametres.addRoom, roomObject);
            if(json.getString("state").equals("ok"))
            	reponse = true;	            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
		return reponse;
		
	}
	
	public boolean updateRoom(Room room){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("id", room.getId()+""));
		
		if(room.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("name", room.getName()));
		
		if(room.getTypeId().length()>0)
			nameValuePairs.add(new BasicNameValuePair("room_type_id", room.getTypeId()));
		
		Document doc = XMLfunctions.XMLfromString(Parametres.postData(nameValuePairs, Parametres.updateRoom));  
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}

}
