package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.DBManager;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriDept;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;
import com.example.louisccc.tripal.utility.DeptsAdapter;

import junit.framework.Assert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by louisccc on 2/12/16.
 */
public class EditItemActivity extends Activity {

    private TriItem mItem;
    private TriFriend mOwner = null; // decided in this edit item page
    private Date mDate = null;
    private TriTrip mTrip = null; // should be given if item can only be added in the trip

    /* Views members */
    private ImageView mItemThumbnailImageView;
    private TextView mItemTimeStampTextView;
    private Button mItemTimeStampButton;
    private TextView mItemLocationTextView;
    private TextView mItemTripTextView;
    private Button mItemTripButton;
    private EditText mItemNameEditText;
    private EditText mItemNoteTextView;
    private EditText mItemAmountTextView;
    private TextView mItemOwnerTextView;
    private Button mItemOwnerButton;
    private ListView mDeptsListView;
    private DeptsAdapter mDeptsAdapter;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mItemThumbnailImageView = (ImageView) this.findViewById(R.id.item_edit_thumbnail);
        mItemTimeStampTextView = (TextView) this.findViewById(R.id.item_edit_timestamp);
        mItemTimeStampButton = (Button) this.findViewById(R.id.item_edit_timestamp_change);
        mItemTripTextView = (TextView) this.findViewById(R.id.item_edit_trip);
        mItemTripButton = (Button) this.findViewById(R.id.item_edit_trip_change);
        mItemNameEditText = (EditText) this.findViewById(R.id.item_edit_item_name);
        mItemNoteTextView = (EditText) this.findViewById(R.id.item_edit_note);
        mItemAmountTextView = (EditText) this.findViewById(R.id.item_edit_amount);
        mItemOwnerButton = (Button) this.findViewById(R.id.item_edit_owner_change);
        mItemOwnerTextView = (TextView) this.findViewById(R.id.item_edit_owner);

        mItem = getIntent().getParcelableExtra("item");
        mTrip = getIntent().getParcelableExtra("trip");
        Assert.assertTrue(mTrip != null);

        mItemTripTextView.setText("Trip: " + mTrip.getName());
        if (mItem != null) { // this is to edit an item
            for (TriFriend f : mTrip.getMembers((TriApplication) getApplication()) ){
                if (f.getLocalId() == mItem.getOwnerId() ) {
                    mOwner = f;
                    break;
                }
            } // TODO optimize

            setTitle("Edit Item");
            mItemNameEditText.setText(mItem.getName());
            mItemNoteTextView.setText(mItem.getNote());
            mItemAmountTextView.setText( Double.toString( mItem.getAmount() ) );
            mItemOwnerTextView.setText( "Owner: " + mOwner.getName() );
        }
        else {
            setTitle("New Item");
            mItemOwnerTextView.setText("Owners-> amy: " + ((TriApplication)getApplication()).getgFriends().get(0).getLocalId() + " astrid: " +((TriApplication)getApplication()).getgFriends().get(1).getLocalId() );
        }

        mDate = DateHelper.getCalendarDay(0).getTime(); // today
        mItemTimeStampTextView.setText(DateHelper.getTodayDateString());
        mItemTimeStampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                c.setTime( mDate );
                showDatePickerDialog(c);
            }
        });

        mItemTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditItemActivity.this);
                builder.setTitle("select the trip");
                ArrayList<String> listitems = new ArrayList<String>();
                for ( TriTrip trip : ((TriApplication)getApplication()).getgTrips() ) {
                    listitems.add(trip.getName());
                }
                final CharSequence[] charSequence = listitems.toArray(new CharSequence[listitems.size()]);
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTrip = ((TriApplication)getApplication()).getgTrips().get(which);
                        mItemTripTextView.setText( "Trip: " + mTrip.getName() );
                    }
                });
                builder.show();
            }
        });
        mItemOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder d = new AlertDialog.Builder(EditItemActivity.this);
                d.setTitle("select the owner");
                ArrayList<String> listitems = new ArrayList<String>();
                for (TriFriend f : mTrip.getMembers((TriApplication)getApplication()) ) {
                    listitems.add(f.getName());
                }
                final CharSequence[] charSequence = listitems.toArray(new CharSequence[listitems.size()]);
                d.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOwner = mTrip.getMembers((TriApplication) getApplication()).get(which);
                        mItemOwnerTextView.setText( "Owner: " + mOwner.getName() );
                    }
                });
                d.show();
            }
        });

        mDeptsListView = (ListView) this.findViewById(R.id.item_edit_depts);
        mDeptsAdapter = new DeptsAdapter(this, R.layout.activity_depts_list_item, mTrip.getMembers((TriApplication)getApplication()), mItem );
        mDeptsListView.setAdapter(mDeptsAdapter);
        mDeptsListView.addHeaderView(DateHelper.getTextViewWithText(this, "Depts : Total " + ((TriApplication)getApplication()).getgDepts().size()) , null, false);
        mDeptsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    public void btn_thumbnail_click(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_edit_item_dialog);
        dialog.setTitle("Alert Dialog View");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnChoosePath)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeGallery();
                    }
                });
        dialog.findViewById(R.id.btnTakePhoto)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeTakePhoto();
                    }
                });
        dialog.show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_record_confirm:
                String name = mItemNameEditText.getText().toString();
                String note = mItemNoteTextView.getText().toString();
                Double amount = Double.parseDouble( mItemAmountTextView.getText().toString() );
                Date date = DateHelper.getDate( mItemTimeStampTextView.getText().toString() );
                if ( mOwner == null || name.equals("") || amount < 0 || mTrip == null || note.equals("") ) {
                    AlertDialog.Builder d_friend = new AlertDialog.Builder(EditItemActivity.this)
                            .setMessage("小元 alert, 有東西沒輸入唷: " + mOwner + ", " + name + ", " + amount + ", " + mTrip + ", " + note)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })
                            .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    AlertDialog dialog_friend = d_friend.create();
                    dialog_friend.show();
                    return true;
                }
                if (mItem == null) {
                    TriItem it = new TriItem( name, amount, mOwner.getLocalId(), mTrip.getLocalId(), 0/* cat id*/, note, date );
                    DBManager db = new DBManager(this);

                    try {
                        db.open();
                        long ret = db.createItem(it);
                        it.setLocalId(ret);
                        ((TriApplication)getApplication()).refreshGlobals();
                        db.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    ArrayList<TriDept> depts = new ArrayList<TriDept>();
                    for (int i = 0; i < mDeptsAdapter.getCount(); i++ ){
                        View v = mDeptsListView.getChildAt(i+1);
                        EditText curr_balance = (EditText) v.findViewById(R.id.ItemAmount);
                        EditText proportion = (EditText) v.findViewById(R.id.ItemProportion);

                        TriDept dept = new TriDept(it, mDeptsAdapter.getItem(i),
                                Double.parseDouble(proportion.getText().toString()),
                                Double.parseDouble(curr_balance.getText().toString())
                        );
                        depts.add(dept);
                    }


                    try {
                        db.open();
                        for(TriDept dept : depts ) {
                            long ret = db.createDept(dept);
                            Assert.assertTrue( ret != -1 );
                            dept.setLocalId(ret);
                        }
                        ((TriApplication)getApplication()).refreshGlobals();
                        db.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    mItem.setName( name );
                    mItem.setAmount( amount );
                    mItem.setOwnerId( mOwner.getLocalId() );
                    mItem.setTripId( mTrip.getLocalId() );
//                    mItem.setCategoryId( 0 );
                    mItem.setNote( note );
                    mItem.setDate( date );

                    DBManager db = new DBManager(this);
                    try {
                        db.open();
                        long ret = db.updateItem(mItem);
                        Assert.assertTrue( ret == 1 );
                        ((TriApplication)getApplication()).refreshGlobals();
                        db.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    ArrayList<TriDept> depts = new ArrayList<TriDept>();
                    for (int i = 0; i < mDeptsAdapter.getCount(); i++ ){
                        View v = mDeptsListView.getChildAt(i+1);
                        EditText curr_balance = (EditText) v.findViewById(R.id.ItemAmount);
                        EditText proportion = (EditText) v.findViewById(R.id.ItemProportion);

                        TriDept dept = new TriDept(mItem, mDeptsAdapter.getItem(i),
                                Double.parseDouble(proportion.getText().toString()),
                                Double.parseDouble(curr_balance.getText().toString())
                        );
                        depts.add(dept);
                    }

                    try {
                        db.open();
                        for(TriDept dept : depts ) {
                            if (db.isDeptExist(dept)) {
                                long ret = db.updateDept(dept);
                                Assert.assertTrue( ret == 1 );
                            }
                            else {
                                long ret = db.createDept(dept);
                                Assert.assertTrue(ret != -1);
                                dept.setLocalId(ret);
                            }
                        }
                        ((TriApplication)getApplication()).refreshGlobals();
                        db.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                this.finish();
                break;
            case R.id.add_record_cancel:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE &&
                        resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        mItemThumbnailImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, THUMBSIZE, THUMBSIZE));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE &&
                        resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String picturePath = cursor.getString(column_index_data);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mCapturedImageURI);
                        mItemThumbnailImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, THUMBSIZE, THUMBSIZE));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public void showDatePickerDialog(Calendar c) {
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mItemTimeStampTextView.setText( DateHelper.getDateString(year, monthOfYear + 1, dayOfMonth) );
                        mDate = DateHelper.getDate(year, monthOfYear + 1, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }


    private static final int THUMBSIZE = 96;
    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    /**
     * take a photo
     */
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }


}