package com.jcertif.jcertifhome.persistance;

public class Piece {
	
	private int Id;
	private String Name;
	
	public Piece(){
		Id = 0;
		Name = "";
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
}
