package com.example.louisccc.tripal.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TriCategory implements Parcelable {
	private final int NUM_COLUMN = 8;
	private int mLocalId = 0;
	private int mCloudId = 0;
	private int mParentId = 0;
	private int mRank = 0;
	private TriCategoryType mType;
	private String mName = "";
	private int mTimestamp = 0;
	private boolean mNeedSync = true;

	public TriCategory(JSONObject object, int localId, int cloudId, int timestamp){
		try {
			this.mLocalId = localId;
			this.mCloudId = cloudId;
			this.mParentId = object.getInt("cloud_parent_id");
			this.mRank = object.getInt("rank");
			this.mType = getType(object.getString("type"));
			this.mTimestamp = timestamp;
			this.mName = object.getString("name");
			this.mNeedSync = (this.mCloudId==0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public TriCategory(int localId, int cloudId, int parentId, int rank, TriCategoryType type, String name, int timestamp) {
		this.mLocalId = localId;
		this.mCloudId = cloudId;
		this.mRank = rank;
		this.mType = type;
		this.mName = name;
		this.mTimestamp = timestamp;
		this.mNeedSync = (cloudId == 0);
	}
	
	public TriCategory(Cursor c) {
		mLocalId = c.getInt(c.getColumnIndex(DBManager.CATEGORY_KEY_LOCALID));
		mCloudId = c.getInt(c.getColumnIndex(DBManager.CATEGORY_KEY_CLOUDID));
		mParentId = c.getInt(c.getColumnIndex(DBManager.CATEGORY_KEY_PARENTID));
		mRank = c.getInt(c.getColumnIndex(DBManager.CATEGORY_KEY_RANK));
		mType = TriCategoryType.values()[c.getInt(c.getColumnIndex(DBManager.CATEGORY_KEY_TYPE))];
		mName = c.getString(c.getColumnIndex(DBManager.CATEGORY_KEY_NAME));
		mTimestamp = c.getInt(c.getColumnIndex(DBManager.CATEGORY_KEY_TIMESTAMP));
		mNeedSync = (c.getInt(c.getColumnIndex(DBManager.ACCOUNT_KEY_NEEDSYNC)) == 0);
	}
	
	public TriCategory(Parcel parcel){
		this.mLocalId = parcel.readInt();
		this.mCloudId = parcel.readInt();
		this.mParentId = parcel.readInt();
		this.mRank = parcel.readInt();
		this.mType = TriCategoryType.values()[parcel.readInt()];
		this.mName = parcel.readString();
		this.mTimestamp = parcel.readInt();
		this.mNeedSync = (parcel.readInt()==1);
	}
	
	private TriCategoryType getType(String type) {
		TriCategoryType bktype;
		if (type.equals("expense")) {
			bktype = TriCategoryType.TriCategoryTypeExpense;
		} else if (type.equals("income")) {
			bktype = TriCategoryType.TriCategoryTypeIncome;
		} else if (type.equals("transfer")) {
			bktype = TriCategoryType.TriCategoryTypeTransfer;
		} else {
			bktype = TriCategoryType.TriCategoryTypeExpense;
		}
		return bktype;
	}

	public String getName() {
		return mName;
	}

	public TriCategoryType getType() {
		return mType;
	}

	public void setRank(int value) {
		mRank = value;
	}

	public int getRank() {
		return mRank;
	}

	public void setTimestamp(int value) {
		mTimestamp = value;
	}

	public int getTimestamp() {
		return mTimestamp;
	}

	public void setLocalId(int value) {
		mLocalId = value;
	}

	public int getLocalId() {
		return mLocalId;
	}

	public void setCloudId(int value) {
		mCloudId = value;
	}

	public int getCloudId() {
		return mCloudId;
	}

	public void setParentId(int value) {
		mParentId = value;
	}

	public int getParentId() {
		return mParentId;
	}

	public void setSync() {
		mNeedSync = false;
	}

	public boolean needSync() {
		return mNeedSync;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeInt(mLocalId);		
		dest.writeInt(mCloudId);
		dest.writeInt(mParentId);
		dest.writeInt(mRank);
		dest.writeInt(mType.ordinal());
		dest.writeString(mName);
		dest.writeInt(mTimestamp);
		if(mNeedSync){
			dest.writeInt(1);
		}
		else{
			dest.writeInt(0);
		}
	}
	
	@SuppressWarnings("unchecked")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TriCategory createFromParcel(Parcel in)
        {
            return new TriCategory(in);
        }
 
        public TriCategory[] newArray(int size)
        {
            return new TriCategory[size];
        }
    };

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}
