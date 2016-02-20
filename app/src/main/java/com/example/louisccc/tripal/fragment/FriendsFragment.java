package com.example.louisccc.tripal.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.activity.FriendActivity;
import com.example.louisccc.tripal.utility.DateHelper;
import com.example.louisccc.tripal.utility.FriendsAdapter;
import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriFriend;

/**
 * Created by louisccc on 1/3/16.
 */
public class FriendsFragment extends Fragment {

    private static int mFriendsFragmentViewResSrcId = R.layout.activity_friends;
    private static int mFriendsListViewResSrcId = R.layout.activity_friends_list_item;
    private ListView mFriendsListview = null;
    private FriendsAdapter mFriendsListViewAdapter = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(mFriendsFragmentViewResSrcId, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFriendsListview = (ListView) getActivity().findViewById(R.id.friends);
        mFriendsListViewAdapter = new FriendsAdapter(getActivity(), mFriendsListViewResSrcId, ((TriApplication) getActivity().getApplication()).getgFriends());
        mFriendsListview.setAdapter(mFriendsListViewAdapter);
        String textShown = getActivity().getPackageName();
        mFriendsListview.addHeaderView(DateHelper.getTextViewWithText(getActivity(), textShown), null, false);
        mFriendsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriFriend f = (TriFriend) parent.getAdapter().getItem(position);
                Intent i = new Intent(getActivity(), FriendActivity.class);
                i.putExtra("friend", f);
                startActivity(i);
            }
        });
        mFriendsListViewAdapter.notifyDataSetChanged();
    }


}
