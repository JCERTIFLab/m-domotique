package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.Room;
import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.services.Parametres;

public class RoomsParseur {

	public ArrayList<Room> getRooms(){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.getAllRooms));              
                
		NodeList nodes = doc.getElementsByTagName("Room");
		
		ArrayList<Room> listRooms = new ArrayList<Room>();
					
		for (int i = 0; i < nodes.getLength(); i++) {						
			
			Element e = (Element)nodes.item(i);
			
			Room room = new Room();

			room.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
			room.setName(XMLfunctions.getValue(e, "name"));
			room.setTypeId(XMLfunctions.getValue(e, "room_type_id"));
			
			listRooms.add(room);
		}	
		
		nodes = doc.getElementsByTagName("RoomType");
		
		for (int i = 0; i < nodes.getLength(); i++) {
			
			Element e = (Element)nodes.item(i);
			
			RoomCategory roomCategory = new RoomCategory();

			roomCategory.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
			roomCategory.setName(XMLfunctions.getValue(e, "name"));
			roomCategory.setImg(XMLfunctions.getValue(e, "img"));				
			
			listRooms.get(i).setRoomCategory(roomCategory);
			
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
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(room.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("name", room.getName()));
		
		if(room.getTypeId().length()>0)
			nameValuePairs.add(new BasicNameValuePair("room_type_id", room.getTypeId()));

		Document doc = XMLfunctions.XMLfromString(Parametres.postData(nameValuePairs, Parametres.addRoom));  
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
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
