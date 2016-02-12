package com.example.louisccc.tripal.model;

import android.content.ContentValues;

/**
 * Created by louisccc on 2/10/16.
 */
public class TriFriend {

    public static final String DATABASE_TABLE_NAME = "friends";

    public static final String KEY_LOCALID = "trip_local_id";
    public static final String KEY_CLOUDID = "trip_cloud_id";
    public static final String KEY_NAME = "trip_name";
    public static final String KEY_FB = "token";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_TIMESTAMP = "last_modified_timestamp";
    public static final String KEY_NEEDSYNC = "need_sync";
    public static final String KEY_ORDER = "trip_order";

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
}