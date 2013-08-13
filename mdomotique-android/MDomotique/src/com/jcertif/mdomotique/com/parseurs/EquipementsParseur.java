package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.Equipement;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.services.Parametres;

public class EquipementsParseur extends RESTRequets{

	public ArrayList<Equipement> getAllEquipements(int idRoom){
		
		ArrayList<Equipement> listEquipements = new ArrayList<Equipement>();
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.getEquipementsByRoom+idRoom));              
        
		NodeList nodes = doc.getElementsByTagName("equipement");
		
		int nbrMax = nodes.getLength();
		
    	for (int i = 0; i < nbrMax; i++) {						
    		
    		Element element = (Element)nodes.item(i);

    		Equipement equipement = new Equipement();
    		
            equipement.setId(Integer.parseInt(XMLfunctions.getValue(element, "id")));
            equipement.setName(XMLfunctions.getValue(element, "nom"));
            equipement.setDescription(XMLfunctions.getValue(element, "description"));	
            if(XMLfunctions.getValue(element, "etat").equals("oui"))
            	equipement.setState(true);
            else
            	equipement.setState(false);
            equipement.setPin(Integer.parseInt(XMLfunctions.getValue(element, "relay")));
            
            listEquipements.add(equipement);
                
    	}
    	
    	nodes = doc.getElementsByTagName("typeId");
		
    	nbrMax = nodes.getLength();
    	for (int i = 0; i < nbrMax; i++) {			
    			
    		Element element = (Element)nodes.item(i);
            
            EquipementCategory equipementCategory = new EquipementCategory();
    			
            equipementCategory.setId(Integer.parseInt(XMLfunctions.getValue(element, "id")));
            equipementCategory.setName(XMLfunctions.getValue(element, "name"));
            equipementCategory.setImg(XMLfunctions.getValue(element, "imf"));
    			
            listEquipements.get(i).setEquipementCategory(equipementCategory);
    			
    	}
    	
    	return listEquipements;
    	
	}	
    	
	public boolean ExecuteAction(int id, int etat){


		boolean reponse = false;   
        try {
        	
        	JSONObject json = doPost(Parametres.executAction+"/"+id+"/"+etat, new JSONObject());
            if(json.getString("state").equals("ok"))
            	reponse = true;	   
        	         
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return reponse;
        
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
