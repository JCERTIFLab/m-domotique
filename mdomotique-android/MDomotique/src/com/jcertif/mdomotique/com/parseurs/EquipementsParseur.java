package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.Equipement;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.services.Parametres;

public class EquipementsParseur {

	public ArrayList<Equipement> getAllEquipements(int idRoom){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.getEquipementsByRoom+idRoom));              
                
		NodeList nodes = doc.getElementsByTagName("Equipment");
		
		ArrayList<Equipement> listEquipements = new ArrayList<Equipement>();
					
		for (int i = 0; i < nodes.getLength(); i++) {						
			
			Element element = (Element)nodes.item(i);
			
			Equipement equipement = new Equipement();

			equipement.setId(Integer.parseInt(XMLfunctions.getValue(element, "id")));
			equipement.setName(XMLfunctions.getValue(element, "name"));
			equipement.setDescription(XMLfunctions.getValue(element, "description"));
			if(XMLfunctions.getValue(element, "state").equals("0"))
				equipement.setState(false);
			else
				equipement.setState(true);
			equipement.setPin(Integer.parseInt(XMLfunctions.getValue(element, "pin")));
			equipement.setRoom_id(Integer.parseInt(XMLfunctions.getValue(element, "room_id")));
			equipement.setEquipement_type_id(Integer.parseInt(XMLfunctions.getValue(element, "equipement_type_id")));
			
			
			listEquipements.add(equipement);
		}	
		
		nodes = doc.getElementsByTagName("EquipementCategory");
		
		for (int i = 0; i < nodes.getLength(); i++) {
			
			Element element = (Element)nodes.item(i);
			
			EquipementCategory equipementCategory = new EquipementCategory();

			equipementCategory.setId(Integer.parseInt(XMLfunctions.getValue(element, "id")));
			equipementCategory.setName(XMLfunctions.getValue(element, "name"));
			equipementCategory.setImg(XMLfunctions.getValue(element, "img"));				
			
			listEquipements.get(i).setEquipementCategory(equipementCategory);
			
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
