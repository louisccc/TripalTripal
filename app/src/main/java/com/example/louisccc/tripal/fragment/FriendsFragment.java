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
import com.example.louisccc.tripal.utility.FriendsAdapter;
import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriFriend;

/**
 * Created by louisccc on 1/3/16.
 */
public class FriendsFragment extends Fragment {

    ListView mListview;
    FriendsAdapter mListViewAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_friends, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListview = (ListView)getActivity().findViewById(R.id.friends);
        mListViewAdapter = new FriendsAdapter(getActivity(),
                R.layout.activity_friends_list_item,
                TriApplication.getInstance().getgFriends());
        mListview.setAdapter(mListViewAdapter);
        TextView textview = new TextView(getActivity());
        textview.setText(getActivity().getPackageName());
        textview.setTextColor(0xffa4a6a8);
        textview.setPadding(0, 0, 0, 14);
        textview.setGravity(Gravity.CENTER);
        mListview.addHeaderView(textview, null, false);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriFriend f = (TriFriend) parent.getAdapter().getItem(position);
                Intent i = new Intent(getActivity(), FriendActivity.class);
                i.putExtra("friend", f);
                startActivity(i);
            }
        });
        mListViewAdapter.notifyDataSetChanged();
    }


}
