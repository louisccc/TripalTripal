package com.example.louisccc.tripal.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

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
	public static final int DATABASE_VERSION = 10;
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
			db.execSQL(TriTrip.TRIPS_CREATE);
			db.execSQL(TriFriend.FRIENDS_CREATE);
			db.execSQL(TriParticipation.TRIPS_PARTICIPATION_CREATE);
			db.execSQL(TriItem.ITEMS_CREATE);
			db.execSQL(TriDept.DEPTS_CREATE);

			Log.w(TAG, "The tables were created: "
					+ TriTrip.TRIPS_CREATE + ", "
					+ TriFriend.FRIENDS_CREATE + ", "
					+ TriParticipation.TRIPS_PARTICIPATION_CREATE + ", "
					+ TriItem.ITEMS_CREATE + ", "
					+ TriDept.DEPTS_CREATE + ", version=" + db.getVersion()
			); // trace warning
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			db.execSQL("drop table if exists " + TriTrip.DATABASE_TABLE_NAME);
			db.execSQL("drop table if exists " + TriFriend.DATABASE_TABLE_NAME);
			db.execSQL("drop table if exists " + TriParticipation.DATABASE_TABLE_NAME);
			db.execSQL("drop table if exists " + TriItem.DATABASE_TABLE_NAME);
			db.execSQL("drop table if exists " + TriDept.DATABASE_TABLE_NAME);
			onCreate(db);
		}

	}

	public void deleteAllTable(){
		mDB.delete(TriTrip.DATABASE_TABLE_NAME, null, null);
		mDB.delete(TriFriend.DATABASE_TABLE_NAME, null, null);
		mDB.delete(TriParticipation.DATABASE_TABLE_NAME, null, null);
		mDB.delete(TriItem.DATABASE_TABLE_NAME, null, null);
		mDB.delete(TriDept.DATABASE_TABLE_NAME, null, null);
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
	public ArrayList<TriTrip> getTrips() {
		ArrayList<TriTrip> trips = new ArrayList<TriTrip>();
		String[] columns = new String[] {
				TriTrip.KEY_LOCALID,
				TriTrip.KEY_CLOUDID,
				TriTrip.KEY_NAME,
				TriTrip.KEY_INITBALANCE,
				TriTrip.KEY_BALANCE,
				TriTrip.KEY_CATEGORYID,
				TriTrip.KEY_TIMESTAMPFROM,
				TriTrip.KEY_TIMESTAMPTO,
				TriTrip.KEY_TIMESTAMP,
				TriTrip.KEY_NEEDSYNC,
				TriTrip.KEY_ORDER
		};
		Cursor cursor = mDB.query(TriTrip.DATABASE_TABLE_NAME, columns, null, null, null, null, null);
		if ( cursor != null && cursor.moveToFirst() ) {
			do {
				TriTrip item = new TriTrip( cursor );
				trips.add(item);
			} while ( cursor.moveToNext() );
			cursor.close();
		}
		return trips;
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

	public ArrayList<TriFriend> getFriends() {
		ArrayList<TriFriend> friends = new ArrayList<TriFriend>();
		String[] columns = new String[] {
				TriFriend.KEY_LOCALID,
				TriFriend.KEY_CLOUDID,
				TriFriend.KEY_NAME,
				TriFriend.KEY_FB,
				TriFriend.KEY_EMAIL,
				TriFriend.KEY_PHONE,
				TriFriend.KEY_TIMESTAMP,
				TriFriend.KEY_NEEDSYNC,
				TriFriend.KEY_ORDER
		};
		Cursor cursor = mDB.query(TriFriend.DATABASE_TABLE_NAME, columns, null, null, null, null, null);
		if ( cursor != null && cursor.moveToFirst() ) {
			do {
				TriFriend friend = new TriFriend( cursor );
				friends.add(friend);
			} while ( cursor.moveToNext() );
			cursor.close();
		}
		return friends;
	}

	public long createParticipation ( final TriParticipation part ) {
		long localId = mDB.insert( TriParticipation.DATABASE_TABLE_NAME, null, part.getContentValues() );
		Log.i(TAG, "participation created at " + localId );
		return localId;
	}

	public long updateParticipation ( final TriParticipation part ) {
		return mDB.update(TriParticipation.DATABASE_TABLE_NAME,
				part.getContentValues(),
				TriParticipation.KEY_LOCALID + " = ?",
				new String[]{Integer.toString(part.getLocalId() )}
		);
	}

	public ArrayList<TriParticipation> getParticipations() {
		ArrayList<TriParticipation> parts = new ArrayList<TriParticipation>();
		String[] columns = new String[] {
				TriParticipation.KEY_LOCALID,
				TriParticipation.KEY_CLOUDID,
				TriParticipation.KEY_TRIPID,
				TriParticipation.KEY_USERID,
				TriParticipation.KEY_TIMESTAMP,
				TriParticipation.KEY_NEEDSYNC
		};
		Cursor cursor = mDB.query(TriParticipation.DATABASE_TABLE_NAME, columns, null, null, null, null, null);
		if ( cursor != null && cursor.moveToFirst() ) {
			do {
				TriParticipation part = new TriParticipation( cursor, mContext );
				parts.add(part);
			} while ( cursor.moveToNext() );
			cursor.close();
		}
		return parts;
	}

	public long createItem ( final TriItem item ) {
		long localId = mDB.insert( TriItem.DATABASE_TABLE_NAME, null, item.getContentValues() );
		Log.i(TAG, "item created at " + localId );
		return localId;
	}

	public long updateItem ( final TriItem item ) {
		return mDB.update(TriItem.DATABASE_TABLE_NAME,
				item.getContentValues(),
				TriItem.KEY_LOCALID + " = ?",
				new String[]{Integer.toString(item.getLocalId() )}
		);
	}

	public ArrayList<TriItem> getItems() {
		ArrayList<TriItem> items = new ArrayList<TriItem>();
		String[] columns = new String[] {
				TriItem.KEY_LOCALID,
				TriItem.KEY_CLOUDID,
				TriItem.KEY_NAME,
				TriItem.KEY_AMOUNT,
				TriItem.KEY_OWNERID,
				TriItem.KEY_TRIPID,
				TriItem.KEY_CATEGORYID,
				TriItem.KEY_NOTE,
				TriItem.KEY_DATE,
				TriItem.KEY_CURR_TIMESTAMP,
				TriItem.KEY_TIMESTAMP,
				TriItem.KEY_RESOLVED,
				TriItem.KEY_NEEDSYNC,
				TriItem.KEY_ORDER
		};
		Cursor cursor = mDB.query(TriItem.DATABASE_TABLE_NAME, columns, null, null, null, null, null);
		if ( cursor != null && cursor.moveToFirst() ) {
			do {
				TriItem item = new TriItem( cursor );
				items.add(item);
			} while ( cursor.moveToNext() );
			cursor.close();
		}
		return items;
	}

	public long createDept ( final TriDept dept ) {
		long localId = mDB.insert( TriDept.DATABASE_TABLE_NAME, null, dept.getContentValues() );
		Log.i(TAG, "dept relation created at " + localId );
		return localId;
	}

	public long updateDept ( final TriDept dept ) {
		return mDB.update(TriDept.DATABASE_TABLE_NAME,
				dept.getContentValues(),
				TriDept.KEY_LOCALID + " = ?",
				new String[]{Integer.toString(dept.getLocalId() )}
		);
	}

	public ArrayList<TriDept> getDepts() {
		ArrayList<TriDept> depts = new ArrayList<TriDept>();
		String[] columns = new String[] {
				TriDept.KEY_LOCALID,
				TriDept.KEY_CLOUDID,
				TriDept.KEY_ITEMID,
				TriDept.KEY_USERID,
				TriDept.KEY_PROPOTION,
				TriDept.KEY_PAID,
				TriDept.KEY_TIMESTAMP,
				TriDept.KEY_NEEDSYNC,
		};
		Cursor cursor = mDB.query(TriDept.DATABASE_TABLE_NAME, columns, null, null, null, null, null);
		if ( cursor != null && cursor.moveToFirst() ) {
			do {
				TriDept dept = new TriDept( cursor );
				depts.add(dept);
			} while ( cursor.moveToNext() );
			cursor.close();
		}
		return depts;
	}

}
