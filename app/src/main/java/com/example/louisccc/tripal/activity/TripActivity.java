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
import com.example.louisccc.tripal.utility.RecordsAdapter;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class TripActivity extends Activity {

    TriTrip mTrip;
    TextView mInitBalanceTextView;
    TextView mRemainTimesTextView;

    ListView mMembersListView;
    FriendsAdapter mMembersAdapter;
    ListView mRecordsListView;
    RecordsAdapter mRecordsAdapter;

    TextView mCurrBalanceTextView;
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
        mInitBalanceTextView = (TextView) this.findViewById(R.id.trip_init_balance);
        mInitBalanceTextView.setText("Init Balance: " + mTrip.getBudget() );

        mRemainTimesTextView = (TextView) this.findViewById(R.id.trip_remain_times);
        if (DateHelper.getCalendarDay(0).getTime().after(mTrip.getDateFrom()) && DateHelper.getCalendarDay(0).getTime().before(mTrip.getDateTo()) ) {
            mRemainTimesTextView.setText("remaining " +
                    mTrip.getDateFrom().toString() + " " + mTrip.getDateTo().toString() + "\n" +
                    DateHelper.getCalendarDay(0).getTime().toString());
        }
        else {
            mRemainTimesTextView.setText("This trip is not ongoing! " +
                    mTrip.getDateFrom().toString() + " "  + mTrip.getDateTo().toString());
        }

        mMembersListView = (ListView) this.findViewById(R.id.trip_members);
        ArrayList<TriFriend> members = mTrip.getMembers((TriApplication)getApplication());
        mMembersAdapter = new FriendsAdapter( this, R.layout.activity_friends_list_item, members );
        mMembersListView.setAdapter(mMembersAdapter);
        TextView textview = new TextView(this);
        textview.setText("Members list view");
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
            ((TriApplication)getApplication()).setgItems(db.getItems());
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
        mCurrBalanceTextView.setText("curr balance: " + mTrip.getCurrBalance((TriApplication)getApplication()) );

        mCheckImageButton = (ImageButton) this.findViewById(R.id.trip_check);
        mCheckImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }
}