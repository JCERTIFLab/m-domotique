package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.services.Parametres;

public class EquipementCategoriesParseur {

	public ArrayList<EquipementCategory> getEquipementCategories(){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.getAllEquipementCategories));              
                
		NodeList nodes = doc.getElementsByTagName("EquipementCategory");
		
		ArrayList<EquipementCategory> listEquipementCategories = new ArrayList<EquipementCategory>();
					
		for (int i = 0; i < nodes.getLength(); i++) {						
			
			Element e = (Element)nodes.item(i);
			
			EquipementCategory equipementCategory = new EquipementCategory();

			equipementCategory.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
			equipementCategory.setName(XMLfunctions.getValue(e, "name"));
			equipementCategory.setImg(XMLfunctions.getValue(e, "img"));
			
			listEquipementCategories.add(equipementCategory);
		}	
		
		return listEquipementCategories;
		
	}

}
