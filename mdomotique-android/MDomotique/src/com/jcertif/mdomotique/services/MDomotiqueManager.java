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
	
	private ArrayList<Equipement> listEquipements = new ArrayList<Equipement>();
	
	private ArrayList<EquipementCategory> listEquipementCategories = new ArrayList<EquipementCategory>();
	
	private User userConnected;
	
	private Room roomSelected;
	
	private boolean parsingUsersFinish = false;
	
	private boolean parsingRoomsFinish = false;
	
	private boolean parsingRoomCategoryFinish = false;
	
	private boolean parsingEquipementCategoryFinish = false;
	
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

}
