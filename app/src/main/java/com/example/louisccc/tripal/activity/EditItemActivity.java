package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    private ImageView mItemThumbnailCoverImageView;
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
        mItemThumbnailCoverImageView = (ImageView) this.findViewById(R.id.item_edit_cover);
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
                if (f.getLocalId() == mItem.getOwner() ) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_record_confirm:

                if ( mOwner == null ||
                        mItemNameEditText.getText().toString().equals("") ||
                        mItemAmountTextView.getText().toString().equals("") ||
                        mTrip == null ||
                        mItemNoteTextView.getText().toString().equals("")
                    ) {
                    AlertDialog.Builder d_friend = new AlertDialog.Builder(EditItemActivity.this)
                            .setMessage("小元 alert, 有東西沒輸入唷")
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
                    return false;
                }
                if (mItem == null) {
                    Assert.assertTrue( mOwner != null );
                    TriItem it = new TriItem(
                            mItemNameEditText.getText().toString(),
                            Double.parseDouble(mItemAmountTextView.getText().toString()),
                            mOwner.getLocalId(),
                            mTrip.getLocalId(),
                            0, // category id
                            mItemNoteTextView.getText().toString(),
                            DateHelper.getDate(mItemTimeStampTextView.getText().toString())
                    );

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

                    DBManager db = new DBManager(this);
                    try {
                        db.open();
                        long ret = db.createItem(it);
                        it.setlocalId(ret);
                        ((TriApplication)getApplication()).setgItems(db.getItems());
                        for(TriDept dept : depts ) {
                            ret = db.createDept(dept);
                            Assert.assertTrue( ret != -1 );
                        }
                        db.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // set mItem's inner variables
                }
                this.finish();
                break;
            case R.id.add_record_cancel:
                this.finish();
                break;
            default:
                break;
        }
        return false;
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
}