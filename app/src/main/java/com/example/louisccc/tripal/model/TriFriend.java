package com.example.louisccc.tripal.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.louisccc.tripal.utility.DateHelper;

import junit.framework.Assert;

/**
 * Created by louisccc on 2/10/16.
 */
public class TriFriend implements Parcelable{

    public static final String DATABASE_TABLE_NAME = "friends";

    public static final String KEY_LOCALID = "friend_local_id";
    public static final String KEY_CLOUDID = "friend_cloud_id";
    public static final String KEY_NAME = "friend_name";
    public static final String KEY_FB = "token";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_TIMESTAMP = "last_modified_timestamp";
    public static final String KEY_NEEDSYNC = "need_sync";
    public static final String KEY_ORDER = "friend_order";

    public static final String FRIENDS_CREATE =
            "create table " + DATABASE_TABLE_NAME
                    + " ("
                    + KEY_LOCALID + " integer not null primary key autoincrement, "
                    + KEY_CLOUDID + " integer default 0, "
                    + KEY_NAME + " text not null, "
                    + KEY_FB + " text, "
                    + KEY_EMAIL + " text, "
                    + KEY_PHONE + " text, "
                    + KEY_TIMESTAMP + " timestamp default current_timestamp, "
                    + KEY_NEEDSYNC + " integer default 1, "
                    + KEY_ORDER + " integer default 0  "
                    + ");";
    /* members */
    private int mLocal_id;
    private int mCloud_id;
    private String mName;
    private String mFB_token;
    private String mEmail;
    private String mPhone;
    private long mTimestamp;
    private boolean mNeedSync;
    private int mOrder;

    protected TriFriend(Parcel in) {
        readFromParcel(in);
    }

    public TriFriend() { /* clean constructor */
        mLocal_id = 0;
        mCloud_id = 0;
        mName = "default friend name";
        mFB_token = "";
        mEmail = "";
        mPhone = "";
        mTimestamp = System.currentTimeMillis();
        mNeedSync = true;
        mOrder = 0;
    }

    public TriFriend(String name, String FB_token, String email, String phone) {
        mLocal_id = 0;
        mCloud_id = 0;
        mName = name;
        mFB_token = FB_token;
        mEmail = email;
        mPhone = phone;
        mTimestamp = System.currentTimeMillis();
        mNeedSync = true;
        mOrder = 0;
    }

    public TriFriend(Cursor cursor) {
        this.mLocal_id = cursor.getInt( cursor.getColumnIndex(KEY_LOCALID) );
        this.mCloud_id = cursor.getInt( cursor.getColumnIndex(KEY_CLOUDID) );
        this.mName = cursor.getString( cursor.getColumnIndex(KEY_NAME) );
        this.mFB_token = cursor.getString( cursor.getColumnIndex(KEY_FB) );
        this.mEmail = cursor.getString( cursor.getColumnIndex(KEY_EMAIL) );
        this.mPhone = cursor.getString( cursor.getColumnIndex(KEY_PHONE) );
        this.mTimestamp = cursor.getLong(cursor.getColumnIndex(KEY_TIMESTAMP));
        this.mNeedSync = ( cursor.getInt( cursor.getColumnIndex(KEY_NEEDSYNC) ) == 1 );
        this.mOrder = cursor.getInt( cursor.getColumnIndex(KEY_ORDER) );
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        // local id is assigned.
        contentValues.put( KEY_CLOUDID      , mCloud_id );
        contentValues.put( KEY_NAME         , mName );
        contentValues.put( KEY_FB           , mFB_token );
        contentValues.put( KEY_EMAIL        , mEmail );
        contentValues.put( KEY_PHONE        , mPhone );
        contentValues.put( KEY_TIMESTAMP    , mTimestamp );
        contentValues.put( KEY_NEEDSYNC     , mNeedSync );
        contentValues.put( KEY_ORDER        , mOrder );

        return contentValues;

    }

    public int getLocalId() {
        return mLocal_id;
    }

    public void setlocalId(long localId) {
        this.mLocal_id = (int)localId;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public double getCurrBalance(Context ctx, TriTrip mTrip) {
        Assert.assertTrue(mTrip != null);
        double curr_balance = 0;
        for (TriItem item : ((TriApplication) ctx.getApplicationContext()).getgItems() ){
            if ( item.getTripId() == mTrip.getLocalId() && this.mLocal_id == item.getOwner() ) {
                curr_balance += item.getAmount();
            }
        }
        return curr_balance;
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
        dest.writeString(mFB_token);
        dest.writeString(mEmail);
        dest.writeString(mPhone);
        dest.writeLong(mTimestamp);
        dest.writeByte( (byte) (mNeedSync ? 1 : 0) );
        dest.writeInt(mOrder);
    }

    public void readFromParcel ( Parcel in ) {
        mLocal_id = in.readInt();
        mCloud_id = in.readInt();
        mName = in.readString();
        mFB_token = in.readString();
        mEmail = in.readString();
        mPhone = in.readString();
        mTimestamp = in.readLong();
        mNeedSync = in.readByte() != 0;
        mOrder = in.readInt();
    }

    public static final Creator<TriFriend> CREATOR = new Creator<TriFriend>() {
        @Override
        public TriFriend createFromParcel(Parcel in) {
            return new TriFriend(in);
        }

        @Override
        public TriFriend[] newArray(int size) {
            return new TriFriend[size];
        }
    };


}