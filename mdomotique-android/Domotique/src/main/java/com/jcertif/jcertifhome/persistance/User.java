package com.jcertif.jcertifhome.persistance;

public class User {
	
	private int Id;      
	private String Nom;
	private String Prenom;
	private String Login;
	private String Password;
	
	private User(){
		Id = 0;
		Nom = "";
		Prenom = "";
		Login = "";
		Password = "";
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public String getLogin() {
		return Login;
	}
	public void setLogin(String Login) {
		this.Login = Login;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String Password) {
		this.Password = Password;
	}

}
