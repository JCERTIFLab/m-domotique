package com.jcertif.jcertifhome.services;

import java.util.ArrayList;

import com.jcertif.jcertifhome.persistance.Piece;



public class JCertifManager {
	
	public int menu_Selected = 1;
	
	public String pieceSelected = "";
	
	public ArrayList<Piece> listPieces = new ArrayList<Piece>();
	
	public static JCertifManager jCertifManager;
	
	public static JCertifManager getInstance() {
		
		if (jCertifManager == null)
			jCertifManager = new JCertifManager();
		
		return jCertifManager;
	}

}
