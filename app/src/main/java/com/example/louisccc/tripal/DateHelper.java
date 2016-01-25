package com.example.louisccc.tripal;

import android.app.ActionBar;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	
	public static void embeddedTabs(Object actionBar, Boolean embed_tabs) {
		try {
			if (actionBar instanceof ActionBar) {
				// ICS and forward
				try {
					Field actionBarField = actionBar.getClass()
							.getDeclaredField("mActionBar");
					actionBarField.setAccessible(true);
					actionBar = actionBarField.get(actionBar);
				} catch (Exception e) {
					Log.e("", "Error enabling embedded tabs", e);
				}
			}
			Method setHasEmbeddedTabsMethod = actionBar.getClass().getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
			setHasEmbeddedTabsMethod.setAccessible(true);
			setHasEmbeddedTabsMethod.invoke(actionBar, embed_tabs);
		} catch (Exception e) {
			Log.e("", "Error marking actionbar embedded", e);
		}
	}
	
	public static Date getDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}
	public static Date getDate(String time_string){
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(time_string);
			//timestamp = new Timestamp(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Calendar getCalendarDay(int diff){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, diff);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	public static String getDate(int diff){
		Calendar c = getCalendarDay(diff);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	public static String getLocalString(Context mContext, int diff) {
		return SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT,
				mContext.getResources().getConfiguration().locale).format(
				getCalendarDay(diff).getTime());
	}
	public static String getLocalString(Context mContext, Date date) {
		return SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT,
				mContext.getResources().getConfiguration().locale).format(
				date);
	}
	public static String getDateString(int year, int month, int day) {
		Date d = getDate(year, month, day);
		return getDateString(d);
	}
	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
