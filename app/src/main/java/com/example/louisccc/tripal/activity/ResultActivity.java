package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriDept;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;
import com.example.louisccc.tripal.utility.DeptRecordsAdapter;

import junit.framework.Assert;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class ResultActivity extends Activity {
    private static int mFriendActivityResSrcId = R.layout.activity_result;
    private static int mDeptRecordsItemResSrcId = R.layout.activity_items_list_item;
    private TriTrip mTrip;

    private ImageView mThumbnail; // TODO

    private TextView mTripNameTextView;
    private TextView mTotalSpentTextView;
    private TextView mRemainTimesTextView;
    private TextView mCurrBalanceTextView;
    private TextView mSummaryTextView;

    private ListView mDeptRecordsListView;
    private DeptRecordsAdapter mDeptRecordsAdapter;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mFriendActivityResSrcId);

        mTrip = getIntent().getParcelableExtra("trip");
        Assert.assertTrue( mTrip != null );
        ((TriApplication)getApplication()).refreshGlobals();

        mTripNameTextView = (TextView) this.findViewById(R.id.result_trip_name);
        mTripNameTextView.setText(mTrip.getName());

        mTotalSpentTextView = (TextView) this.findViewById(R.id.result_trip_total_spent);
        mTotalSpentTextView.setText("Total Spent: $" + mTrip.getTotalCost((TriApplication)getApplication()));

        mRemainTimesTextView = (TextView) this.findViewById(R.id.result_trip_remain_times);
        if (DateHelper.getCalendarDay(0).getTime().compareTo(mTrip.getDateFrom()) >= 0 && DateHelper.getCalendarDay(0).getTime().compareTo(mTrip.getDateTo()) <= 0 ) {
            mRemainTimesTextView.setText("remaining " +
                    DateHelper.getDateString(mTrip.getDateFrom()) + "~" + DateHelper.getDateString(mTrip.getDateTo()) + ": Remaining days: " +
                    Long.toString( ( mTrip.getDateTo().getTime() - DateHelper.getCalendarDay(0).getTimeInMillis() )/(24 * 60 * 60 * 1000) ));
        }
        else {
            mRemainTimesTextView.setText(DateHelper.getDateString(mTrip.getDateFrom()) + "~" + DateHelper.getDateString(mTrip.getDateTo()));
        }
        mCurrBalanceTextView = (TextView) this.findViewById(R.id.result_trip_curr_balance);
        mCurrBalanceTextView.setText("curr balance: $" + mTrip.getCurrBalance((TriApplication)getApplication()) );

        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        mDeptRecordsListView = (ListView) this.findViewById(R.id.result_balance_sheet);
        mDeptRecordsAdapter = new DeptRecordsAdapter( this, mDeptRecordsItemResSrcId, ((TriApplication) getApplication()).getgItems());
        mDeptRecordsListView.setAdapter(mDeptRecordsAdapter);
        mDeptRecordsAdapter.notifyDataSetChanged();
        mDeptRecordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriItem item = (TriItem) parent.getAdapter().getItem(position);
                Intent i = new Intent( getBaseContext(), ItemActivity.class );
                i.putExtra("item", item);
                startActivity(i);
            }
        });

        ArrayList<TriItem> items = ((TriApplication)getApplication()).getgItems();
        ArrayList<TriFriend> members = mTrip.getMembers( (TriApplication) getApplication() );
        ArrayList<TriDept> depts = ((TriApplication)getApplication()).getgDepts();

        String showingText = "In trip " + mTrip.getName() + " :";


        for ( TriFriend friend : members ) {

            double balance = 0;
            for ( TriDept dept : depts ) {
                if ( friend.getLocalId() == dept.getUserId() ) {
                    TriItem item_related_to_dept = null;
                    for (TriItem item : items ) {
                        if (item.getLocalId() == dept.getItemId() ) {
                            item_related_to_dept = item;
                            break;
                        }
                    }
                    if(item_related_to_dept != null) {
                        balance += dept.getPaid() - item_related_to_dept.getAmount() * (dept.getProportion() / 100);
                    }
                }
            }
            showingText += "" + friend.getName() + " should get " + balance  + " back.\n";
        }
        mSummaryTextView = (TextView) this.findViewById( R.id.result_trip_summary);
        mSummaryTextView.setText(showingText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
            
}