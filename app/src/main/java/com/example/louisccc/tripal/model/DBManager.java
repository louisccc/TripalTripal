package com.example.louisccc.tripal.model;

import android.content.Context;
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

public class DBManager {
	public static final String TAG = "dbadapter";

	public static final String DATABASE_NAME = "tripal.sqlite";
	public static final int DATABASE_VERSION = 6;
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

			Log.w(TAG, "The tables were created: "
					+ TriTrip.TRIPS_CREATE + ", "
					+ TriFriend.FRIENDS_CREATE + ", "
					+ TriParticipation.TRIPS_PARTICIPATION_CREATE + ", version=" + db.getVersion()
			); // trace warning
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			db.execSQL("drop table if exists " + TriTrip.DATABASE_TABLE_NAME);
			db.execSQL("drop table if exists " + TriFriend.DATABASE_TABLE_NAME);
			db.execSQL("drop table if exists " + TriParticipation.DATABASE_TABLE_NAME);
			onCreate(db);
		}

	}

	public void deleteAllTable(){
		mDB.delete(TriTrip.DATABASE_TABLE_NAME, null, null);
		mDB.delete(TriFriend.DATABASE_TABLE_NAME, null, null);
		mDB.delete(TriParticipation.DATABASE_TABLE_NAME, null, null);
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

}
