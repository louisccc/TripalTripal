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
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.utility.DateHelper;

import junit.framework.Assert;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by louisccc on 2/12/16.
 */
public class EditItemActivity extends Activity {

    TriItem mItem;
    ImageView mItemThumbnailImageView;
    ImageView mItemThumbnailCoverImageView;
    TextView mItemTimeStampTextView;
    Button mItemTimeStampButton;
    TextView mItemLocationTextView;
    EditText mItemTripTextView;
    EditText mItemNoteTextView;

    EditText mItemAmountTextView;
    TextView mItemOwnerTextView;

    ListView mParticipantsListView;
//TODO    ParticipantsAdapter mParticipantsAdapter;

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

        mItemTripTextView = (EditText) this.findViewById(R.id.item_edit_trip);
        mItemNoteTextView = (EditText) this.findViewById(R.id.item_edit_note);
        mItemAmountTextView = (EditText) this.findViewById(R.id.item_edit_amount);
        mItemOwnerTextView = (TextView) this.findViewById(R.id.item_edit_owner);

        mItem = getIntent().getParcelableExtra("item");
        if (mItem != null) {
            setTitle("Edit Item");
            mItemNoteTextView.setText(mItem.getNote());
            mItemAmountTextView.setText( Double.toString( mItem.getAmount() ) );
            mItemOwnerTextView.setText( "Owner_id: " + mItem.getOwner() );
            // TODO location
        }
        else {
            setTitle("New Item");
            mItemOwnerTextView.setText("Owners-> amy: " + ((TriApplication)getApplication()).getgFriends().get(0).getLocalId() + " astrid: " +((TriApplication)getApplication()).getgFriends().get(1).getLocalId() );
        }
        mItemTimeStampTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mItemTimeStampTextView.setText(DateHelper.getCalendarDay(0).get(Calendar.YEAR) + "-" +
                                        (DateHelper.getCalendarDay(0).get(Calendar.MONTH) + 1) + "-" +
                                        DateHelper.getCalendarDay(0).get(Calendar.DAY_OF_MONTH)
        );
        mItemTimeStampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                c.setTime( DateHelper.getDate(mItemTimeStampTextView.getText().toString()) );
                showDatePickerDialog(c);
            }
        });
        mItemOwnerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder d = new AlertDialog.Builder(EditItemActivity.this);
                LayoutInflater inflater = EditItemActivity.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_friend_picker, null);
                d.setView(dialogView);
                d.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText text = (EditText) dialogView.findViewById(R.id.edittext);
                        mItemOwnerTextView.setText(text.getText().toString());
                    }
                });
                d.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = d.create();
                dialog.show();

            }
        });
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
                if (mItem == null) {
                    TriItem i = new TriItem(
                            mItemTripTextView.getText().toString(),
                            Double.parseDouble(mItemAmountTextView.getText().toString()),
                            Integer.parseInt(mItemOwnerTextView.getText().toString()),
                            ((TriApplication)getApplication()).getgTrips().get(0).getLocalId(),
                            0,
                            mItemNoteTextView.getText().toString(),
                            DateHelper.getDate(mItemTimeStampTextView.getText().toString())
                    );

                    DBManager db = new DBManager(this);
                    try {
                        db.open();
                        Assert.assertTrue( db.createItem(i) != -1 );
                        ((TriApplication)getApplication()).setgItems(db.getItems());
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
    private int mYear, mMonth, mDay;
    public void showDatePickerDialog(Calendar c) {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mItemTimeStampTextView.setText(year + "-" + (monthOfYear + 1) + "-"
                                + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }
}