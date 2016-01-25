package com.example.louisccc.tripal.model;

import android.database.Cursor;

import com.example.louisccc.tripal.DBManager;
import org.json.JSONObject;


public class TriCurrency {
	private String mFrom;
	private String mTo;
	private Double mRate;
	private int mTimestamp; 
	
	public TriCurrency(String from, String to, double rate, int timestamp){
		this.mFrom = from;
		this.mTo = to;
		this.mRate = rate;
		this.mTimestamp = timestamp;
	}
	public TriCurrency(String from, String to, double rate){
		this(from, to, rate, getDefaultTimestamp());
	}
	
	public TriCurrency(JSONObject object){
		try {
			this.mFrom = object.getString("from");
			this.mTo = object.getString("to");
			this.mRate = object.getDouble("rate");
			this.mTimestamp = getDefaultTimestamp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TriCurrency(Cursor c){
		this.mFrom = c.getString(c.getColumnIndex(DBManager.CURRENCY_KEY_FROM));
		this.mTo = c.getString(c.getColumnIndex(DBManager.CURRENCY_KEY_TO));
		this.mRate = c.getDouble(c.getColumnIndex(DBManager.CURRENCY_KEY_RATE));
		this.mTimestamp = c.getInt(c.getColumnIndex(DBManager.CURRENCY_KEY_TIMESTAMP));
	}
	
	private static int getDefaultTimestamp(){
		return (int)( System.currentTimeMillis() / 1000L);
	}
}
