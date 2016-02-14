package com.example.louisccc.tripal.model;

import android.content.ContentValues;

import com.example.louisccc.tripal.utility.DateHelper;

import java.util.Date;

/**
 * Created by louisccc on 2/11/16.
 */
public class TriDept extends TriItem {
    public static final String DATABASE_TABLE_NAME = "depts";

    public static final String KEY_LOCALID  = "dept_local_id";
    public static final String KEY_CLOUDID  = "dept_cloud_id";
    public static final String KEY_ITEMID   = "item_id";
    public static final String KEY_USERID   = "user_id";
    public static final String KEY_PROPOTION= "proportion";
    public static final String KEY_PAID     = "paid";
    public static final String KEY_TIMESTAMP= "last_modified_timestamp";
    public static final String KEY_NEEDSYNC = "need_sync";

    public static final String DEPTS_CREATE =
            "create table " + DATABASE_TABLE_NAME
                    + " ("
                    + KEY_LOCALID         + " integer not null primary key autoincrement, "
                    + KEY_CLOUDID         + " integer default 0, "
                    + KEY_ITEMID          + " integer, "
                    + KEY_USERID          + " integer, "
                    + KEY_PROPOTION       + " real, "
                    + KEY_PAID            + " real, "
                    + KEY_TIMESTAMP       + " timestamp default current_timestamp, "
                    + KEY_NEEDSYNC        + " integer default 1 "
                    + ");";

    /* members */
    private int mLocal_id;
    private int mCloud_id;
    private int mItem_id;
    private int mUser_id;
    private double mPropotion;
    private double mPaid;
    private long mLast_modified_timestamp;
    private boolean mNeedSync;

    public TriDept(){
        this.mLocal_id = 0;
        this.mCloud_id = 0;
        this.mItem_id = 0;
        this.mUser_id = 0;
        this.mPropotion = 0;
        this.mPaid = 0;
        this.mLast_modified_timestamp = System.currentTimeMillis();
        this.mNeedSync = true;
    }

    public TriDept(TriItem item, TriFriend friend, double propotion, double paid) {
        this.mLocal_id = 0;
        this.mCloud_id = 0;
        this.mItem_id = item.getLocalId();
        this.mUser_id = friend.getLocalId();
        this.mPropotion = propotion;
        this.mPaid = paid;
        this.mLast_modified_timestamp = System.currentTimeMillis();
        this.mNeedSync = true;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        // local id is assigned.
        contentValues.put( KEY_CLOUDID       , mCloud_id );
        contentValues.put( KEY_ITEMID        , mItem_id );
        contentValues.put( KEY_USERID        , mUser_id );
        contentValues.put( KEY_PROPOTION     , mPropotion );
        contentValues.put( KEY_PAID          , mPaid );
        contentValues.put( KEY_TIMESTAMP     , mLast_modified_timestamp );
        contentValues.put( KEY_NEEDSYNC      , mNeedSync );

        return contentValues;

    }

    public int getLocalId() {
        return mLocal_id;
    }

    public void setlocalId(long localId) {
        this.mLocal_id = (int)localId;
    }
}
