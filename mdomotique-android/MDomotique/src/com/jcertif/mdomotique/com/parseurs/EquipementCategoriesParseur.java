package com.jcertif.mdomotique.com.parseurs;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.services.Parametres;

public class EquipementCategoriesParseur extends RESTRequets {

	public ArrayList<EquipementCategory> getEquipementCategories(){
		
		ArrayList<EquipementCategory> listEquipementCategories = new ArrayList<EquipementCategory>();
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(Parametres.getAllEquipementCategories);
		httpget.addHeader("Content-Type", "application/xml");
		HttpResponse response;

		try {
			response = httpclient.execute(httpget);
		    HttpEntity entity = response.getEntity();

	        StatusLine responseStatus = response.getStatusLine();
	        int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;

	        if(statusCode==200){
	        	
	        	if (entity != null) {
	        		
	        		InputStream instream = entity.getContent();
	                String result= convertStreamToString(instream);

	                Document doc = XMLfunctions.XMLfromString(result);   
	                    
	            	NodeList nodes = doc.getElementsByTagName("typeEquipement");

	            					
	            	int nbrMax = nodes.getLength();
	            	for (int i = 0; i < nbrMax; i++) {						
	            		
	            		Element element = (Element)nodes.item(i);
	            		
	            		EquipementCategory equipementCategory = new EquipementCategory();
	            		
	            		equipementCategory.setId(Integer.parseInt(XMLfunctions.getValue(element, "id")));
	            		equipementCategory.setName(XMLfunctions.getValue(element, "nom"));
	            		equipementCategory.setImg(XMLfunctions.getValue(element, "imf"));
		
	            		listEquipementCategories.add(equipementCategory);
	                        
	            	}	
	            	
	        	}   
	        
	        }

		} catch (Exception e) {
		        e.printStackTrace();
		} 

        return listEquipementCategories;
		
	}

}
