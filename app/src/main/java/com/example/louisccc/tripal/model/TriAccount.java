package com.example.louisccc.tripal.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TriAccount implements Parcelable {

    private final int NUM_COLUMN = 13;
    private int mLocalId = 0;
    private int mCloudId = 0;
    private TriAccountType mType;
    private boolean mDisabled = false;
    private double mInitBalance = 0;
    private double mRemain = 0;
    private double mRate = 0;
    private String mName = "";
    private String mCurrency = "";
    private String mCustomName = "";
    private boolean mNeedSync = false;
    private int mTimestamp = 0;
    private int mOrder = 0;

    /*
        these parameters are used for interacting with banck server
    */
    public final static String ACCOUNT_SERVER_KEY_CLOUDID = "cloud_id";
    public final static String ACCOUNT_SERVER_KEY_NAME = "name";
    public final static String ACCOUNT_SERVER_KEY_TYPE = "type";
    public final static String ACCOUNT_SERVER_KEY_INIT = "init";
    public final static String ACCOUNT_SERVER_KEY_REMAIN = "remain";
    public final static String ACCOUNT_SERVER_KEY_CURRENCY = "currency";
    public final static String ACCOUNT_SERVER_KEY_CUSNAME = "custom_name";
    public final static String ACCOUNT_SERVER_KEY_RATE = "rate";
    public final static String ACCOUNT_SERVER_KEY_DISABLE = "disabled";
    public final static String ACCOUNT_SERVER_KEY_ORDER = "order";

    /*
        Parameters starting with ACCOUNT_DB_KEY are used for
        interacting with local database.
    */
    public final static String ACCOUNT_DB_KEY_CLOUDID = "cloud_id";
    public final static String ACCOUNT_DB_KEY_LOCALID = "local_id";
    public final static String ACCOUNT_DB_KEY_TYPE = "type";
    public final static String ACCOUNT_DB_KEY_DISABLE = "disabled";
    public final static String ACCOUNT_DB_KEY_INITBALANCE = "init";
    public final static String ACCOUNT_DB_KEY_REMAIN = "remain";
    public final static String ACCOUNT_DB_KEY_RATE = "rate";
    public final static String ACCOUNT_DB_KEY_NAME = "name";
    public final static String ACCOUNT_DB_KEY_CURRENCY = "currency";
    public final static String ACCOUNT_DB_KEY_CUSTOMCURRENCYNAME = "custom_name";
    public final static String ACCOUNT_DB_KEY_NEEDSYNC = "need_sync";
    public final static String ACCOUNT_DB_KEY_TIMESTAMP = "timestamp";
    public final static String ACCOUNT_DB_KEY_ORDER = "account_order";


    /*
        Constructor of TriAccount using database query cursor coming from local database.
    */
    public TriAccount(Cursor c) {
        mLocalId = c.getInt(c.getColumnIndex(ACCOUNT_DB_KEY_LOCALID));
        mCloudId = c.getInt(c.getColumnIndex(ACCOUNT_DB_KEY_CLOUDID));
        mType = TriAccountType.values()[c.getInt(c.getColumnIndex(ACCOUNT_DB_KEY_TYPE))];
        mDisabled = (c.getInt(c.getColumnIndex(ACCOUNT_DB_KEY_DISABLE)) == 0);
        mInitBalance = c.getDouble(c.getColumnIndex(ACCOUNT_DB_KEY_INITBALANCE));
        mRemain = c.getDouble(c.getColumnIndex(ACCOUNT_DB_KEY_REMAIN));
        mRate = c.getDouble(c.getColumnIndex(ACCOUNT_DB_KEY_RATE));
        mName = c.getString(c.getColumnIndex(ACCOUNT_DB_KEY_NAME));
        mCurrency = c.getString(c.getColumnIndex(ACCOUNT_DB_KEY_CURRENCY));
        mCustomName = c.getString(c.getColumnIndex(ACCOUNT_DB_KEY_CUSTOMCURRENCYNAME));
        mNeedSync = (c.getInt(c.getColumnIndex(ACCOUNT_DB_KEY_NEEDSYNC)) == 0);
        mTimestamp = c.getInt(c.getColumnIndex(ACCOUNT_DB_KEY_TIMESTAMP));
        mOrder = c.getInt(c.getColumnIndex(ACCOUNT_DB_KEY_ORDER));
    }


	/*
        Constructor of TriAccount using JSON object coming from Banck Server.
		An example of coming JSON string from server can be,

		{"cloud_id":"47376","name":"\u532f\u8c50\u85aa\u8cc7\u5e33\u6236",
		 "type":"bank","init":"0","remain":"0","currency":"TWD","custom_name":"",
		 "rate":"1","disabled":"0","order":"0"}

	 */


    public TriAccount(JSONObject object, int localId, int cloudId, int timestamp) {
		/* assert object to be non-null value */
        if (object == null)
            throw new AssertionError("TriAccount Constructor: JSON object is NULL.");

        try {
            mLocalId = localId;
            this.mCloudId     = object.getInt(ACCOUNT_SERVER_KEY_CLOUDID);
            this.mType        = getType(object.getString(ACCOUNT_SERVER_KEY_TYPE));
            this.mDisabled    = object.getInt(ACCOUNT_SERVER_KEY_DISABLE) != 0;
            this.mInitBalance = object.getDouble(ACCOUNT_SERVER_KEY_INIT);
            this.mRemain      = object.getDouble(ACCOUNT_SERVER_KEY_REMAIN);
            this.mRate        = object.getDouble(ACCOUNT_SERVER_KEY_RATE);
            this.mName        = object.getString(ACCOUNT_SERVER_KEY_NAME);
            this.mCurrency    = object.getString(ACCOUNT_SERVER_KEY_CURRENCY);
            this.mCustomName  = object.getString(ACCOUNT_SERVER_KEY_CUSNAME);
            this.mNeedSync    = false; /* This is because JSONObject is from servier side, no need to sync again */
            this.mTimestamp   = timestamp;
            this.mOrder       = object.getInt(ACCOUNT_SERVER_KEY_ORDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Constructor of TriAccount using parameters directly
     */
    public TriAccount(int cloud_id, TriAccountType type, boolean disabled,
                      double init_balance, double remain, double rate, String name,
                      String currency, String custom_name, boolean need_sync, int timestamp, int order) {
        this.mCloudId = cloud_id;
        this.mType = type;
        this.mDisabled = disabled;
        this.mInitBalance = init_balance;
        this.mRemain = remain;
        this.mRate = rate;
        this.mName = name;
        this.mCurrency = currency;
        this.mCustomName = name;
        this.mNeedSync = need_sync;
        this.mTimestamp = timestamp;
        this.mOrder = order;
    }

    /*
        This contructor is used when passing TriAccount thru bundle
    */
    public TriAccount(Parcel parcel) {
        this.mCloudId = parcel.readInt();
        this.mLocalId = parcel.readInt();
        this.mType = TriAccountType.values()[parcel.readInt()];
        this.mDisabled = (parcel.readInt() == 1);
        this.mInitBalance = parcel.readDouble();
        this.mRemain = parcel.readDouble();
        this.mRate = parcel.readDouble();

        this.mName = parcel.readString();
        this.mCurrency = parcel.readString();
        this.mCustomName = parcel.readString();

        this.mNeedSync = (parcel.readInt() == 1);
        this.mTimestamp = parcel.readInt();
        this.mOrder = parcel.readInt();
    }


    private TriAccountType getType(String type) {
        TriAccountType bktype = null;
        if (type.equals("cash")) {
            bktype = TriAccountType.TriAccountTypeCash;
        } else if (type.equals("credit")) {
            bktype = TriAccountType.TriAccountTypeCredit;
        } else if (type.equals("paid")) {
            bktype = TriAccountType.TriAccountTypePrepaid;
        } else if (type.equals("bank")) {
            bktype = TriAccountType.TriAccountTypeBank;
        }
        return bktype;
    }

    public int getCloudId() {
        return mCloudId;
    }

    public void setLocalId(int value) {
        mLocalId = value;
    }

    public int getLocalId() {
        return mLocalId;
    }

    public String getName() {
        return mName;
    }

    public TriAccountType getType() {
        return mType;
    }

    public String getTypeString() {
        if (getType().equals(TriAccountType.TriAccountTypeCash)) {
            return "Cash";
        } else if (getType().equals(TriAccountType.TriAccountTypeCredit)) {
            return "Credit";
        } else if (getType().equals(TriAccountType.TriAccountTypePrepaid)) {
            return "Prepaid";
        } else if (getType().equals(TriAccountType.TriAccountTypeBank)) {
            return "Bank";
        }
        return null;
    }

    public double getInitBalance() {
        return mInitBalance;
    }

    public void setCurrency(String value) {
        mCurrency = value;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public double getRemain() {
        return mRemain;
    }

    public int getTimetamp() {
        return mTimestamp;
    }

    public String getCustomName() {
        return mCustomName;
    }

    public double getRate() {
        return mRate;
    }

    public double getOrder() {
        return mOrder;
    }

    public boolean isDisabled() {
        return mDisabled;
    }

    public void setSync() {
        mNeedSync = true;
    }

    public boolean need_update() {
        return mNeedSync;
    }

    public String toString() {
        return "" + mName + " " + mRemain + " ";
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCloudId);
        dest.writeInt(mLocalId);
        dest.writeInt(mType.ordinal());
        if (mDisabled) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }
        dest.writeDouble(mInitBalance);
        dest.writeDouble(mRemain);
        dest.writeDouble(mRate);

        dest.writeString(mName);
        dest.writeString(mCurrency);
        dest.writeString(mCustomName);

        if (mNeedSync) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }

        dest.writeInt(mTimestamp);
        dest.writeInt(mOrder);
    }

    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TriAccount createFromParcel(Parcel in) {
            return new TriAccount(in);
        }

        public TriAccount[] newArray(int size) {
            return new TriAccount[size];
        }
    };
}
