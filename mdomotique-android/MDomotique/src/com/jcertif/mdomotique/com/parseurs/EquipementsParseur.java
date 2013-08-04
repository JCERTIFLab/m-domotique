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

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.com.XMLfunctions;
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
	        	if(json.length()>1){
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
	        	}else{
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
	        	}
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
        }

        return listEquipements;
		
	}
	
	public boolean ExecuteAction(int pin, int etat){
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.executAction+"/"+pin+"/"+etat));
		
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
	}
	
	public boolean RemoveEquipement(int idEquipement){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.deleteEquipement+idEquipement));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}
	
	public boolean addEquipement(Equipement equipement){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(equipement.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("name", equipement.getName()));
		
		if(equipement.getDescription().length()>0)
			nameValuePairs.add(new BasicNameValuePair("description", equipement.getDescription()));
	
		if(equipement.isState())
			nameValuePairs.add(new BasicNameValuePair("state", "1"));
		else
			nameValuePairs.add(new BasicNameValuePair("state", "0"));	
		
		if(equipement.getPin()+"".length()>0)
			nameValuePairs.add(new BasicNameValuePair("pin", equipement.getPin()+""));
		
		if(equipement.getRoom_id()+"".length()>0)
			nameValuePairs.add(new BasicNameValuePair("room_id", equipement.getRoom_id()+""));
		
		if(equipement.getEquipement_type_id()+"".length()>0)
			nameValuePairs.add(new BasicNameValuePair("equipement_type_id", equipement.getEquipement_type_id()+""));

		Document doc = XMLfunctions.XMLfromString(Parametres.postData(nameValuePairs, Parametres.addEquipement));  
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}
	
	public boolean updateEquipement(Equipement equipement){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("id", equipement.getId()+""));
		
		if(equipement.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("name", equipement.getName()));
		
		if(equipement.getDescription().length()>0)
			nameValuePairs.add(new BasicNameValuePair("description", equipement.getDescription()));
	
		if(equipement.isState())
			nameValuePairs.add(new BasicNameValuePair("state", "1"));
		else
			nameValuePairs.add(new BasicNameValuePair("state", "0"));	
		
		if(equipement.getPin()+"".length()>0)
			nameValuePairs.add(new BasicNameValuePair("pin", equipement.getPin()+""));
		
		if(equipement.getRoom_id()+"".length()>0)
			nameValuePairs.add(new BasicNameValuePair("room_id", equipement.getRoom_id()+""));
		
		if(equipement.getEquipement_type_id()+"".length()>0)
			nameValuePairs.add(new BasicNameValuePair("equipement_type_id", equipement.getEquipement_type_id()+""));

		Document doc = XMLfunctions.XMLfromString(Parametres.postData(nameValuePairs, Parametres.updateEquipement));  
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}

}
