package com.example.louisccc.tripal.model;

import android.provider.Settings.Secure;
import android.util.Log;

import com.example.louisccc.tripal.MySSLSocketFactory;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;


public class TriClient {
	private static final String TAG = "Banck_client";
	private static final String TEST_ACCOUNT="test@benck.tw";
	private static final String TEST_PASSWORD = "test";
	
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_LOCALE = "locale";
	private static final String KEY_DEVICE_ID = "device_id";
	private static final String KEY_SIGNUP = "signup";
	private static final String KEY_PLATFORM = "platform";
	
	private static final String KEY_FBTOKEN = "fb_token";
	private String mVersion = "1.0";
	
	private static final String API_HOST_SERVER = "https://banck.tw/api/";
	private static final String API_HOST_PORT = "";
	
	
    private HttpClient mHttpClient;
	public TriClient(){
        mHttpClient = MySSLSocketFactory.createMyHttpClient();
	}
	
	/*
	 * 
	 * Get Token from normal way (enter account and password)
	 * 
	 */
	public JSONObject getToken(String account, String password, int signup) throws Exception {
		HttpPost post = new HttpPost(API_HOST_SERVER + API_HOST_PORT + "get_token");
		post.setHeader("Accept-EncoNameValuePair", "gzip");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(KEY_EMAIL, account));
		nameValuePairs.add(new BasicNameValuePair(KEY_PASSWORD, password));
		nameValuePairs.add(new BasicNameValuePair(KEY_LOCALE, Locale.getDefault().toString()));
		nameValuePairs.add(new BasicNameValuePair(KEY_DEVICE_ID, Secure.ANDROID_ID));
		nameValuePairs.add(new BasicNameValuePair(KEY_SIGNUP, Integer.toString(signup)));
		nameValuePairs.add(new BasicNameValuePair(KEY_PLATFORM, "android"));
		
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs,	HTTP.UTF_8));
		
		HttpResponse response = mHttpClient.execute(post);
		JSONObject json = parseResponseToJson(response);
		return json;
	}


    /*
	 *
	 * Get Token from fb login way (just encap fb token)
	 *
	 */
	public JSONObject getToken(String fb_token) throws Exception {
		HttpPost post = new HttpPost(API_HOST_SERVER + API_HOST_PORT + "get_token");
		post.setHeader("Accept-EncoNameValuePair", "gzip");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(KEY_FBTOKEN, fb_token));
		nameValuePairs.add(new BasicNameValuePair(KEY_LOCALE, Locale.getDefault().toString()));
		nameValuePairs.add(new BasicNameValuePair(KEY_DEVICE_ID, Secure.ANDROID_ID));
		nameValuePairs.add(new BasicNameValuePair(KEY_PLATFORM, "android"));
		
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs,	HTTP.UTF_8));
		
		HttpResponse response = mHttpClient.execute(post);
		JSONObject json = parseResponseToJson(response);
		return json;
	}

    /*

    getData function will get/put data to banck's api server

     */
	public JSONObject getData(String token, long timestamp) throws Exception {
		HttpPost post = new HttpPost(API_HOST_SERVER + API_HOST_PORT + "sync");
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("access_token", token));
		nameValuePairs.add(new BasicNameValuePair("last_sync_timestamp", ""+timestamp));
		nameValuePairs.add(new BasicNameValuePair("upload_sync_json_str", "{}"));
		nameValuePairs.add(new BasicNameValuePair("need_sync_currency", "0"));
		nameValuePairs.add(new BasicNameValuePair("from_currency", "TWD"));
		nameValuePairs.add(new BasicNameValuePair("v", "1"));
		
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs,	HTTP.UTF_8));
		
		HttpResponse response = mHttpClient.execute(post);
		JSONObject json = parseResponseToJson(response);
		return json;
	}

    public JSONObject getData(String token, long timestamp, String upload_sync_string) throws Exception {
        HttpPost post = new HttpPost(API_HOST_SERVER + API_HOST_PORT + "sync");

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("access_token", token));
        nameValuePairs.add(new BasicNameValuePair("last_sync_timestamp", ""+timestamp));
        nameValuePairs.add(new BasicNameValuePair("upload_sync_json_str", upload_sync_string));
        nameValuePairs.add(new BasicNameValuePair("need_sync_currency", "0"));
        nameValuePairs.add(new BasicNameValuePair("from_currency", "TWD"));
        nameValuePairs.add(new BasicNameValuePair("v", "1"));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

		Log.d(TAG, "sync request: " + nameValuePairs.toString() + " sent.");

        HttpResponse response = mHttpClient.execute(post);
        JSONObject json = parseResponseToJson(response);
        return json;
    }

    /*
       tools functions
    */
	private String streamToString(InputStream is) {
		if (is != null) {
			Writer w = new StringWriter();
			char[] buffer = new char[1024];
			
			Reader r;
			try {
				r = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8192);
				int n;
				while((n = r.read(buffer)) != -1) {
					w.write(buffer,0,n);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return w.toString();
		} else {
			return null;
		}
	}
	
	private JSONObject parseResponseToJson(HttpResponse response) throws IOException, JSONException {
		String result = streamToString(getInputStream(response));
		Log.d(TAG, "Parsing: " + result);
		JSONObject json = new JSONObject(result);
		return json;
	}
	private InputStream getInputStream(HttpResponse response) {
		InputStream is = null;
		try {
			 is = response.getEntity().getContent();
			
			 Header ce = response.getFirstHeader("Content-Encoding");
			 if (ce != null && ce.getValue().equalsIgnoreCase("gzip")){
			 	is = new GZIPInputStream(is);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
}
