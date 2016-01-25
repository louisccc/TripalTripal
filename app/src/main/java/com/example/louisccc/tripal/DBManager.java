package com.example.louisccc.tripal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
import java.util.ArrayList;


import com.example.louisccc.tripal.model.TriAccount;
import com.example.louisccc.tripal.model.TriAccountType;
import com.example.louisccc.tripal.model.TriCategory;
import com.example.louisccc.tripal.model.TriCategoryType;
import com.example.louisccc.tripal.model.TriRecord;


public class DBManager {
	public static final String TAG = "dbadapter";

	public static final String DATABASE_NAME = "tripal.sqlite";

	public static final int DATABASE_VERSION = 1;

	public static final String DATABASE_TABLE_RECORD = "record";
	public static final String RECORD_KEY_LOCALID = "local_id";
	public static final String RECORD_KEY_CLOUDID = "cloud_id";
	public static final String RECORD_KEY_ACCOUNTID = "local_account_id";
	public static final String RECORD_KEY_ACCOUNTDSTID = "local_account_dst_id";
	public static final String RECORD_KEY_CATEGORYID = "local_category_id";
	public static final String RECORD_KEY_TYPE = "type";
	public static final String RECORD_KEY_AMOUNT = "amount";
	public static final String RECORD_KEY_DESC = "desc";
	public static final String RECORD_KEY_DATE = "date";
	public static final String RECORD_KEY_TIMESTAMP = "timestamp";
	public static final String RECORD_KEY_AMOUNTDST = "amount_dst";
	public static final String RECORD_KEY_FEE = "fee";
	public static final String RECORD_KEY_NEEDSYNC = "need_sync";

	public static final String DATABASE_TABLE_RECORD_CREATE = "create table "
			+ DATABASE_TABLE_RECORD + " (" 
			+ RECORD_KEY_LOCALID + " integer not null primary key autoincrement, "
			+ RECORD_KEY_CLOUDID + " integer default 0, "
			+ RECORD_KEY_ACCOUNTID + " integer, " 
			+ RECORD_KEY_ACCOUNTDSTID + " integer, " 
			+ RECORD_KEY_TYPE + " integer, "
			+ RECORD_KEY_CATEGORYID + " integer, " 
			+ RECORD_KEY_DESC + " text, " 
			+ RECORD_KEY_AMOUNT + " real, " 
			+ RECORD_KEY_AMOUNTDST + " real, " 
			+ RECORD_KEY_FEE + " real, " 
			+ RECORD_KEY_DATE + " date, " 
			+ RECORD_KEY_NEEDSYNC + " integer default 0,"
			+ RECORD_KEY_TIMESTAMP + " timestamp default current_timestamp"
			+ ");";

	public static final String DATABASE_TABLE_CATEGORY = "category";
	public static final String CATEGORY_KEY_LOCALID = "local_id";
	public static final String CATEGORY_KEY_NAME = "name";
	public static final String CATEGORY_KEY_TYPE = "type";
	public static final String CATEGORY_KEY_PARENTID = "parent";
	public static final String CATEGORY_KEY_RANK = "rank";
	public static final String CATEGORY_KEY_CLOUDID = "cloud_id";
	public static final String CATEGORY_KEY_TIMESTAMP = "timestamp";
	public static final String CATEGORY_KEY_NEEDSYNC = "need_sync";

	public static final String DATABASE_TABLE_CATEGORY_CREATE = "create table "
			+ DATABASE_TABLE_CATEGORY + " (" 
			+ CATEGORY_KEY_LOCALID + " integer not null primary key autoincrement, "
			+ CATEGORY_KEY_CLOUDID + " integer default 0, "
			+ CATEGORY_KEY_PARENTID + " integer, "
			+ CATEGORY_KEY_RANK + " integer, "
			+ CATEGORY_KEY_TYPE + " integer, "
			+ CATEGORY_KEY_NAME + " text, "
			+ CATEGORY_KEY_TIMESTAMP + " timestamp default current_timestamp, "
			+ CATEGORY_KEY_NEEDSYNC + " integer default 0 " + ");";

	public static final String DATABASE_TABLE_ACCOUNT = "account";
	
	public static final String ACCOUNT_KEY_LOCALID = "local_id";
	public static final String ACCOUNT_KEY_CLOUDID = "cloud_id";
	public static final String ACCOUNT_KEY_NAME = "name";
	public static final String ACCOUNT_KEY_INITBALANCE = "init";
	public static final String ACCOUNT_KEY_REMAIN = "remain";
	public static final String ACCOUNT_KEY_CURRENCY = "currency";
	public static final String ACCOUNT_KEY_TYPE = "type";
	public static final String ACCOUNT_KEY_TIMESTAMP = "timestamp";
	public static final String ACCOUNT_KEY_CUSTOMCURRENCYNAME = "custom_name";
	public static final String ACCOUNT_KEY_RATE = "rate";
	public static final String ACCOUNT_KEY_DISABLED = "disabled";
	public static final String ACCOUNT_KEY_NEEDSYNC = "need_sync";
	public static final String ACCOUNT_KEY_ORDER = "account_order";

	public static final String DATABASE_TABLE_ACCOUNT_CREATE = "create table "
			+ DATABASE_TABLE_ACCOUNT + " (" 
			+ ACCOUNT_KEY_LOCALID + " integer not null primary key autoincrement, "
			+ ACCOUNT_KEY_CLOUDID + " integer default 0, " 
			+ ACCOUNT_KEY_TYPE + " integer, " 
			+ ACCOUNT_KEY_DISABLED + " integer default 0, "
			+ ACCOUNT_KEY_INITBALANCE + " real, " 
			+ ACCOUNT_KEY_REMAIN + " real, " 
			+ ACCOUNT_KEY_RATE + " real, " 
			+ ACCOUNT_KEY_NAME + " text, " 
			+ ACCOUNT_KEY_CURRENCY + " text, "
			+ ACCOUNT_KEY_CUSTOMCURRENCYNAME + " text, " 
			+ ACCOUNT_KEY_NEEDSYNC + " integer default 0, " 
			+ ACCOUNT_KEY_TIMESTAMP + " timestamp default current_timestamp, " 
			+ ACCOUNT_KEY_ORDER + " integer default 0" + ");";

	public static final String DATABASE_TABLE_DELETE = "deleted";

	public static final String DELETE_KEY_ID = "deleted_index";
	public static final String DELETE_KEY_TABLENAME = "table_name";
	public static final String DELETE_KEY_ROWID = "cloud_row_id";
	public static final String DELETE_KEY_TIMESTAMP = "timestamp";

	public static final String DATABASE_TABLE_DELETE_CREATE = "create table "
			+ DATABASE_TABLE_DELETE + " (" 
			+ DELETE_KEY_ID + " integer not null primary key autoincrement, "
			+ DELETE_KEY_TABLENAME + " text not null, " 
			+ DELETE_KEY_ROWID + " integer not null, " 
			+ DELETE_KEY_TIMESTAMP + " timestamp default current_timestamp " + ");";

	public static final String DATABASE_TABLE_CURRENCY = "currency";

	public static final String CURRENCY_KEY_FROM = "currency_from";
	public static final String CURRENCY_KEY_TO = "currency_to";
	public static final String CURRENCY_KEY_RATE = "rate";
	public static final String CURRENCY_KEY_TIMESTAMP = "timestamp";

	public static final String DATABASE_TABLE_CURRENCY_CREATE = "create table "
			+ DATABASE_TABLE_CURRENCY + " ("
			+ CURRENCY_KEY_FROM + " text not null, "
			+ CURRENCY_KEY_TO + " text not null, "
			+ CURRENCY_KEY_RATE + " real, "
			+ CURRENCY_KEY_TIMESTAMP + " timestamp default current_timestamp,"
			+ "primary key (" + CURRENCY_KEY_FROM + ", " + CURRENCY_KEY_TO + ")" + ");";

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
			createAllTables(db);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL("drop table if exists " + DATABASE_TABLE_ACCOUNT);
				db.execSQL("drop table if exists " + DATABASE_TABLE_RECORD);
				db.execSQL("drop table if exists " + DATABASE_TABLE_CATEGORY);
				db.execSQL("drop table if exists " + DATABASE_TABLE_DELETE);
				db.execSQL("drop table if exists " + DATABASE_TABLE_CURRENCY);
			} catch (Exception e) {
				e.printStackTrace();
			}
			onCreate(db);
		}
	}

	private void createDatabase(SQLiteDatabase db, String create){
		try {
			db.execSQL(create);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void createAllTables(SQLiteDatabase db){
		createDatabase(db, DATABASE_TABLE_RECORD_CREATE);
		createDatabase(db, DATABASE_TABLE_CATEGORY_CREATE);
		createDatabase(db, DATABASE_TABLE_ACCOUNT_CREATE);
		createDatabase(db, DATABASE_TABLE_CURRENCY_CREATE);
		createDatabase(db, DATABASE_TABLE_DELETE_CREATE);
	}
	
	public void deleteAllTable(){
		mDB.delete(DATABASE_TABLE_ACCOUNT, null, null);
		mDB.delete(DATABASE_TABLE_CATEGORY, null, null);
		mDB.delete(DATABASE_TABLE_CURRENCY, null, null);
		mDB.delete(DATABASE_TABLE_RECORD, null, null);
		mDB.delete(DATABASE_TABLE_DELETE, null, null);
	}
	public DBManager open() throws SQLException {
		mDB = mDBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDBHelper.close();
	}

	public void exportDB() {
		File sd = Environment.getExternalStorageDirectory();
		File data = Environment.getDataDirectory();
		FileChannel source = null;
		FileChannel destination = null;
		String currentDBPath = "/data/" + "//tw.banck.Banck//" + "//databases//" + DATABASE_NAME;
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

	/*
	 * 
	 * Related to table account
	 * 
	 */
	
	public int getAccountLocalId(int cloudId){
        int local_id = 0;
		if (cloudId == 0)
			return 0;
		String sql = "select `" + ACCOUNT_KEY_LOCALID
				+ "` from `account` where `" + ACCOUNT_KEY_CLOUDID + "` = ?";
		String[] selectionArgs = new String[] { Integer.toString(cloudId) };
		Cursor result = mDB.rawQuery(sql, selectionArgs);
		if(result != null && result.moveToFirst()){
			local_id = result.getInt(result.getColumnIndex(ACCOUNT_KEY_LOCALID));
            result.close();
		}
		return local_id;
	}

	public TriAccount getAccount(int localId, int cloudId){
		return null;
	}
	
	public String getAccountName(int localId){
		String sql = "select `" + ACCOUNT_KEY_NAME
				+ "` from `account` where `" + ACCOUNT_KEY_LOCALID + "` = ?";
		String[] selectionArgs = new String[] { Integer.toString(localId) };
		Cursor result = mDB.rawQuery(sql, selectionArgs);
		String result_string = "";
		if(result.moveToFirst()){
			result_string = result.getString(result.getColumnIndex(ACCOUNT_KEY_NAME));
			result.close();
		}
		return result_string;
	}
	
	private ContentValues getAccountContentValues(TriAccount account) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_CLOUDID, account.getCloudId());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_NAME, account.getName());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_TYPE, account.getType().ordinal());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_INITBALANCE, account.getInitBalance());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_CURRENCY, account.getCurrency());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_REMAIN, account.getRemain());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_TIMESTAMP, account.getTimetamp());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_CUSTOMCURRENCYNAME, account.getCustomName());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_RATE, account.getRate());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_DISABLE, account.isDisabled());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_ORDER, account.getOrder());
		contentValues.put(TriAccount.ACCOUNT_DB_KEY_NEEDSYNC, account.need_update());
		return contentValues;
	}
	
	public TriAccount createAccount(TriAccount account){
		long local_id = mDB.insert(DATABASE_TABLE_ACCOUNT, null, getAccountContentValues(account));
		account.setLocalId((int)local_id);
		return account;
	}
	
	public boolean updateAccount(TriAccount account){
		int localId = account.getLocalId();
		int rowAffected = mDB.update(DATABASE_TABLE_ACCOUNT, getAccountContentValues(account), ACCOUNT_KEY_LOCALID + " = ?", new String[]{Integer.toString(localId)});
		return (rowAffected == 1);
	}
	public boolean deleteAccount(TriAccount account){
		int localId = account.getLocalId();
		int rowAffected = mDB.delete(DATABASE_TABLE_ACCOUNT, ACCOUNT_KEY_LOCALID + " = ?", new String[]{Integer.toString(localId)});
		
		long inserted_delete_id = insertDeleteAccount(account);
		return (rowAffected == 1) & (inserted_delete_id != -1);
	}
	public boolean deleteRecord(TriRecord record){
		int localId = record.getLocalId();
		int rowAffected = mDB.delete(DATABASE_TABLE_RECORD, RECORD_KEY_LOCALID + " = ?", new String[]{Integer.toString(localId)});
		
		long inserted_delete_id = insertDeleteRecord(record, true);
		return (rowAffected == 1) & (inserted_delete_id != -1);
	}
	public long insertDeleteAccount(TriAccount account){
		long delete_id = mDB.insert(DATABASE_TABLE_DELETE, null, getDeleteAccountContentValues(account));
		return delete_id;
	}
	public long insertDeleteRecord(TriRecord record, boolean updateAccountRemain) {
		long delete_id = mDB.insert(DATABASE_TABLE_DELETE, null, getDeleteRecordContentValues(record));
		if(updateAccountRemain){
			updateAccountRemainByType(record.getType(), -1 * (record.getAmount() + record.getFee()), record.getLocalAccountId(), record.getLocalAccountDstId(), record.getAmountDst());
		}
		return delete_id;
	}
	public ContentValues getDeleteAccountContentValues(TriAccount account){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DELETE_KEY_ROWID, account.getCloudId());
		contentValues.put(DELETE_KEY_TABLENAME, DATABASE_TABLE_ACCOUNT);
		contentValues.put(DELETE_KEY_TIMESTAMP, account.getTimetamp());
		return contentValues;
	}
	public ContentValues getDeleteRecordContentValues(TriRecord record){
		ContentValues contentValues = new ContentValues();
		contentValues.put(DELETE_KEY_ROWID, record.getCloudId());
		contentValues.put(DELETE_KEY_TABLENAME, DATABASE_TABLE_RECORD);
		contentValues.put(DELETE_KEY_TIMESTAMP, record.getTimestamp());
		return contentValues;
	}
	
	public Cursor getAccounts() {
		String[] columns = new String[]{
				ACCOUNT_KEY_LOCALID, ACCOUNT_KEY_TYPE, ACCOUNT_KEY_CLOUDID, 
				ACCOUNT_KEY_NAME, ACCOUNT_KEY_INITBALANCE, ACCOUNT_KEY_REMAIN, 
				ACCOUNT_KEY_CURRENCY, ACCOUNT_KEY_CUSTOMCURRENCYNAME, ACCOUNT_KEY_RATE, 
				ACCOUNT_KEY_TIMESTAMP, ACCOUNT_KEY_DISABLED, ACCOUNT_KEY_ORDER, ACCOUNT_KEY_NEEDSYNC };
		
		Cursor result = mDB.query(DATABASE_TABLE_ACCOUNT, columns, null, null, null, null, ACCOUNT_KEY_TYPE +" ASC, " + ACCOUNT_KEY_ORDER + " ASC, " + ACCOUNT_KEY_CLOUDID);
		if(result.moveToFirst())
			return result;
		return null;
	}
	
	public Cursor getAccounts(TriAccountType type){
		String[] columns = new String[]{
				ACCOUNT_KEY_LOCALID, ACCOUNT_KEY_TYPE, ACCOUNT_KEY_CLOUDID, 
				ACCOUNT_KEY_NAME, ACCOUNT_KEY_INITBALANCE, ACCOUNT_KEY_REMAIN, 
				ACCOUNT_KEY_CURRENCY, ACCOUNT_KEY_CUSTOMCURRENCYNAME, ACCOUNT_KEY_RATE, 
				ACCOUNT_KEY_TIMESTAMP, ACCOUNT_KEY_DISABLED, ACCOUNT_KEY_ORDER, ACCOUNT_KEY_NEEDSYNC };
		
		Cursor result = mDB.query(DATABASE_TABLE_ACCOUNT, columns, ACCOUNT_KEY_TYPE + "= ?", new String[]{Integer.toString(type.ordinal())}, null, null, ACCOUNT_KEY_ORDER + " ASC, " + ACCOUNT_KEY_CLOUDID);
		if(result.moveToFirst())
			return result;
		return null;
	}
	

	/*
	 * 
	 * Related to table category
	 * 
	 */
	public int getCategoryLocalId(int cloudId){
		if (cloudId == 0)
			return 0;
		String sql = "select `" + CATEGORY_KEY_LOCALID
				+ "` from `category` where `" + CATEGORY_KEY_CLOUDID + "` = ?";
		String[] selectionArgs = new String[] { Integer.toString(cloudId) };
		Cursor result = mDB.rawQuery(sql, selectionArgs);
		if (result.moveToFirst())
			return result.getInt(result.getColumnIndex(CATEGORY_KEY_LOCALID));
		return 0;
	}
	
	private ContentValues getCategoryContentValues(TriCategory c){
		ContentValues contentValues = new ContentValues();
		contentValues.put(CATEGORY_KEY_NAME, c.getName());
		contentValues.put(CATEGORY_KEY_TYPE, c.getType().ordinal());
		contentValues.put(CATEGORY_KEY_PARENTID, c.getParentId());
		contentValues.put(CATEGORY_KEY_CLOUDID, c.getCloudId());
		contentValues.put(CATEGORY_KEY_NEEDSYNC, c.needSync());
		contentValues.put(CATEGORY_KEY_RANK, c.getRank());
		contentValues.put(CATEGORY_KEY_TIMESTAMP, c.getTimestamp());
		return contentValues;
	}
	public TriCategory createCategory(TriCategory category){
		long localId = mDB.insert(DATABASE_TABLE_CATEGORY, null, getCategoryContentValues(category));
		category.setLocalId((int)localId);
		return category;
	}

	public TriCategory createCategory(String name, int parentId, TriCategoryType type, int cloudId, int rank, int time) {
		TriCategory category = new TriCategory(0, cloudId, parentId, rank, type, name, time);
		return createCategory(category);
	}
	
	public boolean updateCategory(TriCategory category){
		int localId = category.getLocalId();
		int rowAffected = mDB.update(DATABASE_TABLE_CATEGORY, getCategoryContentValues(category), CATEGORY_KEY_LOCALID + " = ?", new String[]{Integer.toString(localId)});
		return (rowAffected == 1);
	}
	
	public Cursor getCategories(){
		String[] columns = new String[]{
				CATEGORY_KEY_LOCALID, CATEGORY_KEY_CLOUDID, CATEGORY_KEY_PARENTID,
				CATEGORY_KEY_RANK, CATEGORY_KEY_TYPE, CATEGORY_KEY_NAME, CATEGORY_KEY_TIMESTAMP,
				CATEGORY_KEY_NEEDSYNC};
		Cursor result = mDB.query(DATABASE_TABLE_CATEGORY, columns, null, null, null, null, null);
		if (result.moveToFirst()){
			return result;
		}
		return null;
	}
	
	public ArrayList<TriCategory> getParentCategory(){
        ArrayList<TriCategory> parents = new ArrayList<TriCategory>();
		String[] columns = new String[]{
				CATEGORY_KEY_LOCALID, CATEGORY_KEY_CLOUDID, CATEGORY_KEY_PARENTID,
				CATEGORY_KEY_RANK, CATEGORY_KEY_TYPE, CATEGORY_KEY_NAME, CATEGORY_KEY_TIMESTAMP,
				CATEGORY_KEY_NEEDSYNC};
		Cursor result = mDB.query(DATABASE_TABLE_CATEGORY, columns, CATEGORY_KEY_PARENTID + " = ?" , new String[]{"0"}, null, null, null);
        Log.d(TAG, "" + result.getCount());
		if (result != null && result.moveToFirst()){
            do{
                TriCategory category = new TriCategory(result);
                parents.add(category);
            }while(result.moveToNext());
            result.close();
		}
		return parents;
	}

	public Cursor getChildCategory(int parentId){
		if (parentId == 0){
			return null;
		}
		String[] columns = new String[]{
				CATEGORY_KEY_LOCALID, CATEGORY_KEY_CLOUDID, CATEGORY_KEY_PARENTID,
				CATEGORY_KEY_RANK, CATEGORY_KEY_TYPE, CATEGORY_KEY_NAME, CATEGORY_KEY_TIMESTAMP,
				CATEGORY_KEY_NEEDSYNC};
		Cursor result = mDB.query(DATABASE_TABLE_CATEGORY, columns, CATEGORY_KEY_PARENTID + " = ? ", new String[]{Integer.toString(parentId)}, null, null, null);
		if (result.moveToFirst()){
			return result;
		}
		return null;
	}

    public ArrayList<ArrayList<TriCategory>> getChildCategories(){

        ArrayList<TriCategory> parents = getParentCategory();
        ArrayList<ArrayList<TriCategory>> categories = new ArrayList<ArrayList<TriCategory>>();
        for (TriCategory c : parents){

           ArrayList<TriCategory> second_categories = new ArrayList<TriCategory>();
           second_categories.add(c);
           Cursor result = getChildCategory(c.getCloudId());
           if (result != null && result.moveToFirst()){
               do{
                   TriCategory second_c = new TriCategory(result);
                   second_categories.add(second_c);
               }while(result.moveToNext());

               result.close();
           }
           categories.add(second_categories);
        }
        return categories;
    }

	/*
	 * 
	 * Related to table record
	 * 
	 */
	


	public ContentValues getRecordContentValues(TriRecord record) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(RECORD_KEY_CLOUDID, record.getCloudId());
		contentValues.put(RECORD_KEY_ACCOUNTID, record.getLocalAccountId());
		contentValues.put(RECORD_KEY_ACCOUNTDSTID, record.getLocalAccountDstId());
		contentValues.put(RECORD_KEY_TYPE, record.getType().ordinal());
		contentValues.put(RECORD_KEY_CATEGORYID, record.getCategoryId());
		contentValues.put(RECORD_KEY_DESC, record.getDesc());
		contentValues.put(RECORD_KEY_AMOUNT, record.getAmount());
		contentValues.put(RECORD_KEY_AMOUNTDST, record.getAmountDst());
		contentValues.put(RECORD_KEY_FEE, record.getFee());
		contentValues.put(RECORD_KEY_DATE, DateHelper.getDateString(record.getDate()));
		contentValues.put(RECORD_KEY_NEEDSYNC, record.isNeedSync());
		contentValues.put(RECORD_KEY_TIMESTAMP, record.getTimestamp());
		return contentValues;
	}
	public TriRecord createRecord(TriRecord record, boolean updateAccountRemain) {
		ContentValues contentValues = getRecordContentValues(record);
		long localId = mDB.insert(DATABASE_TABLE_RECORD, null, contentValues);
		record.setLocalId((int) localId);
		if(updateAccountRemain) {
			updateAccountRemainByType(record.getType(), record.getAmount() + record.getFee(), record.getLocalAccountId(), record.getLocalAccountDstId(), record.getAmountDst());
		}
		return record;
	}
	public boolean updateRecordFromCloud(TriRecord record) {
		ContentValues contentValues = getRecordContentValues(record);
		int row_affected = mDB.update(DATABASE_TABLE_RECORD, contentValues, RECORD_KEY_LOCALID + " = ?", new String[]{Integer.toString(record.getLocalId())});
		return (row_affected >= 1);
	}
	public boolean updateRecordFromLocal(TriRecord record) {
		
		int original_account_src_id = 0;
		int original_account_dst_id = 0; 
		double original_amount = 0; 
		double original_amount_dst = 0;
		double original_fee = 0;
		
		Cursor result = getRecord(record.getLocalId(), 0);
		if (result != null) {
			original_account_dst_id = result.getInt(result.getColumnIndex(RECORD_KEY_ACCOUNTDSTID));
			original_account_src_id = result.getInt(result.getColumnIndex(RECORD_KEY_ACCOUNTID));
			original_amount = result.getDouble(result.getColumnIndex(RECORD_KEY_AMOUNT));
			original_amount_dst = result.getDouble(result.getColumnIndex(RECORD_KEY_AMOUNTDST));
			original_fee = result.getDouble(result.getColumnIndex(RECORD_KEY_FEE));
		}
		
		if (record.getType() == TriCategoryType.TriCategoryTypeExpense) {
			if(original_account_src_id != record.getLocalAccountId()) { 
				updateAddAccountRemain(original_account_src_id, original_amount + original_fee);
				updateDeductAccountRemain(record.getLocalAccountId(), record.getAmount() + record.getFee());
			} else {
				updateDeductAccountRemain(record.getLocalAccountId(), ( record.getAmount() + record.getFee() ) - (original_amount + original_fee));
			}
		} 
		else if (record.getType() == TriCategoryType.TriCategoryTypeIncome) {
			if(original_account_src_id != record.getLocalAccountId()) { 
				updateDeductAccountRemain(original_account_src_id, original_amount + original_fee);
				updateAddAccountRemain(record.getLocalAccountId(), record.getAmount() + record.getFee());
			} else{
				updateAddAccountRemain(record.getLocalAccountId(), ( record.getAmount() + record.getFee() ) - (original_amount + original_fee));
			}
		} 
		else if (record.getType() == TriCategoryType.TriCategoryTypeTransfer) {
			if(original_account_src_id != record.getLocalAccountId()) { 
				updateAddAccountRemain(original_account_src_id, original_amount + original_fee);
				updateDeductAccountRemain(record.getLocalAccountId(), record.getAmount() + record.getFee());
			} else{
				updateDeductAccountRemain(record.getLocalAccountId(), ( record.getAmount() + record.getFee() ) - (original_amount + original_fee));
			}
			if(record.getLocalAccountDstId() != original_account_dst_id){
				updateDeductAccountRemain(original_account_dst_id, original_amount_dst);
				updateAddAccountRemain(record.getLocalAccountDstId(), record.getAmountDst());
			} else{
				updateAddAccountRemain(record.getLocalAccountDstId(), record.getAmountDst() - original_amount_dst);
			}
		} 
		mDB.update(DATABASE_TABLE_RECORD, getRecordContentValues(record), RECORD_KEY_LOCALID + " = ?", new String[]{Integer.toString(record.getLocalId())});
		return false;
	}
	
	private void updateAddAccountRemain(int account_id, double amount){
		String sql = "";
		String[] selectionArgs = new String[]{};
		sql = "update `account` set `" + ACCOUNT_KEY_REMAIN + "` =`"
				+ ACCOUNT_KEY_REMAIN + "` + ? where `" + ACCOUNT_KEY_LOCALID
				+ "` = ?";
		selectionArgs = new String[] { Double.toString(amount), Integer.toString(account_id) };
		mDB.execSQL(sql, selectionArgs);
		return;
	}
	
	private void updateDeductAccountRemain(int account_id, double amount) {
		String sql = "";
		String[] selectionArgs = new String[]{};
		sql = "update `account` set `" + ACCOUNT_KEY_REMAIN + "` =`"
				+ ACCOUNT_KEY_REMAIN + "` - ? where `" + ACCOUNT_KEY_LOCALID
				+ "` = ?";
		selectionArgs = new String[] { Double.toString(amount),
				Integer.toString(account_id) };
		mDB.execSQL(sql, selectionArgs);
		return;
	}
	private void updateAccountRemainByType(TriCategoryType type, double amount_and_fee, int account_src_id, int account_dst_id, double amount_dst){
		if (type == TriCategoryType.TriCategoryTypeExpense) {
			updateDeductAccountRemain(account_src_id, amount_and_fee);
		} 
		else if (type == TriCategoryType.TriCategoryTypeIncome) {
			updateAddAccountRemain(account_src_id, amount_and_fee);
		} 
		else if (type == TriCategoryType.TriCategoryTypeTransfer) {
			updateDeductAccountRemain(account_src_id, amount_and_fee);
			updateAddAccountRemain(account_dst_id, amount_and_fee);
		} 
		return;
	}


	/*
	Function description: Querying Record function
	Input: the cloud id of a accounting record
	Output: the local id of target record
	*/
	public int getLocalRecordId(int cloud_id){
		TriRecord record = getRecordByCloudId(cloud_id);
		return (record != null) ? record.getLocalId() : 0;
	}

	/*
		Function description: query a record using its local id
		Input: the local id of a record
		Output: the TriRecord instance with target local id
	*/
	public TriRecord getRecordByLocalId(int localId) {
		/* assuming queried local id must not be 0 */
		if (localId == 0) throw new AssertionError("querying record by localId, but localId is zero.");

		Cursor result = getRecord(localId, 0);
		return (result != null) ? new TriRecord(result) : null;
	}

	/*
		Function description: query a record using its cloud id
		Input: the cloud id of a record
		Output: the TriRecord instance with target cloud id
	*/
	public TriRecord getRecordByCloudId(int cloudId) {
		/* assuming queried cloud id must not be 0 */
		if (cloudId == 0) throw new AssertionError("querying record by cloudId, but cloudId is zero.");

		Cursor result = getRecord(0, cloudId);
		return (result != null) ? new TriRecord(result) : null;
	}

	/*
		Function description: inner function for query a record using local/cloud id
		Input: local id/cloud id of a record
		Output: if the querying record exists, this function will return a valid cursor, to be null otherwise.
	*/
	private Cursor getRecord(int localId, int cloudId) {
		String[] columns = new String[] { RECORD_KEY_LOCALID,
				RECORD_KEY_CLOUDID, RECORD_KEY_ACCOUNTID,
				RECORD_KEY_ACCOUNTDSTID, RECORD_KEY_TYPE,
				RECORD_KEY_CATEGORYID, RECORD_KEY_DESC, RECORD_KEY_AMOUNT,
				RECORD_KEY_AMOUNTDST, RECORD_KEY_FEE, RECORD_KEY_DATE,
				RECORD_KEY_NEEDSYNC, RECORD_KEY_TIMESTAMP };
		String selection = "";
		String[] selectionArgs;
		if (cloudId == 0) {
			selection = "`" + RECORD_KEY_LOCALID + "` = ?";
			selectionArgs = new String[] { Integer.toString(localId) };
		}
		else if (localId == 0){
			selection = "`" + RECORD_KEY_CLOUDID + "` = ?";
			selectionArgs = new String[] { Integer.toString(cloudId) };
		}
		else{
			selection = "`?` = ? and `?` = ?";
			selectionArgs = new String[]
					{ RECORD_KEY_LOCALID, Integer.toString(localId),
					  RECORD_KEY_CLOUDID, Integer.toString(cloudId) };
		}
		Cursor result = mDB.query(DATABASE_TABLE_RECORD, columns, selection, selectionArgs, null, null, null);
		return ( result != null && result.moveToFirst() ) ? result : null;
	}

    public boolean updateRecord(int localId, int cloudId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECORD_KEY_CLOUDID, cloudId);
        contentValues.put(RECORD_KEY_NEEDSYNC, false);
        int rowAffected = mDB.update(DATABASE_TABLE_RECORD, contentValues, RECORD_KEY_LOCALID + " = ?", new String[]{Integer.toString(localId)});
        return (rowAffected == 1);
    }

	public Cursor getAllRecordInPeriod(long startDate, long endDate){
		
		String[] columns = new String[] { RECORD_KEY_LOCALID,
				RECORD_KEY_CLOUDID, RECORD_KEY_ACCOUNTID,
				RECORD_KEY_ACCOUNTDSTID, RECORD_KEY_TYPE,
				RECORD_KEY_CATEGORYID, RECORD_KEY_DESC, RECORD_KEY_AMOUNT,
				RECORD_KEY_AMOUNTDST, RECORD_KEY_FEE, RECORD_KEY_DATE,
				RECORD_KEY_NEEDSYNC, RECORD_KEY_TIMESTAMP };
		String selection = RECORD_KEY_TIMESTAMP + " >= ? and " + RECORD_KEY_TIMESTAMP + " >= ? ";
		String[] selectionArgs = new String[] {
				Double.toString(startDate / 1000L),
				Double.toString(endDate / 1000L) };
		String orderBy = RECORD_KEY_DATE + " DESC, " + RECORD_KEY_TIMESTAMP + " DESC";
		Cursor result = mDB.query(DATABASE_TABLE_RECORD, columns, selection, selectionArgs, null, null, orderBy);
		if(result.moveToFirst()){
			return result;
		}
		return null;
	}
	
	public Cursor getRecordInPeriod(String startDate, String endDate){
		String command = "SELECT `record`.`" + RECORD_KEY_LOCALID + "`, `record`.`"
				+ RECORD_KEY_TYPE + "`, `" + RECORD_KEY_CATEGORYID + "`, `"
				+ RECORD_KEY_DESC + "`, `" + RECORD_KEY_AMOUNT + "`, `"
				+ RECORD_KEY_ACCOUNTID + "`, `" + RECORD_KEY_DATE + "`, `"
				+ RECORD_KEY_ACCOUNTDSTID + "`, `category`.`" + CATEGORY_KEY_NAME + "`, `record`.`" 
				+ RECORD_KEY_TIMESTAMP +"`, `record`.`" 
				+ RECORD_KEY_AMOUNTDST+"`, `record`.`" 
				+ RECORD_KEY_FEE+"`, `record`.`" + RECORD_KEY_NEEDSYNC + "`, `record`.`" + RECORD_KEY_CLOUDID  
				+ "` from `record` LEFT OUTER JOIN `category` on `record`.`" + RECORD_KEY_CATEGORYID + "` = `category`.`" + CATEGORY_KEY_LOCALID + "`" + " where `"
				+ RECORD_KEY_DATE + "` >= \"" + startDate+ "\" AND `"
				+ RECORD_KEY_DATE + "` <= \"" + endDate + "\" order by `"
				+ RECORD_KEY_DATE + "` DESC, `record`.`" + RECORD_KEY_TIMESTAMP
				+ "` DESC";
		Cursor result = mDB.rawQuery(command, null);
        Log.d(TAG, "get record in period: " + command);
		if (result.moveToFirst()) {
			return result;
		}
		return null;
	}
	
	public Cursor getRecordInPeriodWithType(String startDate, String endDate, TriCategoryType type) {
//		String[] columns = new String[]{
//				RECORD_KEY_ACCOUNTDSTID, RECORD_KEY_LOCALID, RECORD_KEY_CLOUDID,
//				RECORD_KEY_ACCOUNTID, RECORD_KEY_ACCOUNTDSTID, RECORD_KEY_CATEGORYID,
//				RECORD_KEY_TYPE, RECORD_KEY_AMOUNT, RECORD_KEY_DESC, RECORD_KEY_DATE,
//				RECORD_KEY_TIMESTAMP, RECORD_KEY_AMOUNTDST, RECORD_KEY_FEE, RECORD_KEY_NEEDSYNC
//		};
		String command = "SELECT `record`.`" + RECORD_KEY_LOCALID + "`, `record`.`"
				+ RECORD_KEY_TYPE + "`, `" + RECORD_KEY_CATEGORYID + "`, `"
				+ RECORD_KEY_DESC + "`, `" + RECORD_KEY_AMOUNT + "`, `"
				+ RECORD_KEY_ACCOUNTID + "`, `" + RECORD_KEY_DATE + "`, `"
				+ RECORD_KEY_ACCOUNTDSTID + "`, `category`.`" + CATEGORY_KEY_NAME + "`, `record`.`" + RECORD_KEY_TIMESTAMP +"`, `record`.`" 
				+ RECORD_KEY_AMOUNTDST+"`, `record`.`" 
				+ RECORD_KEY_FEE+"`, `record`.`" + RECORD_KEY_NEEDSYNC 
				+ "`, `record`.`" + RECORD_KEY_CLOUDID  
				+ "` from `record` LEFT OUTER JOIN `category` on `record`.`" + RECORD_KEY_CATEGORYID + "` = `category`.`" + CATEGORY_KEY_LOCALID + "`" + " where `record`.`"+  RECORD_KEY_TYPE+"`= \"" + type.ordinal() + "\" AND `"
				+ RECORD_KEY_DATE + "` >= \"" + startDate+ "\" AND `"
				+ RECORD_KEY_DATE + "` <= \"" + endDate + "\" order by `"
				+ RECORD_KEY_DATE + "` DESC, `record`.`" + RECORD_KEY_TIMESTAMP
				+ "` DESC";
//		String[] selectionArgs = new String[] { startDate, endDate, Integer.toString(type.ordinal()) };
//		String orderBy = RECORD_KEY_DATE + " DESC, " + RECORD_KEY_TIMESTAMP + " DESC";
//		Cursor result = mDB.query(DATABASE_TABLE_RECORD, columns,
//				RECORD_KEY_DATE + " >= ? and " + RECORD_KEY_DATE + " <= ? and " + RECORD_KEY_TYPE + " = ?",
//				selectionArgs, null, null, orderBy);
		Cursor result = mDB.rawQuery(command, null);
//		Log.d(TAG, " " + startDate + ", " + endDate + " :" + result.getCount());
		Log.d(TAG, command);
		if (result.moveToFirst()) {
			return result;
		}
		return null;
	}
	public ArrayList<TriRecord> getRecordByAccount(TriAccount account) {

        ArrayList<TriRecord> records = new ArrayList<TriRecord>();
		String sql = "SELECT `record`.`" + RECORD_KEY_LOCALID + "`, `record`.`"
				+ RECORD_KEY_TYPE + "`, `" + RECORD_KEY_CATEGORYID + "`, `"
				+ RECORD_KEY_DESC + "`, `" + RECORD_KEY_AMOUNT + "`, `"
				+ RECORD_KEY_ACCOUNTID + "`, `" + RECORD_KEY_DATE + "`, `"
				+ RECORD_KEY_ACCOUNTDSTID + "`, `category`.`" + CATEGORY_KEY_NAME + "`, `record`.`" + RECORD_KEY_TIMESTAMP +"`, `record`.`" 
				+ RECORD_KEY_AMOUNTDST+"`, `record`.`" 
				+ RECORD_KEY_FEE+"`, `record`.`" + RECORD_KEY_NEEDSYNC 
				+ "`, `record`.`" + RECORD_KEY_CLOUDID  
				+ "` from `record` LEFT OUTER JOIN `category` on `record`.`" + RECORD_KEY_CATEGORYID + "` = `category`.`" + CATEGORY_KEY_LOCALID + "`" 
				+ " where `record`.`"+  RECORD_KEY_ACCOUNTID+"`= \"" + account.getLocalId() + "\" order by `"
				+ RECORD_KEY_DATE + "` DESC, `record`.`" + RECORD_KEY_TIMESTAMP
				+ "` DESC";
		Cursor result = mDB.rawQuery(sql, null);
		Log.d(TAG, sql);

		if(result.moveToFirst()){
            do{
				TriRecord record = new TriRecord(result);
                String category_name = result.getString(result.getColumnIndex(DBManager.CATEGORY_KEY_NAME));

                if(record.getType() == TriCategoryType.TriCategoryTypeTransfer){
                    record.setCategoryName("123");
                }
                else if(category_name == null || category_name.equals("")){
                    record.setCategoryName("123");
                }
                else{
                    record.setCategoryName(category_name);
                }

                records.add(record);
            }while(result.moveToNext());
            result.close();

            for(TriRecord record : records){
                record.setLocalAccountName(getAccountName(record.getLocalAccountId()));
                record.setLocalAccountNameDst(getAccountName(record.getLocalAccountDstId()));
            }
		}
		return records;
	}

    public ArrayList<TriRecord> getRecordByCategory(TriCategory category){

        ArrayList<TriRecord> records = new ArrayList<TriRecord>();
        String sql = "SELECT `record`.`" + RECORD_KEY_LOCALID + "`, `record`.`"
                + RECORD_KEY_TYPE + "`, `" + RECORD_KEY_CATEGORYID + "`, `"
                + RECORD_KEY_DESC + "`, `" + RECORD_KEY_AMOUNT + "`, `"
                + RECORD_KEY_ACCOUNTID + "`, `" + RECORD_KEY_DATE + "`, `"
                + RECORD_KEY_ACCOUNTDSTID + "`, `category`.`" + CATEGORY_KEY_NAME + "`, `record`.`" + RECORD_KEY_TIMESTAMP +"`, `record`.`"
                + RECORD_KEY_AMOUNTDST+"`, `record`.`"
                + RECORD_KEY_FEE+"`, `record`.`" + RECORD_KEY_NEEDSYNC
                + "`, `record`.`" + RECORD_KEY_CLOUDID
                + "` from `record` LEFT OUTER JOIN `category` on `record`.`" + RECORD_KEY_CATEGORYID + "` = `category`.`" + CATEGORY_KEY_LOCALID + "`"
                + " where `record`.`"+  RECORD_KEY_CATEGORYID+"`= \"" + category.getLocalId() + "\" order by `"
                + RECORD_KEY_DATE + "` DESC, `record`.`" + RECORD_KEY_TIMESTAMP
                + "` DESC";
        Cursor result = mDB.rawQuery(sql, null);
        Log.d(TAG, sql);
        if(result.moveToFirst()){
            do{
				TriRecord record = new TriRecord(result);
                String category_name = result.getString(result.getColumnIndex(DBManager.CATEGORY_KEY_NAME));

                if(record.getType() == TriCategoryType.TriCategoryTypeTransfer){
                    record.setCategoryName("123");
                }
                else if(category_name == null || category_name.equals("")){
                    record.setCategoryName("123");
                }
                else{
                    record.setCategoryName(category_name);
                }

                records.add(record);
            }while(result.moveToNext());
            result.close();

			for(TriRecord record : records){
                record.setLocalAccountName(getAccountName(record.getLocalAccountId()));
                record.setLocalAccountNameDst(getAccountName(record.getLocalAccountDstId()));
            }

        }
        return records;
    }

	public Cursor getAllRecordInPeriodWithType(long startDate, long endDate, TriCategoryType type){
		
		String command = "SELECT `record`.`" + RECORD_KEY_LOCALID + "`, `record`.`"
				+ RECORD_KEY_TYPE + "`, `" + RECORD_KEY_CATEGORYID + "`, `"
				+ RECORD_KEY_DESC + "`, `" + RECORD_KEY_AMOUNT + "`, `"
				+ RECORD_KEY_ACCOUNTID + "`, `" + RECORD_KEY_DATE + "`, `"
				+ RECORD_KEY_ACCOUNTDSTID +"`, `category`.`" + CATEGORY_KEY_NAME
				+ "` from `record` where `record`.`"+RECORD_KEY_TYPE+"`= \"" + type.ordinal() + "\" AND `"
				+ RECORD_KEY_DATE + "` >= \"" + startDate / 1000L+ "\" AND `"
				+ RECORD_KEY_DATE + "` <= \"" + endDate / 1000L+ "\" order by `"
				+ RECORD_KEY_DATE + "` DESC, `record`.`" + RECORD_KEY_TIMESTAMP
				+ "` DESC";
		Log.d(TAG, "get all record in a period: " + command);
		Cursor result = mDB.rawQuery(command, null);
		if(result.moveToFirst()){
			return result;
		}
		return null;
	}

	public ArrayList<TriRecord> getUnSyncRecords(){
		ArrayList<TriRecord> uploadedRecords = new ArrayList<TriRecord>();
		Cursor c = queryUnSyncRecords();

		if(c!=null){
			do{
				TriRecord record = new TriRecord(c);
				uploadedRecords.add(record);
				/* Assert check for sync-needed record only */
				if (!record.isNeedSync()) throw new AssertionError("Get a synced record when querying unsync one!");
				Log.d(TAG, "unsync record: " + record.toString() + " added");
			}while(c.moveToNext());
			c.close();
		}

		return uploadedRecords;
	}

    public Cursor queryUnSyncRecords(){

		String command = "SELECT `record`.`" + RECORD_KEY_LOCALID + "`, `record`.`"
                + RECORD_KEY_TYPE + "`, `" + RECORD_KEY_CATEGORYID + "`, `"
                + RECORD_KEY_DESC + "`, `" + RECORD_KEY_AMOUNT + "`, `"
                + RECORD_KEY_ACCOUNTID + "`, `" + RECORD_KEY_DATE + "`, "
                + "`a1`.`" + ACCOUNT_KEY_CLOUDID +"` as a1_cloud_id, `a2`.`" + ACCOUNT_KEY_CLOUDID + "` as a2_cloud_id, `"
                + RECORD_KEY_ACCOUNTDSTID +"`, `category`.`" + CATEGORY_KEY_NAME + "`, `category`.`" + CATEGORY_KEY_CLOUDID  + "` as c_cloud_id, `record`.`"
                + RECORD_KEY_TIMESTAMP +"`, `record`.`"
                + RECORD_KEY_AMOUNTDST+"`, `record`.`"
                + RECORD_KEY_FEE+"`, `record`.`" + RECORD_KEY_NEEDSYNC + "`, `record`.`" + RECORD_KEY_CLOUDID
                + "`as r_cloud_id from `record` LEFT OUTER JOIN `category` on `record`.`"+RECORD_KEY_CATEGORYID + "` = `category`.`" + CATEGORY_KEY_LOCALID
                + "` LEFT OUTER JOIN `account` a1 on `record`.`" + RECORD_KEY_ACCOUNTID + "` = `a1`.`" + ACCOUNT_KEY_LOCALID
                + "` LEFT OUTER JOIN `account` a2 on `record`.`" + RECORD_KEY_ACCOUNTDSTID + "` = `a2`.`" + ACCOUNT_KEY_LOCALID
                + "` where `record`.`" + RECORD_KEY_NEEDSYNC+"` = 1";
		String[] args = new String[]{};

		Log.d(TAG, "Querying all sync-needed records by command: " + command);

		Cursor result = mDB.rawQuery(command, args);
        if(result.moveToFirst()){
            return result;
        }

        return null;
    }

	/*
	 * 
	 * Related to table currency
	 * 
	 */

	public boolean isCurrencyExist(String from, String to) {
		String[] columns = new String[]{CURRENCY_KEY_FROM, CURRENCY_KEY_RATE, CURRENCY_KEY_TO, CURRENCY_KEY_TIMESTAMP};
		String selection = CURRENCY_KEY_FROM + " = ? and " + CURRENCY_KEY_TO + " = ?";
		String[] selectionArgs = new String[]{from, to};
		Cursor result = mDB.query(DATABASE_TABLE_CURRENCY, columns, selection, selectionArgs, null, null, null);
		if(result.moveToFirst()){
			return true;
		}
		return false;
	}
	public boolean createCurrency(String from, double rate, String to,
								  int time) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(CURRENCY_KEY_FROM, from);
		contentValues.put(CURRENCY_KEY_RATE, rate);
		contentValues.put(CURRENCY_KEY_TO, to);
		contentValues.put(CURRENCY_KEY_TIMESTAMP, time);

		mDB.insert(DATABASE_TABLE_CURRENCY, null, contentValues);
		return false;
	}
	public boolean updateCurrency(String from, String to, double rate, int time){
		String whereClause = CURRENCY_KEY_FROM + " = ? and " + CURRENCY_KEY_TO + " = ? ";
		String[] whereArgs = new String[]{ from, to } ;
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(CURRENCY_KEY_RATE, rate);
		contentValues.put(CURRENCY_KEY_TIMESTAMP, time);
		
		int row_affected = mDB.update(DATABASE_TABLE_CURRENCY, contentValues, whereClause, whereArgs);
		return (row_affected >= 1); 
	}

	/*
	 * 
	 * Utilities
	 * 
	 */

	public double totalBalance() {
		String command = "SELECT SUM(IFNULL(`currency`.`"
				+ CURRENCY_KEY_RATE
				+ "`, `account`.`"
				+ ACCOUNT_KEY_RATE
				+ "`) * `account`.`"
				+ ACCOUNT_KEY_REMAIN
				+ "`) AS `sum` FROM `account` LEFT OUTER JOIN `currency` ON `account`.`currency`=`currency`.`"
				+ CURRENCY_KEY_TO + "` AND `currency`.`" + CURRENCY_KEY_FROM
				+ "`= \"TWD\"";
		//Log.d(TAG, "get total balance: " + command);
		Cursor result = mDB.rawQuery(command, null);
		if (result != null && result.moveToFirst()) {
			double sum = result.getDouble(0);
			return sum;
		}
		return 0;
	}
	
	public double totalBalanceData(long startDate, long endDate, TriCategoryType type, String currency) {
		String command = "SELECT SUM(IFNULL(`currency`.`"
				+ CURRENCY_KEY_RATE
				+ "`, `account`.`"
				+ ACCOUNT_KEY_RATE
				+ "`) * `account`.`"
				+ ACCOUNT_KEY_REMAIN
				+ "`) AS `sum` FROM `record` LEFT OUTER JOIN `account` ON `account`.`"
				+ ACCOUNT_KEY_LOCALID + "` = `record`.`" + RECORD_KEY_ACCOUNTID
				+ "` LEFT OUTER JOIN `currency` on `account`.`"
				+ ACCOUNT_KEY_CURRENCY + "`=`currency`.`" + CURRENCY_KEY_TO
				+ "` AND `currency`.`" + CURRENCY_KEY_FROM
				+ "` = \""+currency+"\" WHERE `record`.`" + RECORD_KEY_TYPE + "` = "
				+ type.ordinal() + " AND `record`.`" + RECORD_KEY_TIMESTAMP + "` >= \""
				+ startDate / 1000L + "\" AND `record`.`" + RECORD_KEY_TIMESTAMP
				+ "` <= \"" + endDate / 1000L + "\"";
		Log.d(TAG, "get total balance in a period: " + command);
		Cursor result = mDB.rawQuery(command, null);
		if (result.moveToFirst()) {
			double sum = result.getDouble(0);
			return sum;
		}
		return 0;
	}
	public double totalBalanceData(String startDate, String endDate, TriCategoryType type, String currency) {
		String command = "SELECT SUM(IFNULL(`currency`.`"
				+ CURRENCY_KEY_RATE
				+ "`, `account`.`"
				+ ACCOUNT_KEY_RATE
				+ "`) * `record`.`"
				+ RECORD_KEY_AMOUNT
				+ "`) AS `sum` FROM `record` LEFT OUTER JOIN `account` ON `account`.`"
				+ ACCOUNT_KEY_LOCALID + "` = `record`.`" + RECORD_KEY_ACCOUNTID
				+ "` LEFT OUTER JOIN `currency` on `account`.`"
				+ ACCOUNT_KEY_CURRENCY + "`=`currency`.`" + CURRENCY_KEY_TO
				+ "` AND `currency`.`" + CURRENCY_KEY_FROM
				+ "` = \""+currency+"\" WHERE `record`.`" + RECORD_KEY_TYPE + "` = "
				+ type.ordinal() + " AND `record`.`" + RECORD_KEY_DATE + "` >= \""
				+ startDate  + "\" AND `record`.`" + RECORD_KEY_DATE
				+ "` <= \"" + endDate + "\"";
		Log.d(TAG, "get total balance in a period: " + command);
		Cursor result = mDB.rawQuery(command, null);
		if (result.moveToFirst()) {
			double sum = result.getDouble(0);
			return sum;
		}
		return 0;
	}
}
