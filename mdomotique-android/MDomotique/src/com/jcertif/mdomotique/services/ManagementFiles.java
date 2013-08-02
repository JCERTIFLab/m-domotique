package com.jcertif.mdomotique.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ManagementFiles {
	
	public static void writeData(String data, String fileName){
		try {
            File myFile = new File(fileName);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
        } 
        catch (Exception e) { }
	}
	
	public static String readData(String fileName){
		try {

            File myFile = new File(fileName);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow ;
            }
            myReader.close();
            return aBuffer;
        } 
        catch (Exception e){
            return "";
        }
	}
}
