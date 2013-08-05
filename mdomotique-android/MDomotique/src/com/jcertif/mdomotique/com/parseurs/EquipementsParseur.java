package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.persistance.Equipement;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.services.Parametres;

public class EquipementsParseur extends RESTRequets{

	public ArrayList<Equipement> getAllEquipements(int idRoom){
		
		JSONArray roomsArray = null;
        JSONObject json = doPost(Parametres.getEquipementsByRoom+idRoom, new JSONObject());

        ArrayList<Equipement> listEquipements = new ArrayList<Equipement>();

        if(json != null){
	        try {
	        	roomsArray = json.getJSONArray("equipement");       	
	            int sizeEquipements = roomsArray.length();
	            
	        	for(int i = 0; i < sizeEquipements; i++){
	        		
	        		JSONObject jsonObject = roomsArray.getJSONObject(i);
		            Equipement equipement = new Equipement();
		            equipement.setId(jsonObject.getInt("id"));
		            equipement.setName(jsonObject.getString("nom"));
		            equipement.setDescription(jsonObject.getString("description"));	
		            equipement.setState(jsonObject.getBoolean("etat"));
		            equipement.setPin(jsonObject.getInt("relay"));
		
		            JSONObject category = jsonObject.getJSONObject("typeId");
		            EquipementCategory equipementCategory = new EquipementCategory();
		            equipementCategory.setId(category.getInt("id"));
		            equipementCategory.setName(category.getString("nom"));
		            equipementCategory.setImg(category.getString("imf"));
		            equipement.setEquipementCategory(equipementCategory);
		
		            listEquipements.add(equipement);
		
		        }
	        	
	        } catch (JSONException e) {}
	        
	        if(roomsArray==null){
	        	try {
	        		
		        	JSONObject jsonObject = json.getJSONObject("equipement");
	       		 	Equipement equipement = new Equipement();
		            equipement.setId(jsonObject.getInt("id"));
		            equipement.setName(jsonObject.getString("nom"));
		            equipement.setDescription(jsonObject.getString("description"));	
		            equipement.setState(jsonObject.getBoolean("etat"));
		            equipement.setPin(jsonObject.getInt("relay"));
		
		            JSONObject category = jsonObject.getJSONObject("typeId");
		            EquipementCategory equipementCategory = new EquipementCategory();
		            equipementCategory.setId(category.getInt("id"));
		            equipementCategory.setName(category.getString("nom"));
		            equipementCategory.setImg(category.getString("imf"));
		            equipement.setEquipementCategory(equipementCategory);
		
		            listEquipements.add(equipement);
		            
	        	 } catch (JSONException e) {}
	        }
        }

        return listEquipements;
		
	}
	
	public boolean ExecuteAction(int pin, int etat){
//		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.executAction+"/"+pin+"/"+etat));
//		
//		NodeList nodes = doc.getElementsByTagName("response");
//		
//		Element e = (Element)nodes.item(0);
//		
//		if(XMLfunctions.getValue(e, "status").equals("OK"))
//			return true;
//		else
			return false;
	}
	
	public boolean RemoveEquipement(int idEquipement){
		
		boolean reponse = false;   
        try {
        	
        	JSONObject json = doPost(Parametres.deleteEquipement+idEquipement, new JSONObject());
            if(json.getString("state").equals("ok"))
            	reponse = true;	   
        	         
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return reponse;
		
	}
	
	public boolean addEquipement(Equipement equipement){
				
		boolean reponse = false;   
        try {
        	
        	JSONObject equipementObject=new JSONObject();
        	equipementObject.put("description", equipement.getDescription());
        	equipementObject.put("etat", equipement.isState()+"");
        	equipementObject.put("nom", equipement.getName());
        	
        	JSONObject categoryRoomObject=new JSONObject();
        	categoryRoomObject.put("id", equipement.getRoom().getRoomCategory().getId()+"");
        	categoryRoomObject.put("nom", equipement.getRoom().getRoomCategory().getName());
        	categoryRoomObject.put("imf", equipement.getRoom().getRoomCategory().getImg());
        	
        	JSONObject roomObject=new JSONObject();
        	roomObject.put("id", equipement.getRoom().getId()+"");
        	roomObject.put("nom", equipement.getRoom().getName());
        	roomObject.put("typePieceId", categoryRoomObject);
        	equipementObject.put("pieceId", roomObject);
        	
        	equipementObject.put("relay", equipement.getPin()+"");
        	
        	JSONObject categoryEquipementObject=new JSONObject();
        	categoryEquipementObject.put("id", equipement.getEquipementCategory().getId()+"");
        	categoryEquipementObject.put("nom", equipement.getEquipementCategory().getName());
        	categoryEquipementObject.put("imf", equipement.getEquipementCategory().getImg());
        	equipementObject.put("typeId", categoryEquipementObject);
        	
        	JSONObject json = doPost(Parametres.addEquipement, equipementObject);
            if(json.getString("state").equals("ok"))
            	reponse = true;	            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
		return reponse;
		
	}
	
	public boolean updateEquipement(Equipement equipement){

		boolean reponse = false;   
        try {
        	
        	JSONObject equipementObject=new JSONObject();
        	equipementObject.put("id", equipement.getId());
        	equipementObject.put("description", equipement.getDescription());
        	equipementObject.put("etat", equipement.isState()+"");
        	equipementObject.put("nom", equipement.getName());
        	
        	JSONObject categoryRoomObject=new JSONObject();
        	categoryRoomObject.put("id", equipement.getRoom().getRoomCategory().getId()+"");
        	categoryRoomObject.put("nom", equipement.getRoom().getRoomCategory().getName());
        	categoryRoomObject.put("imf", equipement.getRoom().getRoomCategory().getImg());
        	
        	JSONObject roomObject=new JSONObject();
        	roomObject.put("id", equipement.getRoom().getId()+"");
        	roomObject.put("nom", equipement.getRoom().getName());
        	roomObject.put("typePieceId", categoryRoomObject);
        	equipementObject.put("pieceId", roomObject);
        	
        	equipementObject.put("relay", equipement.getPin()+"");
        	
        	JSONObject categoryEquipementObject=new JSONObject();
        	categoryEquipementObject.put("id", equipement.getEquipementCategory().getId()+"");
        	categoryEquipementObject.put("nom", equipement.getEquipementCategory().getName());
        	categoryEquipementObject.put("imf", equipement.getEquipementCategory().getImg());
        	equipementObject.put("typeId", categoryEquipementObject);
        	
        	JSONObject json = doPost(Parametres.updateEquipement, equipementObject);
            if(json.getString("state").equals("ok"))
            	reponse = true;	            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
		return reponse;
		
	}

}
