package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.services.Parametres;

public class RoomCategoriesParseur {

	public ArrayList<RoomCategory> getRoomCategories(){
		Log.i("test","URL : "+Parametres.getAllRoomsCategories);
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.getAllRoomsCategories));              
                
		NodeList nodes = doc.getElementsByTagName("RoomCategory");
		
		ArrayList<RoomCategory> listRoomCategory = new ArrayList<RoomCategory>();
					
		for (int i = 0; i < nodes.getLength(); i++) {						
			
			Element e = (Element)nodes.item(i);
			
			RoomCategory roomCategory = new RoomCategory();

			roomCategory.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
			roomCategory.setName(XMLfunctions.getValue(e, "name"));
			roomCategory.setImg(XMLfunctions.getValue(e, "img"));
			
			listRoomCategory.add(roomCategory);
		}	
		
		return listRoomCategory;
		
	}

}
