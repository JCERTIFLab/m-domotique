package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcertif.mdomotique.com.RESTRequets;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.mdomotique.com.XMLfunctions;
import com.jcertif.mdomotique.persistance.User;
import com.jcertif.mdomotique.services.Parametres;

public class UsersParseur extends RESTRequets{
	
	public ArrayList<User> getUsers(){

        JSONArray users = null;
        JSONObject json = doGet(Parametres.getAllUsers);

        ArrayList<User> listUsers = new ArrayList<User>();

        try {
            users = json.getJSONArray("user");
            int sizeUsers = users.length();

            for(int i = 0; i < sizeUsers; i++){
                JSONObject jsonObject = users.getJSONObject(i);

                User user = new User();

                user.setId(jsonObject.getInt("id"));
                user.setLogin(jsonObject.getString("login"));
                user.setName(jsonObject.getString("nom"));
                user.setPassword(jsonObject.getString("password"));
                user.setFirstname(jsonObject.getString("prenom"));

                listUsers.add(user);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
		return listUsers;
	}
	
	public User authentification(String login, String password){

		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.getAuthentificationURL(login, password)));   
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		User user = null;
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			user = new User();
			
			nodes = doc.getElementsByTagName("User");
			
			e = (Element)nodes.item(0);

			user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
			user.setFirstname(XMLfunctions.getValue(e, "firstname"));
			user.setName(XMLfunctions.getValue(e, "lastname"));
			user.setLogin(XMLfunctions.getValue(e, "login"));
			user.setPassword(XMLfunctions.getValue(e, "password"));

		}
		
		return user;
		
	}
	
	public boolean addUser(User user){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(user.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("lastname", user.getName()));
		
		if(user.getFirstname().length()>0)
			nameValuePairs.add(new BasicNameValuePair("firstname", user.getFirstname()));
		
		if(user.getLogin().length()>0)
			nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
		
		if(user.getPassword().length()>0)
			nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));

		Document doc = XMLfunctions.XMLfromString(Parametres.postData(nameValuePairs, Parametres.addUser));  
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			return true;
		}else
			return false;
		
	}
	
	public boolean updateUser(User user){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("id", user.getId()+""));
		
		if(user.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("lastname", user.getName()));
		
		if(user.getFirstname().length()>0)
			nameValuePairs.add(new BasicNameValuePair("firstname", user.getFirstname()));
		
		if(user.getLogin().length()>0)
			nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
		
		if(user.getPassword().length()>0)
			nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));

		
		Document doc = XMLfunctions.XMLfromString(Parametres.postData(nameValuePairs, Parametres.updateUser));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))	
			return true;
		else
			return false;
		
	}
	
	public boolean RemoveUser(int idUser){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Parametres.deleteUser+idUser));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}

}
