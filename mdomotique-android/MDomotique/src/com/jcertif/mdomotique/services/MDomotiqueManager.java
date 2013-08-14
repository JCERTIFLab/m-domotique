package com.jcertif.mdomotique.services;

import java.util.ArrayList;

import com.jcertif.mdomotique.persistance.Equipement;
import com.jcertif.mdomotique.persistance.EquipementCategory;
import com.jcertif.mdomotique.persistance.Room;
import com.jcertif.mdomotique.persistance.RoomCategory;
import com.jcertif.mdomotique.persistance.User;



public class MDomotiqueManager {
	
	private ArrayList<User> listUsers = new ArrayList<User>();
	
	private ArrayList<Room> listRooms = new ArrayList<Room>();
	
	private ArrayList<RoomCategory> listRoomsCategories = new ArrayList<RoomCategory>();
	
	private ArrayList<RoomCategory> listInitialRoomsCategories = new ArrayList<RoomCategory>();
	
	private ArrayList<Equipement> listEquipements = new ArrayList<Equipement>();
	
	private ArrayList<EquipementCategory> listEquipementCategories = new ArrayList<EquipementCategory>();
	
	private ArrayList<EquipementCategory> listInitialEquipementCategories = new ArrayList<EquipementCategory>();
	
	private User userConnected;
	
	private Room roomSelected;
	
	private boolean parsingUsersFinish = false;
	
	private boolean parsingRoomsFinish = false;
	
	private boolean parsingRoomCategoryFinish = false;
	
	private boolean parsingEquipementCategoryFinish = false;
	
	private boolean stopLoading = false;
	
	public static MDomotiqueManager mDomotiqueManager;
	
	public static MDomotiqueManager getInstance() {
		
		if (mDomotiqueManager == null)
			mDomotiqueManager = new MDomotiqueManager();
		
		return mDomotiqueManager;
	}

	public ArrayList<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(ArrayList<User> listUsers) {
		this.listUsers = listUsers;
	}

	public User getUserConnected() {
		return userConnected;
	}

	public void setUserConnected(User userConnected) {
		this.userConnected = userConnected;
	}

	public ArrayList<Room> getListRooms() {
		return listRooms;
	}

	public void setListRooms(ArrayList<Room> listRooms) {
		this.listRooms = listRooms;
	}

	public boolean isParsingRoomsFinish() {
		return parsingRoomsFinish;
	}

	public void setParsingRoomsFinish(boolean parsingRoomsFinish) {
		this.parsingRoomsFinish = parsingRoomsFinish;
	}

	public boolean isParsingRoomCategoryFinish() {
		return parsingRoomCategoryFinish;
	}

	public void setParsingRoomCategoryFinish(boolean parsingRoomCategoryFinish) {
		this.parsingRoomCategoryFinish = parsingRoomCategoryFinish;
	}

	public ArrayList<RoomCategory> getListRoomsCategories() {
		return listRoomsCategories;
	}

	public void setListRoomsCategories(ArrayList<RoomCategory> listRoomsCategories) {
		this.listRoomsCategories = listRoomsCategories;
	}

	public Room getRoomSelected() {
		return roomSelected;
	}

	public void setRoomSelected(Room roomSelected) {
		this.roomSelected = roomSelected;
	}

	public ArrayList<Equipement> getListEquipements() {
		return listEquipements;
	}

	public void setListEquipements(ArrayList<Equipement> listEquipements) {
		this.listEquipements = listEquipements;
	}

	public ArrayList<EquipementCategory> getListEquipementCategories() {
		return listEquipementCategories;
	}

	public void setListEquipementCategories(ArrayList<EquipementCategory> listEquipementCategories) {
		this.listEquipementCategories = listEquipementCategories;
	}

	public boolean isParsingEquipementCategoryFinish() {
		return parsingEquipementCategoryFinish;
	}

	public void setParsingEquipementCategoryFinish(
			boolean parsingEquipementCategoryFinish) {
		this.parsingEquipementCategoryFinish = parsingEquipementCategoryFinish;
	}

	public boolean isParsingUsersFinish() {
		return parsingUsersFinish;
	}

	public void setParsingUsersFinish(boolean parsingUsersFinish) {
		this.parsingUsersFinish = parsingUsersFinish;
	}

	public boolean isStopLoading() {
		return stopLoading;
	}

	public void setStopLoading(boolean stopLoading) {
		this.stopLoading = stopLoading;
	}

	public ArrayList<RoomCategory> getListInitialRoomsCategories() {
		return listInitialRoomsCategories;
	}

	public void setListInitialRoomsCategories() {

		this.listInitialRoomsCategories.add(new RoomCategory(1, "Salon", "/"+Parametres.projectName+"/images/rooms/salon.png"));
		this.listInitialRoomsCategories.add(new RoomCategory(2, "Salle de bains", "/"+Parametres.projectName+"/images/rooms/salle_de_bains.png"));
		this.listInitialRoomsCategories.add(new RoomCategory(3, "Garage", "/"+Parametres.projectName+"/images/rooms/garage.png"));
		this.listInitialRoomsCategories.add(new RoomCategory(4, "Facade", "/"+Parametres.projectName+"/images/rooms/facade_maison.png"));
		this.listInitialRoomsCategories.add(new RoomCategory(5, "Cuisine", "/"+Parametres.projectName+"/images/rooms/cuisine.png"));
		this.listInitialRoomsCategories.add(new RoomCategory(6, "Chambre enfant", "/"+Parametres.projectName+"/images/rooms/chambre_enfant.png"));
		this.listInitialRoomsCategories.add(new RoomCategory(7, "Chambre à coucher", "/"+Parametres.projectName+"/images/rooms/chambre.png"));
	}

	public ArrayList<EquipementCategory> getListInitialEquipementCategories() {
		return listInitialEquipementCategories;
	}

	public void setListInitialEquipementCategories() {
		this.listInitialEquipementCategories.add(new EquipementCategory(1, "Lamps", "/"+Parametres.projectName+"/images/equipements/lamps.png"));
		this.listInitialEquipementCategories.add(new EquipementCategory(2, "Lamps 2", "/"+Parametres.projectName+"/images/equipements/lamps2.png"));
		this.listInitialEquipementCategories.add(new EquipementCategory(3, "Port", "/"+Parametres.projectName+"/images/equipements/port.png"));
		this.listInitialEquipementCategories.add(new EquipementCategory(4, "tv", "/"+Parametres.projectName+"/images/equipements/tv.png"));
	}

}
