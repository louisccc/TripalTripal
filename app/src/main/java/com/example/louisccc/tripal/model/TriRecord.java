package com.example.louisccc.tripal.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.Date;

import com.example.louisccc.tripal.DBManager;
import com.example.louisccc.tripal.DateHelper;

public class TriRecord implements Parcelable {
	private final int NUM_COLUMN = 13;
	private int mLocalId;
	

	private int mCloudId;
	
	private int mLocalAccountId;
	private int mLocalAccountDstId;
	private TriCategoryType mType;
	private int mLocalCategoryId;
	private String mDesc;
	private double mAmount; 
	private double mAmountDst;
	private double mFee;
	private Date mDate;
	private boolean mNeedSync;
	private int mTimestamp;
	private String mCategoryName="";
	private String mLocalAccountName = "";
	private String mLocalAccountNameDst = "";

    private int mCloudAccountId;
    private int mCloudAccountDstId;
    private int mCloudCategoryId;
	
	public TriRecord(Cursor c) {
		mLocalId = c.getInt(c.getColumnIndex(DBManager.RECORD_KEY_LOCALID));
        if (c.getColumnIndex("r_cloud_id") != -1){
            mCloudId = c.getInt(c.getColumnIndex("r_cloud_id"));
        }
        else {
            mCloudId = c.getInt(c.getColumnIndex(DBManager.RECORD_KEY_CLOUDID));
        }
		mLocalAccountId = c.getInt(c.getColumnIndex(DBManager.RECORD_KEY_ACCOUNTID));
		mLocalAccountDstId = c.getInt(c.getColumnIndex(DBManager.RECORD_KEY_ACCOUNTDSTID));
		mType = TriCategoryType.values()[c.getInt(c.getColumnIndex(DBManager.RECORD_KEY_TYPE))];
		mLocalCategoryId = c.getInt(c.getColumnIndex(DBManager.RECORD_KEY_CATEGORYID));
		mDesc = c.getString(c.getColumnIndex(DBManager.RECORD_KEY_DESC));
		mAmount = c.getDouble(c.getColumnIndex((DBManager.RECORD_KEY_AMOUNT)));
		mAmountDst = c.getDouble(c.getColumnIndex((DBManager.RECORD_KEY_AMOUNTDST)));
		mFee = c.getDouble(c.getColumnIndex((DBManager.RECORD_KEY_FEE)));
		mDate = DateHelper.getDate(c.getString(c.getColumnIndex(DBManager.RECORD_KEY_DATE)));
		mNeedSync = (c.getInt(c.getColumnIndex(DBManager.RECORD_KEY_NEEDSYNC)) == 1);
		mTimestamp = c.getInt(c.getColumnIndex(DBManager.RECORD_KEY_TIMESTAMP));

        if (c.getColumnIndex("a1_cloud_id") != -1) {
            mCloudAccountId = c.getInt(c.getColumnIndex("a1_cloud_id"));
        }

        if (c.getColumnIndex("a2_cloud_id") != -1) {
            mCloudAccountDstId = c.getInt(c.getColumnIndex("a2_cloud_id"));
        }

        if (c.getColumnIndex("c_cloud_id") != -1){
            mCloudCategoryId = c.getInt(c.getColumnIndex("c_cloud_id"));
        }
//		mCategoryName = c.getString(c.getColumnIndex(DBManager.CATEGORY_KEY_NAME));
//		if(mCategoryName == null || mCategoryName.equals("")){
//			mCategoryName = "transfer";
//		}
	}
	
	
	public TriRecord(JSONObject object, int localId, int cloudId) {
		try {
			mLocalId = localId;
			mCloudId = cloudId;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public TriRecord(int mLocalId, int mCloudId, int mLocalAccountId,
					 int mLocalAccountDstId, TriCategoryType mType, int mLocalCategoryId,
					 String mDesc, double mAmount, double mAmountDst, double mFee,
					 Date mDate, boolean mNeedSync, int mTimestamp) {
		this.mLocalId = mLocalId;
		this.mCloudId = mCloudId;
		this.mLocalAccountId = mLocalAccountId;
		this.mLocalAccountDstId = mLocalAccountDstId;
		this.mType = mType;
		this.mLocalCategoryId = mLocalCategoryId;
		this.mDesc = mDesc;
		this.mAmount = mAmount;
		this.mAmountDst = mAmountDst;
		this.mFee = mFee;
		this.mDate = mDate;
		this.mNeedSync = mNeedSync;
		this.mTimestamp = mTimestamp;
	}
	
	public TriRecord(Parcel in){
		this.mLocalId = in.readInt();
		this.mCloudId = in.readInt();
		this.mLocalAccountId = in.readInt();
		this.mLocalAccountDstId = in.readInt();
		this.mType = TriCategoryType.values()[in.readInt()];
		this.mLocalCategoryId = in.readInt();
		this.mDesc = in.readString();
		this.mAmount = in.readDouble();
		this.mAmountDst = in.readDouble();
		this.mFee = in.readDouble();
		this.mDate = DateHelper.getDate(in.readString());
		this.mNeedSync = (in.readInt() == 1);
		this.mTimestamp = in.readInt();
		this.mCategoryName = in.readString();
		this.mLocalAccountName = in.readString();
		this.mLocalAccountNameDst = in.readString();
	}
	
	public String getLocalAccountName(){
		return mLocalAccountName;
	}
	public void setLocalAccountName(String value){
		mLocalAccountName = value;
	}
	public String getLocalAccountNameDst(){
		return mLocalAccountNameDst;
	}
	public void setLocalAccountNameDst(String value){
		mLocalAccountNameDst = value;
	}
	public int getCategoryId() {
		return mLocalCategoryId;
	}

	public String getDesc() {
		return mDesc;
	}
	
	public String getShowingDescString(){
		String descString = "";
		if(getDesc().trim().equals("")){
			descString = "No description";
		}else{
			descString = getDesc().trim();
		}
		return descString;
	}

	public double getAmount() {
		return mAmount;
	}
	
	public String getShowingAmountString() {
		String amountString = "";
		if(getType() == TriCategoryType.TriCategoryTypeExpense){
			amountString = "- $ " + Double.toString(getAmount());
		}
		else if(getType() == TriCategoryType.TriCategoryTypeIncome){
			amountString = "+ $ " + Double.toString(getAmount());
		}
		else{
			amountString = "~ $ " + Double.toString(getAmount());
		}
		return amountString;
	}
	
	
	public String getCategoryName(){
		if (mCategoryName!=null && !mCategoryName.equals("")) {
			return mCategoryName;
		}
		return null;
	}

	public void setCategoryName(String value) {
		mCategoryName = value;
	}

	public TriCategoryType getType() {
		return mType;
	}

	public int getLocalId() {
		return mLocalId;
	}

	public void setLocalId(int local_id) {
		this.mLocalId = local_id;
	}

	public int getCloudId() {
		return mCloudId;
	}

	public void setCloudId(int mCloudId) {
		this.mCloudId = mCloudId;
	}

	public int getLocalAccountDstId() {
		return mLocalAccountDstId;
	}

	public void setLocalAccountDstId(int mLocalAccountDstId) {
		this.mLocalAccountDstId = mLocalAccountDstId;
	}

	public double getAmountDst() {
		return mAmountDst;
	}

	public void setAmountDst(double mAmountDst) {
		this.mAmountDst = mAmountDst;
	}

	public double getFee() {
		return mFee;
	}

	public void setFee(double mFee) {
		this.mFee = mFee;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date mDate) {
		this.mDate = mDate;
	}

	public boolean isNeedSync() {
		return mNeedSync;
	}

	public void setNeedSync(boolean mNeedSync) {
		this.mNeedSync = mNeedSync;
	}

	public int getTimestamp() {
		return mTimestamp;
	}

	public void setTimestamp(int mTimestamp) {
		this.mTimestamp = mTimestamp;
	}

	public int getLocalAccountId() {
		return mLocalAccountId;
	}

	public void setLocalAccountId(int mLocalAccountId) {
		this.mLocalAccountId = mLocalAccountId;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mLocalId);
		dest.writeInt(mCloudId);
		dest.writeInt(mLocalAccountId);
		dest.writeInt(mLocalAccountDstId);
		dest.writeInt(mType.ordinal());
		dest.writeInt(mLocalCategoryId);
		dest.writeString(mDesc);
		dest.writeDouble(mAmount);
		dest.writeDouble(mAmountDst);
		dest.writeDouble(mFee);
		dest.writeString(DateHelper.getDateString(mDate));
		if(mNeedSync){
			dest.writeInt(1);
		}
		else{
			dest.writeInt(0);
		}
		dest.writeInt(mTimestamp);
		dest.writeString(mCategoryName);
		dest.writeString(mLocalAccountName);
		dest.writeString(mLocalAccountNameDst);
	}
	@SuppressWarnings("unchecked")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TriRecord createFromParcel(Parcel in)
        {
            return new TriRecord(in);
        }
 
        public TriRecord[] newArray(int size)
        {
            return new TriRecord[size];
        }
    };

    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("cloud_id", mCloudId);
            obj.put("local_id", mLocalId);
            obj.put("timestamp", mTimestamp);
            obj.put("cloud_account_id", mCloudAccountId);
            obj.put("local_account_id", mLocalAccountId);
            obj.put("cloud_account_dst_id", mCloudAccountDstId);
            obj.put("local_account_dst_id", mLocalAccountDstId);
            obj.put("date", DateHelper.getDateString(mDate));
            obj.put("type", "expense");
            obj.put("cloud_category_id", mCloudCategoryId);
            obj.put("local_category_id", mLocalCategoryId);
            obj.put("amount", mAmount);
            obj.put("fee", mFee);
            obj.put("amount_dst", mAmountDst);
            obj.put("desc", mDesc);
//            obj.put("local_invoice_id",0);
//            obj.put("cloud_invoice_id",0);
            // invoice
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }
}
