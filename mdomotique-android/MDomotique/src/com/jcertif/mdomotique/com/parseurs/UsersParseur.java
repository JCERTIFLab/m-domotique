package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcertif.mdomotique.com.RESTRequets;
import com.jcertif.mdomotique.persistance.User;
import com.jcertif.mdomotique.services.Parametres;

public class UsersParseur extends RESTRequets{
	
	public ArrayList<User> getUsers(){

        JSONArray usersArray = null;
        JSONObject json = doGet(Parametres.getAllUsers);
        ArrayList<User> listUsers = new ArrayList<User>();
       
        if(json!=null){
	        try {
	        	usersArray = json.getJSONArray("user");
	            int sizeUsers = usersArray.length();
	
	            for(int i = 0; i < sizeUsers; i++){
	                JSONObject jsonObject = usersArray.getJSONObject(i);
	
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
	        
	        if(usersArray==null){
	        	try {
	        		
	        		User user = new User();
	        		
	        		JSONObject jsonObject = json.getJSONObject("user");
	                user.setId(jsonObject.getInt("id"));
	                user.setLogin(jsonObject.getString("login"));
	                user.setName(jsonObject.getString("nom"));
	                user.setPassword(jsonObject.getString("password"));
	                user.setFirstname(jsonObject.getString("prenom"));
	
	                listUsers.add(user);
		            
	        	 } catch (JSONException e) {}        
	        }
        }
		
		return listUsers;
	}
	
	public User authentification(String login, String password){

        JSONObject jsonObject = doPost(Parametres.getAuthentificationURL(login, password), new JSONObject());

        User user = null;

        if(jsonObject!=null){
        	try{
      		
	        	user = new User();
	        	user.setId(jsonObject.getInt("id"));
	        	user.setLogin(jsonObject.getString("login"));
	        	user.setName(jsonObject.getString("nom"));
	        	user.setPassword(jsonObject.getString("password"));
	        	user.setFirstname(jsonObject.getString("prenom"));
	        	
        	}catch(Exception e){}
        }
		
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
