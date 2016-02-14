package com.example.louisccc.tripal.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.louisccc.tripal.utility.DateHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by louisccc on 1/30/16.
 */
public class TriTrip implements Parcelable {

    public static final String DATABASE_TABLE_NAME = "trips";

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
                    + KEY_TIMESTAMP     + " timestamp default current_timestamp, "
                    + KEY_NEEDSYNC      + " integer default 1, "
                    + KEY_ORDER         + " integer default 0  "
                    + ");";

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

    protected TriTrip(Parcel in) {
        readFromParcel(in);
    }

    public TriTrip () { /* clean constructor */
        mLocal_id = 0;
        mCloud_id = 0;
        mName = "default trip name";
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

    public int getLocalId() {
        return mLocal_id;
    }

    public void setLocalId(long localId) {
        this.mLocal_id = (int)localId;
    }

    public String getName() {
        return mName;
    }


    public Date getDateFrom() {
        return mTime_from;
    }

    public Date getDateTo() {
        return mTime_to;
    }

    public double getBudget() {
        return mInit_balance;
    }

    public double getCurrBalance() {
        double curr_balance = getBudget();
        for(TriItem item : TriApplication.getInstance().getgItems()) {
            if ( item.getTripId() == this.mLocal_id ) {
                curr_balance -= item.getAmount();
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
        dest.writeDouble(mInit_balance);
        dest.writeDouble(mCurr_balance);
        dest.writeInt(mCategory_id);
        dest.writeString(DateHelper.getDateString(mTime_from));
        dest.writeString(DateHelper.getDateString(mTime_to));
        dest.writeLong(mTimestamp);
        dest.writeByte((byte) (mNeedSync ? 1 : 0));
        dest.writeInt(mOrder);
        dest.writeInt(num_item);
    }

    public void readFromParcel ( Parcel in ) {
        mLocal_id = in.readInt();
        mCloud_id = in.readInt();
        mName = in.readString();
        mInit_balance = in.readDouble();
        mCurr_balance = in.readDouble();
        mCategory_id = in.readInt();
        mTime_from = DateHelper.getDate(in.readString());
        mTime_to = DateHelper.getDate(in.readString());
        mTimestamp = in.readLong();
        mNeedSync = in.readByte() != 0;
        mOrder = in.readInt();
        num_item = in.readInt();
    }

    public static final Creator<TriTrip> CREATOR = new Creator<TriTrip>() {
        @Override
        public TriTrip createFromParcel(Parcel in) {
            return new TriTrip(in);
        }

        @Override
        public TriTrip[] newArray(int size) {
            return new TriTrip[size];
        }
    };

    public ArrayList<TriFriend> getMembers() {
        ArrayList<TriFriend> members = new ArrayList<TriFriend>();
        for ( TriParticipation p : TriApplication.getInstance().getgParticipations() ) {
            if ( p.getTrip().getLocalId() == this.mLocal_id ) {
                members.add(p.getFriend());
            }
        }
        return members;
    }

    public ArrayList<TriItem> getRecords() {
        ArrayList<TriItem> items = new ArrayList<TriItem>();
        for( TriItem i : TriApplication.getInstance().getgItems() ) {
            if ( i.getTripId() == this.mLocal_id ) {
                items.add(i);
            }
        }
        return items;
    }
}
