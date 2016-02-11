package com.example.louisccc.tripal.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.louisccc.tripal.utility.DateHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {
	public static final String TAG = "dbadapter";

	public static final String DATABASE_NAME = "tripal.sqlite";

	public static final int DATABASE_VERSION = 5;

	public static final String DATABASE_TABLE_RECORD = "record";
	public static final String RECORD_KEY_LOCALID = "local_id";
	public static final String RECORD_KEY_CLOUDID = "cloud_id";
	public static final String RECORD_KEY_ACCOUNTID = "local_account_id";
	public static final String RECORD_KEY_ACCOUNTDSTID = "local_account_dst_id";
	public static final String RECORD_KEY_CATEGORYID = "local_category_id";
	public static final String RECORD_KEY_TYPE = "type";
	public static final String RECORD_KEY_AMOUNT = "amount";
	public static final String RECORD_KEY_DESC = "desc";
	public static final String RECORD_KEY_DATE = "date";
	public static final String RECORD_KEY_TIMESTAMP = "timestamp";
	public static final String RECORD_KEY_AMOUNTDST = "amount_dst";
	public static final String RECORD_KEY_FEE = "fee";
	public static final String RECORD_KEY_NEEDSYNC = "need_sync";

	public static final String DATABASE_TABLE_RECORD_CREATE = "create table "
			+ DATABASE_TABLE_RECORD + " ("
			+ RECORD_KEY_LOCALID + " integer not null primary key autoincrement, "
			+ RECORD_KEY_CLOUDID + " integer default 0, "
			+ RECORD_KEY_ACCOUNTID + " integer, "
			+ RECORD_KEY_ACCOUNTDSTID + " integer, "
			+ RECORD_KEY_TYPE + " integer, "
			+ RECORD_KEY_CATEGORYID + " integer, "
			+ RECORD_KEY_DESC + " text, "
			+ RECORD_KEY_AMOUNT + " real, "
			+ RECORD_KEY_AMOUNTDST + " real, "
			+ RECORD_KEY_FEE + " real, "
			+ RECORD_KEY_DATE + " date, "
			+ RECORD_KEY_NEEDSYNC + " integer default 0,"
			+ RECORD_KEY_TIMESTAMP + " timestamp default current_timestamp"
			+ ");";

	public static final String DATABASE_TABLE_CATEGORY = "category";
	public static final String CATEGORY_KEY_LOCALID = "local_id";
	public static final String CATEGORY_KEY_NAME = "name";
	public static final String CATEGORY_KEY_TYPE = "type";
	public static final String CATEGORY_KEY_PARENTID = "parent";
	public static final String CATEGORY_KEY_RANK = "rank";
	public static final String CATEGORY_KEY_CLOUDID = "cloud_id";
	public static final String CATEGORY_KEY_TIMESTAMP = "timestamp";
	public static final String CATEGORY_KEY_NEEDSYNC = "need_sync";

	public static final String DATABASE_TABLE_CATEGORY_CREATE = "create table "
			+ DATABASE_TABLE_CATEGORY + " ("
			+ CATEGORY_KEY_LOCALID + " integer not null primary key autoincrement, "
			+ CATEGORY_KEY_CLOUDID + " integer default 0, "
			+ CATEGORY_KEY_PARENTID + " integer, "
			+ CATEGORY_KEY_RANK + " integer, "
			+ CATEGORY_KEY_TYPE + " integer, "
			+ CATEGORY_KEY_NAME + " text, "
			+ CATEGORY_KEY_TIMESTAMP + " timestamp default current_timestamp, "
			+ CATEGORY_KEY_NEEDSYNC + " integer default 0 " + ");";

	public static final String DATABASE_TABLE_ACCOUNT = "account";

	public static final String ACCOUNT_KEY_LOCALID = "local_id";
	public static final String ACCOUNT_KEY_CLOUDID = "cloud_id";
	public static final String ACCOUNT_KEY_NAME = "name";
	public static final String ACCOUNT_KEY_INITBALANCE = "init";
	public static final String ACCOUNT_KEY_REMAIN = "remain";
	public static final String ACCOUNT_KEY_CURRENCY = "currency";
	public static final String ACCOUNT_KEY_TYPE = "type";
	public static final String ACCOUNT_KEY_TIMESTAMP = "timestamp";
	public static final String ACCOUNT_KEY_CUSTOMCURRENCYNAME = "custom_name";
	public static final String ACCOUNT_KEY_RATE = "rate";
	public static final String ACCOUNT_KEY_DISABLED = "disabled";
	public static final String ACCOUNT_KEY_NEEDSYNC = "need_sync";
	public static final String ACCOUNT_KEY_ORDER = "account_order";

	public static final String DATABASE_TABLE_ACCOUNT_CREATE = "create table "
			+ DATABASE_TABLE_ACCOUNT + " ("
			+ ACCOUNT_KEY_LOCALID + " integer not null primary key autoincrement, "
			+ ACCOUNT_KEY_CLOUDID + " integer default 0, "
			+ ACCOUNT_KEY_TYPE + " integer, "
			+ ACCOUNT_KEY_DISABLED + " integer default 0, "
			+ ACCOUNT_KEY_INITBALANCE + " real, "
			+ ACCOUNT_KEY_REMAIN + " real, "
			+ ACCOUNT_KEY_RATE + " real, "
			+ ACCOUNT_KEY_NAME + " text, "
			+ ACCOUNT_KEY_CURRENCY + " text, "
			+ ACCOUNT_KEY_CUSTOMCURRENCYNAME + " text, "
			+ ACCOUNT_KEY_NEEDSYNC + " integer default 0, "
			+ ACCOUNT_KEY_TIMESTAMP + " timestamp default current_timestamp, "
			+ ACCOUNT_KEY_ORDER + " integer default 0" + ");";

	public static final String DATABASE_TABLE_DELETE = "deleted";

	public static final String DELETE_KEY_ID = "deleted_index";
	public static final String DELETE_KEY_TABLENAME = "table_name";
	public static final String DELETE_KEY_ROWID = "cloud_row_id";
	public static final String DELETE_KEY_TIMESTAMP = "timestamp";

	public static final String DATABASE_TABLE_DELETE_CREATE = "create table "
			+ DATABASE_TABLE_DELETE + " ("
			+ DELETE_KEY_ID + " integer not null primary key autoincrement, "
			+ DELETE_KEY_TABLENAME + " text not null, "
			+ DELETE_KEY_ROWID + " integer not null, "
			+ DELETE_KEY_TIMESTAMP + " timestamp default current_timestamp " + ");";

	public static final String DATABASE_TABLE_CURRENCY = "currency";

	public static final String CURRENCY_KEY_FROM = "currency_from";
	public static final String CURRENCY_KEY_TO = "currency_to";
	public static final String CURRENCY_KEY_RATE = "rate";
	public static final String CURRENCY_KEY_TIMESTAMP = "timestamp";

	public static final String DATABASE_TABLE_CURRENCY_CREATE = "create table "
			+ DATABASE_TABLE_CURRENCY + " ("
			+ CURRENCY_KEY_FROM + " text not null, "
			+ CURRENCY_KEY_TO + " text not null, "
			+ CURRENCY_KEY_RATE + " real, "
			+ CURRENCY_KEY_TIMESTAMP + " timestamp default current_timestamp,"
			+ "primary key (" + CURRENCY_KEY_FROM + ", " + CURRENCY_KEY_TO + ")" + ");";

	private final Context mContext;
	private DatabaseHelper mDBHelper;
	private SQLiteDatabase mDB;

	public DBManager(Context ctx) {
		this.mContext = ctx;
		mDBHelper = new DatabaseHelper(mContext);
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
//			db.execSQL(DATABASE_TABLE_RECORD_CREATE);
//			db.execSQL(DATABASE_TABLE_CATEGORY_CREATE);
//			db.execSQL(DATABASE_TABLE_ACCOUNT_CREATE);
//			db.execSQL(DATABASE_TABLE_CURRENCY_CREATE);
//			db.execSQL(DATABASE_TABLE_DELETE_CREATE);

			db.execSQL(TriTrip.TRIPS_CREATE);
			db.execSQL(TriFriend.FRIENDS_CREATE);

			Log.w(TAG, "The tables were created: "
//					+ DATABASE_TABLE_RECORD_CREATE + ", "
//					+ DATABASE_TABLE_CATEGORY_CREATE + ", "
//					+ DATABASE_TABLE_ACCOUNT_CREATE + ", "
//					+ DATABASE_TABLE_CURRENCY_CREATE + ", "
//					+ DATABASE_TABLE_DELETE_CREATE + ", "
					+ TriTrip.TRIPS_CREATE + ", "
					+ TriFriend.FRIENDS_CREATE + ", version=" + db.getVersion()
			); // trace warning
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists " + DATABASE_TABLE_ACCOUNT);
			db.execSQL("drop table if exists " + DATABASE_TABLE_RECORD);
			db.execSQL("drop table if exists " + DATABASE_TABLE_CATEGORY);
			db.execSQL("drop table if exists " + DATABASE_TABLE_DELETE);
			db.execSQL("drop table if exists " + DATABASE_TABLE_CURRENCY);

			db.execSQL("drop table if exists " + TriTrip.DATABASE_TABLE_NAME);
			db.execSQL("drop table if exists " + TriFriend.DATABASE_TABLE_NAME);
			onCreate(db);
		}

	}

	public void deleteAllTable(){
		mDB.delete(TriTrip.DATABASE_TABLE_NAME, null, null);
		mDB.delete(TriFriend.DATABASE_TABLE_NAME, null, null);
	}

	public void open() throws SQLException {
		mDB = mDBHelper.getWritableDatabase();
	}

	public void close() {
		mDBHelper.close();
	}

	public void exportDB() {
		File sd = Environment.getExternalStorageDirectory();
		File data = Environment.getDataDirectory();
		FileChannel source = null;
		FileChannel destination = null;
		String currentDBPath = "/data/" + "//com.example.louisccc.tripal//" + "//databases//" + DATABASE_NAME;
		String backupDBPath = DATABASE_NAME;
		File currentDB = new File(data, currentDBPath);
		File backupDB = new File(sd, backupDBPath);
		try {
			source = new FileInputStream(currentDB).getChannel();
			destination = new FileOutputStream(backupDB).getChannel();
			destination.transferFrom(source, 0, source.size());
			source.close();
			destination.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long createTrip ( final TriTrip trip ) {
		long localId = mDB.insert( TriTrip.DATABASE_TABLE_NAME, null, trip.getContentValues() );
		Log.i(TAG, "trip created at " + localId );
		return localId;
	}

	public long updateTrip ( final TriTrip trip ) {
		return mDB.update(TriTrip.DATABASE_TABLE_NAME,
				          trip.getContentValues(),
				   		  TriTrip.KEY_LOCALID + " = ?",
						  new String[]{Integer.toString( trip.getLocalId() )}
		 				 );
	}

	public long createFriend ( final TriFriend friend ) {
		long localId = mDB.insert( TriFriend.DATABASE_TABLE_NAME, null, friend.getContentValues() );
		Log.i(TAG, "friend created at " + localId );
		return localId;
	}

	public long updateFriend ( final TriFriend friend ) {
		return mDB.update(TriFriend.DATABASE_TABLE_NAME,
				       	  friend.getContentValues(),
				  	 	  TriFriend.KEY_LOCALID + " = ?",
						  new String[]{Integer.toString(friend.getLocalId() )}
						 );
	}


}
