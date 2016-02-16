package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.model.DBManager;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.utility.FriendsAdapter;
import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.utility.MembersAdapter;
import com.example.louisccc.tripal.utility.RecordsAdapter;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

import junit.framework.Assert;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class TripActivity extends Activity {

    TriTrip mTrip;
    TextView mTripNameTextView;
    TextView mRemainTimesTextView;
    TextView mTotalSpentTextView;
    TextView mCurrBalanceTextView;


    ListView mMembersListView;
    MembersAdapter mMembersAdapter;
    ListView mRecordsListView;
    RecordsAdapter mRecordsAdapter;


    ImageButton mCheckImageButton;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        mTrip = getIntent().getParcelableExtra("trip");
        Assert.assertTrue(mTrip != null);

        mTripNameTextView = (TextView) this.findViewById(R.id.trip_name);
        mTripNameTextView.setText(mTrip.getName());

        mTotalSpentTextView = (TextView) this.findViewById(R.id.trip_total_spent);
        mTotalSpentTextView.setText("Total Spent: $" + mTrip.getTotalCost((TriApplication)getApplication()));

        mRemainTimesTextView = (TextView) this.findViewById(R.id.trip_remain_times);
        if (DateHelper.getCalendarDay(0).getTime().compareTo(mTrip.getDateFrom()) >= 0 && DateHelper.getCalendarDay(0).getTime().compareTo(mTrip.getDateTo()) <= 0 ) {
            mRemainTimesTextView.setText("remaining " +
                    DateHelper.getDateString(mTrip.getDateFrom()) + "~" + DateHelper.getDateString(mTrip.getDateTo()) + ": Remaining days: " +
                    Long.toString( (DateHelper.getCalendarDay(0).getTimeInMillis() - mTrip.getDateTo().getTime())/(24 * 60 * 60 * 1000) ));
        }
        else {
            mRemainTimesTextView.setText(DateHelper.getDateString(mTrip.getDateFrom()) + "~" + DateHelper.getDateString(mTrip.getDateTo()));
        }

        mMembersListView = (ListView) this.findViewById(R.id.trip_members);
        ArrayList<TriFriend> members = mTrip.getMembers((TriApplication)getApplication());
        mMembersAdapter = new MembersAdapter( this, R.layout.activity_members_list_item, members, mTrip);
        mMembersListView.setAdapter(mMembersAdapter);
        TextView textview = new TextView(this);
        textview.setText("Members : Total " + mTrip.getMembers( (TriApplication)getApplication()).size() );
        textview.setTextColor(0xffa4a6a8);
        textview.setPadding(0, 0, 0, 14);
        textview.setGravity(Gravity.LEFT);
        mMembersListView.addHeaderView(textview, null, false);
        mMembersAdapter.notifyDataSetChanged();
        mMembersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriFriend friend = (TriFriend) parent.getAdapter().getItem(position);
                Intent i = new Intent(getBaseContext(), FriendActivity.class);
                i.putExtra("friend", friend);
                startActivity(i);
            }
        });

        mRecordsListView = (ListView) this.findViewById(R.id.trip_records);
        DBManager db = new DBManager(this);
        try {
            db.open();
            ((TriApplication)getApplication()).getgItems().clear();
            ((TriApplication)getApplication()).getgItems().addAll(db.getItems());
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<TriItem> items = mTrip.getRecords((TriApplication)getApplication());
        mRecordsAdapter = new RecordsAdapter( this, R.layout.activity_friends_list_item, items );
        mRecordsListView.setAdapter(mRecordsAdapter);
        TextView textview2 = new TextView(this);
        textview2.setText("Trip items list view");
        textview2.setTextColor(0xffa4a6a8);
        textview2.setPadding(0, 0, 0, 14);
        textview2.setGravity(Gravity.LEFT);
        mRecordsListView.addHeaderView(textview2, null, false);
        mRecordsAdapter.notifyDataSetChanged();;

        mRecordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriItem item = (TriItem) parent.getAdapter().getItem(position);
                Intent i = new Intent( getBaseContext(), ItemActivity.class );
                i.putExtra("item", item);
                startActivity(i);
            }
        });

        mRecordsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TripActivity.this);
                builder.setTitle("Select options");
                CharSequence options[] = new CharSequence[] {"View", "Edit", "delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TriItem item;
                        Intent i;
                        switch (which) {
                            case 0: // view
                                item = (TriItem) parent.getAdapter().getItem(position);
                                i = new Intent( getBaseContext(), ItemActivity.class );
                                i.putExtra("item", item);
                                startActivity(i);
                                break;
                            case 1: // edit
                                item = (TriItem) parent.getAdapter().getItem(position);
                                i = new Intent( getBaseContext(), EditItemActivity.class );
                                i.putExtra("item", item);
                                startActivity(i);
                                break;
                            case 2: // delete
                                item = (TriItem) parent.getAdapter().getItem(position);
                                ((TriApplication)getApplication()).getgItems().remove(item); // delete
                                mRecordsAdapter.remove(item);
                                mRecordsAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });

        mCurrBalanceTextView = (TextView) this.findViewById(R.id.trip_curr_balance);
        mCurrBalanceTextView.setText("curr balance: $" + mTrip.getCurrBalance((TriApplication)getApplication()) );

        mCheckImageButton = (ImageButton) this.findViewById(R.id.trip_check);
        mCheckImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }
}