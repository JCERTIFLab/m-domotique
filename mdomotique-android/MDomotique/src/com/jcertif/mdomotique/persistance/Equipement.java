package com.jcertif.mdomotique.persistance;

public class Equipement {
	
	private int id;
	private String name;
	private String description;
	private boolean state;
	private int pin;
	private int equipement_type_id;
	private int room_id;
	private EquipementCategory equipementCategory;
	
	public Equipement(){
		id = 0;
		name = "";
		description = "";
		state = false;
		pin = 0;
		equipement_type_id = 0;
		room_id = 0;
		equipementCategory = new EquipementCategory();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public EquipementCategory getEquipementCategory() {
		return equipementCategory;
	}

	public void setEquipementCategory(EquipementCategory equipementCategory) {
		this.equipementCategory = equipementCategory;
	}

	public int getEquipement_type_id() {
		return equipement_type_id;
	}

	public void setEquipement_type_id(int equipement_type_id) {
		this.equipement_type_id = equipement_type_id;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

}
