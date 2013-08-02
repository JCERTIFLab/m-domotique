package com.jcertif.mdomotique.persistance;

public class RoomCategory {
	
	private int id;
	private String name;
	private String img;
	
	public RoomCategory(){
		id = 0;
		name = "";
		img = "";
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
