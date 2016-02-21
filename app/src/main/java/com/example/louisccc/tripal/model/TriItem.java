package com.example.louisccc.tripal.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.louisccc.tripal.utility.DateHelper;

import java.util.Date;

/**
 * Created by louisccc on 2/11/16.
 */
public class TriItem implements Parcelable {

    public static final String DATABASE_TABLE_NAME = "items";

    public static final String KEY_LOCALID = "item_local_id";
    public static final String KEY_CLOUDID = "item_cloud_id";
    public static final String KEY_NAME = "item_name";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_OWNERID = "owner_friend_id";
    public static final String KEY_TRIPID = "trip_id";
    public static final String KEY_CATEGORYID = "category_id";
    public static final String KEY_NOTE = "note";
    public static final String KEY_DATE = "date";
    public static final String KEY_CURR_TIMESTAMP = "created_modified_timestamp";
    public static final String KEY_TIMESTAMP = "last_modified_timestamp";
    public static final String KEY_RESOLVED = "resolved";
    public static final String KEY_NEEDSYNC = "need_sync";
    public static final String KEY_ORDER = "item_order";

    public static final String ITEMS_CREATE =
            "create table " + DATABASE_TABLE_NAME
                    + " ("
                    + KEY_LOCALID         + " integer not null primary key autoincrement, "
                    + KEY_CLOUDID         + " integer default 0, "
                    + KEY_NAME            + " text not null, "
                    + KEY_AMOUNT          + " real, "
                    + KEY_OWNERID         + " integer, "
                    + KEY_TRIPID          + " integer, "
                    + KEY_CATEGORYID      + " integer, "
                    + KEY_NOTE            + " text, "
                    + KEY_DATE            + " date, "
                    + KEY_CURR_TIMESTAMP  + " timestamp, "
                    + KEY_TIMESTAMP       + " timestamp default current_timestamp, "
                    + KEY_RESOLVED        + " integer default 0, "
                    + KEY_NEEDSYNC        + " integer default 1, "
                    + KEY_ORDER           + " integer default 0  "
                    + ");";

    /* members */
    private int mLocal_id;
    private int mCloud_id;
    private String mName;
    private double mAmount;
    private int mOwner_id;
    private int mTrip_id;
    private int mCategory_id;
    private String mNote;
    private Date mDate;
    private long mCurr_timestamp;
    private long mLast_modified_timestamp;
    private boolean mResolved;
    private boolean mNeedSync;
    private int mOrder;

    protected TriItem(Parcel in) {
        readFromParcel(in);
    }

    public TriItem(){
        this.mLocal_id = 0;
        this.mCloud_id = 0;
        this.mName = "default trip item";
        this.mAmount = 0;
        this.mOwner_id = 0;
        this.mTrip_id = 0;
        this.mCategory_id = 0;
        this.mNote = "";
        this.mDate = new Date();
        this.mCurr_timestamp = System.currentTimeMillis();
        this.mLast_modified_timestamp = System.currentTimeMillis();
        this.mNeedSync = true;
        this.mResolved = false;
        this.mOrder = 0;
    }

    public TriItem(String mName, double mAmount,
                   int mOwner_id, int mTrip_id, int mCategory_id,
                   String mNote, Date mDate){
        this.mLocal_id = 0;
        this.mCloud_id = 0;
        this.mName = mName;
        this.mAmount = mAmount;
        this.mOwner_id = mOwner_id;
        this.mTrip_id = mTrip_id;
        this.mCategory_id = mCategory_id;
        this.mNote = mNote;
        this.mDate = mDate;
        this.mCurr_timestamp = mDate.getTime();
        this.mLast_modified_timestamp = System.currentTimeMillis();
        this.mNeedSync = true;
        this.mResolved = false;
        this.mOrder = 0;
    }

    public TriItem(Cursor cursor) {
        this.mLocal_id = cursor.getInt( cursor.getColumnIndex(KEY_LOCALID) );
        this.mCloud_id = cursor.getInt( cursor.getColumnIndex(KEY_CLOUDID) );
        this.mName = cursor.getString( cursor.getColumnIndex(KEY_NAME) );
        this.mAmount = cursor.getDouble( cursor.getColumnIndex(KEY_AMOUNT) );
        this.mOwner_id = cursor.getInt( cursor.getColumnIndex(KEY_OWNERID) );
        this.mTrip_id = cursor.getInt( cursor.getColumnIndex(KEY_TRIPID) );
        this.mCategory_id = cursor.getInt( cursor.getColumnIndex(KEY_CATEGORYID) );
        this.mNote = cursor.getString( cursor.getColumnIndex(KEY_NOTE) );
        this.mDate = DateHelper.getDate( cursor.getString( cursor.getColumnIndex(KEY_DATE) ) );
        this.mCurr_timestamp = cursor.getLong( cursor.getColumnIndex(KEY_CURR_TIMESTAMP) );
        this.mLast_modified_timestamp = cursor.getLong( cursor.getColumnIndex(KEY_TIMESTAMP) );
        this.mResolved = ( cursor.getInt( cursor.getColumnIndex(KEY_RESOLVED) ) == 1 );
        this.mNeedSync = ( cursor.getInt( cursor.getColumnIndex(KEY_NEEDSYNC) ) == 1 );
        this.mOrder = cursor.getInt( cursor.getColumnIndex(KEY_ORDER) );
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        // local id is assigned.
        contentValues.put( KEY_CLOUDID       , mCloud_id );
        contentValues.put( KEY_NAME          , mName );
        contentValues.put( KEY_AMOUNT        , mAmount );
        contentValues.put( KEY_OWNERID       , mOwner_id );
        contentValues.put( KEY_TRIPID        , mTrip_id );
        contentValues.put( KEY_CATEGORYID    , mCategory_id );
        contentValues.put( KEY_NOTE          , mNote );
        contentValues.put( KEY_DATE          , DateHelper.getDateString(mDate) );
        contentValues.put( KEY_CURR_TIMESTAMP, mCurr_timestamp );
        contentValues.put( KEY_TIMESTAMP     , mLast_modified_timestamp );
        contentValues.put( KEY_RESOLVED      , mResolved );
        contentValues.put( KEY_NEEDSYNC      , mNeedSync );
        contentValues.put( KEY_ORDER         , mOrder );

        return contentValues;

    }

    public int getLocalId() {
        return mLocal_id;
    }

    public void setlocalId(long localId) {
        this.mLocal_id = (int) localId;
    }

    public int getTripId() {
        return mTrip_id;
    }

    public double getAmount() {
        return mAmount;
    }

    public String getName() {
        return mName;
    }

    public String getTimeStamp() {
        return DateHelper.getDateString(mDate);
    }

    public String getNote() {
        return mNote;
    }

    public int getOwner() {
        return mOwner_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mLocal_id);
        dest.writeInt(mCloud_id);
        dest.writeString(mName);
        dest.writeDouble(mAmount);
        dest.writeInt(mOwner_id);
        dest.writeInt(mTrip_id);
        dest.writeInt(mCategory_id);
        dest.writeString(mNote);
        dest.writeString(DateHelper.getDateString(mDate));
        dest.writeLong(mCurr_timestamp);
        dest.writeLong(mLast_modified_timestamp);
        dest.writeByte( (byte) (mResolved ? 1 : 0) );
        dest.writeByte( (byte) (mNeedSync ? 1 : 0) );
        dest.writeInt(mOrder);
    }

    public void readFromParcel ( Parcel in ) {
        mLocal_id = in.readInt();
        mCloud_id = in.readInt();
        mName = in.readString();
        mAmount = in.readDouble();
        mOwner_id = in.readInt();
        mTrip_id = in.readInt();
        mCategory_id = in.readInt();
        mNote = in.readString();
        mDate = DateHelper.getDate(in.readString());
        mCurr_timestamp = in.readLong();
        mLast_modified_timestamp = in.readLong();
        mResolved = in.readByte() != 0;
        mNeedSync = in.readByte() != 0;
        mOrder = in.readInt();
    }

    public static final Parcelable.Creator<TriItem> CREATOR = new Parcelable.Creator<TriItem>() {
        @Override
        public TriItem createFromParcel(Parcel in) {
            return new TriItem(in);
        }

        @Override
        public TriItem[] newArray(int size) {
            return new TriItem[size];
        }
    };

    public void setName(String name) {
        this.mName = name;
    }

    public void setAmount(Double amount) {
        this.mAmount = amount;
    }

    public void setOwnerId(int ownerId) {
        this.mOwner_id = ownerId;
    }

    public void setTripId(int tripId) {
        this.mTrip_id = tripId;
    }

    public void setNote(String note) {
        this.mNote = note;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }
}
