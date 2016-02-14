package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;
import com.example.louisccc.tripal.utility.FriendsAdapter;
import com.example.louisccc.tripal.utility.RecordsAdapter;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class ItemActivity extends Activity {

    TriItem mItem;
    ImageView mItemThumbnailImageView;
    TextView mItemTimeStampTextView;
    TextView mItemLocationTextView;
    TextView mItemNoteTextView;

    TextView mItemAmountTextView;
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
        setContentView(R.layout.activity_item);

        mItem = getIntent().getParcelableExtra("item");

        // TODO thumbnail
        mItemTimeStampTextView = (TextView) this.findViewById(R.id.item_timestamp);
        mItemTimeStampTextView.setText( mItem.getTimeStamp() );

        // TODO location
        mItemNoteTextView = (TextView) this.findViewById(R.id.item_note);
        mItemNoteTextView.setText(mItem.getNote());

        mItemAmountTextView = (TextView) this.findViewById(R.id.item_amount);
        mItemAmountTextView.setText( Double.toString( mItem.getAmount() ) );

        mItemOwnerTextView = (TextView) this.findViewById(R.id.item_owner);
        mItemOwnerTextView.setText( "Owner_id: " + mItem.getOwner() );
    }
}