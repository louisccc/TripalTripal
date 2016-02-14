package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.utility.RecordsAdapter;
import com.example.louisccc.tripal.model.TriFriend;

/**
 * Created by louisccc on 2/12/16.
 */
public class FriendActivity extends Activity {

    TriFriend mFriend;
    ImageView mThumbnail; // TODO

    TextView mNameTextView;
    TextView mEmailTextView;
    TextView mPhoneTextView;

    ListView mDeptRecordsListView;
    RecordsAdapter mDeptRecordsAdapter;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        mFriend = getIntent().getParcelableExtra("friend");
        mNameTextView = (TextView) this.findViewById(R.id.friend_name);
        mNameTextView.setText(mFriend.getName());

        mEmailTextView = (TextView) this.findViewById(R.id.friend_email);
        mEmailTextView.setText(mFriend.getEmail());

        mPhoneTextView = (TextView) this.findViewById(R.id.friend_phone);
        mPhoneTextView.setText(mFriend.getPhone());
    }
}