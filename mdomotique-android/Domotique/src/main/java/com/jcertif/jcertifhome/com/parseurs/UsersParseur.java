package com.jcertif.jcertifhome.com.parseurs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.jcertif.jcertifhome.com.Requests;
import com.jcertif.jcertifhome.com.XMLfunctions;
import com.jcertif.jcertifhome.services.Parametres;

public class UsersParseur {
	
	public void getAllUsers(){
		
		Document doc = XMLfunctions.XMLfromString(Requests.Get(Parametres.allUsers));              
        
		NodeList nodes = doc.getElementsByTagName("userss");
		
		Element e = (Element)nodes.item(0);
		
		nodes = doc.getElementsByTagName("users");
		
		int nbrMax = nodes.getLength();
		
		for (int i = 0; i < nbrMax; i++) {
			
			e = (Element)nodes.item(i);
			
			Log.i("test","id : "+XMLfunctions.getValue(e, "id"));
			Log.i("test","login : "+XMLfunctions.getValue(e, "login"));
			Log.i("test","nom : "+XMLfunctions.getValue(e, "nom"));
			Log.i("test","password : "+XMLfunctions.getValue(e, "password"));
			Log.i("test","prenom : "+XMLfunctions.getValue(e, "prenom"));
			
//			User news = new User();
			
//			news.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
//			news.setUrl(XMLfunctions.getValue(e, "url"));
//			news.setTitle(XMLfunctions.getValue(e, "title"));
//			news.setContent(XMLfunctions.getValue(e, "content"));
//			news.setImg_url(XMLfunctions.getValue(e, "img_url"));
//			news.setCreated(XMLfunctions.getValue(e, "created"));				
			
//			jCertifManager.getListNews().add(news);
			
		}	
	}

}
