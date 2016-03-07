package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    private static int mTripActivityViewResSrcId = R.layout.activity_trip;
    private static int mMembersListViewItemResSrcId = R.layout.activity_members_list_item;
    private static int mItemsListViewItemResSrcId = R.layout.activity_items_list_item;

    private TriTrip mTrip;
    private TextView mTripNameTextView;
    private TextView mRemainTimesTextView;
    private TextView mTotalSpentTextView;
    private TextView mCurrBalanceTextView;


    private ListView mMembersListView;
    private MembersAdapter mMembersAdapter;
    private ListView mRecordsListView;
    private RecordsAdapter mRecordsAdapter;


    private Button mCheckImageButton;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mTripActivityViewResSrcId);

        mTrip = getIntent().getParcelableExtra("trip");
        Assert.assertTrue(mTrip != null);
        ((TriApplication)getApplication()).refreshGlobals();

        mTripNameTextView = (TextView) this.findViewById(R.id.trip_name);
        mTripNameTextView.setText(mTrip.getName());

        mTotalSpentTextView = (TextView) this.findViewById(R.id.trip_total_spent);
        mTotalSpentTextView.setText("NT$" + mTrip.getTotalCost((TriApplication)getApplication()));

        mRemainTimesTextView = (TextView) this.findViewById(R.id.trip_remain_times);
        if (DateHelper.getCalendarDay(0).getTime().compareTo(mTrip.getDateFrom()) >= 0 && DateHelper.getCalendarDay(0).getTime().compareTo(mTrip.getDateTo()) <= 0 ) {
            mRemainTimesTextView.setText("remaining " +
                    DateHelper.getDateString(mTrip.getDateFrom()) + "~" + DateHelper.getDateString(mTrip.getDateTo()) + ": Remaining days: " +
                    Long.toString( ( mTrip.getDateTo().getTime() - DateHelper.getCalendarDay(0).getTimeInMillis() )/(24 * 60 * 60 * 1000) ));
        }
        else {
            mRemainTimesTextView.setText(DateHelper.getDateString(mTrip.getDateFrom()) + "~" + DateHelper.getDateString(mTrip.getDateTo()));
        }
        mCurrBalanceTextView = (TextView) this.findViewById(R.id.trip_curr_balance);
        mCurrBalanceTextView.setText("NT$" + mTrip.getCurrBalance((TriApplication)getApplication()) );

        setupMembersListView();
        setupRecordsListView();
        setupCheckButton();

        this.getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupMembersListView() {
        mMembersListView = (ListView) this.findViewById(R.id.trip_members);
        mMembersAdapter = new MembersAdapter( this, mMembersListViewItemResSrcId, mTrip.getMembers((TriApplication)getApplication()), mTrip);
        mMembersListView.setAdapter(mMembersAdapter);
//        mMembersListView.addHeaderView(DateHelper.getTextViewWithText(this, "Members : Total " + mTrip.getMembers( (TriApplication)getApplication()).size()), null, false);
        mMembersAdapter.notifyDataSetChanged();
        mMembersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriFriend friend = (TriFriend) parent.getAdapter().getItem(position);
                Intent i = new Intent(parent.getContext(), FriendActivity.class);
                i.putExtra("friend", friend);
                startActivity(i);
            }
        });
    }

    public void setupRecordsListView() {
        mRecordsListView = (ListView) this.findViewById(R.id.trip_records);
        ArrayList<TriItem> items = mTrip.getRecords((TriApplication)getApplication());
        mRecordsAdapter = new RecordsAdapter( this, mItemsListViewItemResSrcId, items );
        mRecordsListView.setAdapter(mRecordsAdapter);
//        mRecordsListView.addHeaderView(DateHelper.getTextViewWithText(this, "Items : Total " + mTrip.getRecords( (TriApplication)getApplication() ).size()), null, false);
        mRecordsListView.setEmptyView( this.findViewById(R.id.trip_records_empty) );
        mRecordsAdapter.notifyDataSetChanged();
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
                                i.putExtra("trip", mTrip);
                                startActivity(i);
                                break;
                            case 2: // delete
                                item = (TriItem) parent.getAdapter().getItem(position);
// TODO                                ((TriApplication)getApplication()).getgItems().remove(item); // delete
                                ((TriApplication)getApplication()).refreshGlobals();
                                mRecordsAdapter.clear();
                                mRecordsAdapter.addAll(((TriApplication)getApplication()).getgItems());
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
    }

    public void setupCheckButton(){
        mCheckImageButton = (Button) this.findViewById(R.id.trip_check);
        mCheckImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ResultActivity.class);
                i.putExtra("trip", mTrip);
                startActivity(i);
                return;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_trip_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.action_add_item:
                Intent i = new Intent(this, EditItemActivity.class);
                i.putExtra("trip", mTrip);
                startActivity(i);
                break;
            case R.id.action_add_friend:
                getTempAlert( TripActivity.this ).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private AlertDialog.Builder getTempAlert(Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
                .setMessage("小元 alert").setMessage("回來再做給你用")
                .setPositiveButton("爽", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setNegativeButton("不爽", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        return builder;
    }


}