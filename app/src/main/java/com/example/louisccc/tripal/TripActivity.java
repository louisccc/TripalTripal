package com.example.louisccc.tripal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

/**
 * Created by louisccc on 2/12/16.
 */
public class TripActivity extends Activity {

    TriTrip mTrip;
    TextView mInitBalanceTextView;
    TextView mRemainTimesTextView;

    ListView mMemebersListView;
    ListView mRecordsListView;

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
        mMemebersListView = (ListView) this.findViewById(R.id.trip_members);
        mRecordsListView = (ListView) this.findViewById(R.id.trip_records);

        mCurrBalanceTextView = (TextView) this.findViewById(R.id.trip_curr_balance);
        mCurrBalanceTextView.setText("curr balance: " + mTrip.getCurrBalance() );

        mCheckImageButton = (ImageButton) this.findViewById(R.id.trip_check);
        mCheckImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }
}