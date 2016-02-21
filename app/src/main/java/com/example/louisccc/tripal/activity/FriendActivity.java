package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
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
    private static int mFriendActivityResSrcId = R.layout.activity_friend;
    private TriFriend mFriend;
    private ImageView mThumbnail; // TODO

    private TextView mNameTextView;
    private TextView mEmailTextView;
    private TextView mPhoneTextView;

//    private ListView mDeptRecordsListView;
//    private RecordsAdapter mDeptRecordsAdapter;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mFriendActivityResSrcId);

        mFriend = getIntent().getParcelableExtra("friend");
        mNameTextView = (TextView) this.findViewById(R.id.friend_name);
        mEmailTextView = (TextView) this.findViewById(R.id.friend_email);
        mPhoneTextView = (TextView) this.findViewById(R.id.friend_phone);

        mNameTextView.setText(mFriend.getName());
        mEmailTextView.setText(mFriend.getEmail());
        mPhoneTextView.setText(mFriend.getPhone());

        this.getActionBar().setDisplayHomeAsUpEnabled(true);
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