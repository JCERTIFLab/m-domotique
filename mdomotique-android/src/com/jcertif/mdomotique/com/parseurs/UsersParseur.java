package com.jcertif.mdomotique.com.parseurs;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.User;
import com.jcertif.mdomotique.services.Parametres;

public class UsersParseur extends RESTRequets{
	
	public ArrayList<User> getUsers(){
		
		ArrayList<User> listUsers = new ArrayList<User>();
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(Parametres.getAllUsers);
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
        
					NodeList nodes = doc.getElementsByTagName("user");
								
					for (int i = 0; i < nodes.getLength(); i++) {						
						
						Element e = (Element)nodes.item(i);
						
						User user = new User();
						
						user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
						user.setLogin(XMLfunctions.getValue(e, "login"));
						user.setName(XMLfunctions.getValue(e, "nom"));
			            user.setPassword(XMLfunctions.getValue(e, "password"));
			            user.setFirstname(XMLfunctions.getValue(e, "prenom"));
			
			            listUsers.add(user);
					}	
					
	        	}
	        	
	        }
	        
		}catch(Exception e){}
		
		return listUsers;
	}
	
	public User authentification(String login, String password){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.getAuthentificationURL(login, password)));              
        
		User user = null;
		
		try{
			
			NodeList nodes = doc.getElementsByTagName("user");
			
			Element e = (Element)nodes.item(0);
			
			user = new User();

			user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
			user.setLogin(XMLfunctions.getValue(e, "login"));
			user.setName(XMLfunctions.getValue(e, "nom"));
	        user.setPassword(XMLfunctions.getValue(e, "password"));
	        user.setFirstname(XMLfunctions.getValue(e, "prenom"));
			
		}catch(Exception e){}
		
		
		return user;
		
	}

	public boolean addUser(User user){
		
		boolean reponse = false;   
        try {
        	JSONObject userObject=new JSONObject();
        	userObject.put("login", user.getLogin());
        	userObject.put("nom", user.getName());
        	userObject.put("password", user.getPassword());
        	userObject.put("prenom", user.getFirstname());
        	
        	JSONObject json = doPost(Parametres.addUser, userObject);
            if(json.getString("state").equals("ok"))
            	reponse = true;	            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
		return reponse;
		
	}
	
	public boolean updateUser(User user){

		boolean reponse = false;   
        try {
        	JSONObject userObject=new JSONObject();
        	userObject.put("id", user.getId());
        	userObject.put("login", user.getLogin());
        	userObject.put("nom", user.getName());
        	userObject.put("password", user.getPassword());
        	userObject.put("prenom", user.getFirstname());
        	
        	JSONObject json = doPost(Parametres.updateUser, userObject);
            if(json.getString("state").equals("ok"))
            	reponse = true;	            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
		return reponse;
		
	}
	
	public boolean RemoveUser(int idUser){
		
		boolean reponse = false;   
        try {
        	
        	JSONObject json = doPost(Parametres.deleteUser+idUser, new JSONObject());
            if(json.getString("state").equals("ok"))
            	reponse = true;	   
        	         
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return reponse;
        
	}

}
