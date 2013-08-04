package com.jcertif.mdomotique.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RESTRequets {
	
	public JSONObject doGet(String url) {

        JSONObject jsonObject = null;
	    HttpClient httpclient = new DefaultHttpClient();

	    HttpGet httpget = new HttpGet(url);
	    httpget.addHeader("accept", "application/json");
	    HttpResponse response;

	    try {
	        response = httpclient.execute(httpget);
	        HttpEntity entity = response.getEntity();

            StatusLine responseStatus = response.getStatusLine();
            int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;

            if(statusCode==200){

                if (entity != null) {

                    InputStream instream = entity.getContent();
                    String result= convertStreamToString(instream);
                    
                    Log.i("test",">>>"+result);

                    jsonObject=new JSONObject(result);
                    instream.close();
                }

            }

	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }

	    return jsonObject;
	}
	
	public String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        while ((line = reader.readLine()) != null) {
                                sb.append(line).append("\n");
                        }
                }
                finally {
                        is.close();
                }
                return sb.toString();
        } else {
                return "";
        }
	}
	
	public JSONObject doPost(String url, JSONObject c) throws ClientProtocolException, IOException {

        JSONObject jsonObject = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(c.toString());

        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");

        request.setEntity(stringEntity);
        request.addHeader("Content-Type", "application/json");
        
        HttpResponse response;
	    try {
	        response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
	        
	        StatusLine responseStatus = response.getStatusLine();
			int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;

            if(statusCode==200){

                if (entity != null) {

                    InputStream instream = entity.getContent();
                    String result= convertStreamToString(instream);

                    jsonObject=new JSONObject(result);
                    instream.close();
                }

            }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return jsonObject;
	}
	
	public JSONObject doPut(String url, JSONObject c) throws ClientProtocolException, IOException{

        JSONObject jsonObject = null;

        HttpClient httpclient = new DefaultHttpClient();
	    HttpPut request = new HttpPut(url);

        StringEntity s = new StringEntity(c.toString());
	    s.setContentEncoding("UTF-8");
	    s.setContentType("application/json");

	    request.setEntity(s);
	    request.addHeader("accept", "application/json");

        HttpResponse response;
        try {
            response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            StatusLine responseStatus = response.getStatusLine();
            int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;

            if(statusCode==200){

                if (entity != null) {

                    InputStream instream = entity.getContent();
                    String result= convertStreamToString(instream);

                    jsonObject=new JSONObject(result);
                    instream.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
	}
	
	public void doDelete(String url) throws  ClientProtocolException, IOException{
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpDelete delete = new HttpDelete(url);
    	delete.addHeader("accept", "application/json");
    	httpclient.execute(delete);
	}

}
