package com.jcertif.mdomotique.com.parseurs;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.services.Parametres;
import com.jcertif.mdomotique.com.RESTRequets;

public class RoomCategoriesParseur extends RESTRequets{

	public ArrayList<RoomCategory> getRoomCategories(){

        JSONArray categories = null;
        JSONObject json = doGet(Parametres.getAllRoomsCategories);

        ArrayList<RoomCategory> listRoomCategories = new ArrayList<RoomCategory>();

        try {
            categories = json.getJSONArray("typePiece");
            int sizeCategories = categories.length();

            for(int i = 0; i < sizeCategories; i++){
                JSONObject jsonObject = categories.getJSONObject(i);

                RoomCategory roomCategory = new RoomCategory();

                roomCategory.setId(jsonObject.getInt("id"));
                roomCategory.setName(jsonObject.getString("nom"));
                roomCategory.setImg(jsonObject.getString("imf"));

                listRoomCategories.add(roomCategory);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listRoomCategories;
		
	}

}
