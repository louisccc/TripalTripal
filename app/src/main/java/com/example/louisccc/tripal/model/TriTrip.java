package com.example.louisccc.tripal.model;

import android.content.ContentValues;

import com.example.louisccc.tripal.com.example.louisccc.tripal.utility.DateHelper;

import java.util.Date;

/**
 * Created by louisccc on 1/30/16.
 */
public class TriTrip {
    public static final String DATABASE_TABLE_NAME = "trips";
    /* members */
    private int mLocal_id;
    private int mCloud_id;
    private String mName;
    private double mInit_balance;
    private double mCurr_balance;
    private int mCategory_id;
    private Date mTime_from;
    private Date mTime_to;
    private Long mTimestamp;
    private boolean mNeedSync;
    private int mOrder;

    private int num_item; /* ? */

    public static final String KEY_LOCALID = "trip_local_id";
    public static final String KEY_CLOUDID = "trip_cloud_id";
    public static final String KEY_NAME = "trip_name";
    public static final String KEY_INITBALANCE = "init_balance";
    public static final String KEY_BALANCE = "balance";
    public static final String KEY_CATEGORYID = "category_id";
    public static final String KEY_TIMESTAMPFROM = "time_from";
    public static final String KEY_TIMESTAMPTO = "time_to";
    public static final String KEY_TIMESTAMP = "last_modified_timestamp";
    public static final String KEY_NEEDSYNC = "need_sync";
    public static final String KEY_ORDER = "trip_order";

    public static final String TRIPS_CREATE =
            "create table " + DATABASE_TABLE_NAME
            + " ("
            + KEY_LOCALID       + " integer not null primary key autoincrement, "
            + KEY_CLOUDID       + " integer default 0, "
            + KEY_NAME          + " text, "
            + KEY_INITBALANCE   + " real, "
            + KEY_BALANCE       + " real, "
            + KEY_CATEGORYID    + " integer, "
            + KEY_TIMESTAMPFROM + " date, "
            + KEY_TIMESTAMPTO   + " date, "
            + KEY_TIMESTAMP     + " timestamp dafault current_timestamp, "
            + KEY_NEEDSYNC      + " integer default 1, "
            + KEY_ORDER         + " integer default 0  "
            + ");";


    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        // local id is assigned.
        contentValues.put( KEY_CLOUDID      , mCloud_id);
        contentValues.put( KEY_NAME         , mName);
        contentValues.put( KEY_INITBALANCE  , mInit_balance);
        contentValues.put( KEY_BALANCE      , mCurr_balance );
        contentValues.put( KEY_CATEGORYID   , mCategory_id );
        contentValues.put( KEY_TIMESTAMPFROM, DateHelper.getDateString(mTime_from) );
        contentValues.put( KEY_TIMESTAMPTO  , DateHelper.getDateString(mTime_to) );
        contentValues.put( KEY_TIMESTAMP    , mTimestamp );
        contentValues.put( KEY_NEEDSYNC     , mNeedSync );
        contentValues.put( KEY_ORDER        , mOrder );
        return contentValues;
    }

    public TriTrip () { /* clean constructor */
        mLocal_id = 0;
        mCloud_id = 0;
        mName = " default trip name";
        mInit_balance = 0;
        mCurr_balance = 0;
        mCategory_id = 0;
        mTime_from = new Date();
        mTime_to = new Date();
        mTimestamp = System.currentTimeMillis();
        mNeedSync = true;
        mOrder = 0;
    }

    public TriTrip (String name, int init_balance, int cat_id, Date from, Date to) {
        mLocal_id = 0;
        mCloud_id = 0;
        mName = name;
        mInit_balance = init_balance; mCurr_balance = init_balance;
        mCategory_id = cat_id;
        mTime_from = from; mTime_to = to;
        mTimestamp = System.currentTimeMillis();
        mNeedSync = true;
        mOrder = 0;
    }


}
