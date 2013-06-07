package com.jcertif.jcertifhome.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Requests {
	
	public static String Get(String url){
		
		InputStream content = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		try {
			HttpClient httpclient = new DefaultHttpClient();
		    HttpResponse response = httpclient.execute(new HttpGet(url));
		    content = response.getEntity().getContent();
		    br = new BufferedReader(new InputStreamReader(content));
		    String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {}

		return sb.toString();
	}
	
}
