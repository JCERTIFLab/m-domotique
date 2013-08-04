package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;

import com.jcertif.mdomotique.com.RESTRequets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.services.Parametres;

public class EquipementCategoriesParseur extends RESTRequets {

	public ArrayList<EquipementCategory> getEquipementCategories(){

        JSONArray categories = null;
        JSONObject json = doGet(Parametres.getAllEquipementCategories);

        ArrayList<EquipementCategory> listEquipementCategories = new ArrayList<EquipementCategory>();

        if(json != null){
	        try {
	            categories = json.getJSONArray("typeEquipement");
	            int sizeCategories = categories.length();
	
	            for(int i = 0; i < sizeCategories; i++){
	                JSONObject jsonObject = categories.getJSONObject(i);
	
	                EquipementCategory equipementCategory = new EquipementCategory();
	
	                equipementCategory.setId(jsonObject.getInt("id"));
	                equipementCategory.setName(jsonObject.getString("nom"));
	                equipementCategory.setImg(jsonObject.getString("imf"));
	
	                listEquipementCategories.add(equipementCategory);
	
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
        }

        return listEquipementCategories;
		
	}

}
