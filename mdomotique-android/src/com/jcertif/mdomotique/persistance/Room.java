package com.jcertif.mdomotique.persistance;

public class Room {
	
	private int id;
	private String name;
	private String typeId;
	private RoomCategory roomCategory;
	
	public Room(){
		id = 0;
		name = "";
		typeId = "";
		roomCategory = new RoomCategory();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public RoomCategory getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(RoomCategory roomCategory) {
		this.roomCategory = roomCategory;
	}

}
