package com.example.louisccc.tripal.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.utility.DateHelper;
import com.example.louisccc.tripal.utility.FriendsAdapter;

import java.util.ArrayList;

/**
 * Created by louisccc on 1/3/16.
 */
public class CategoriesFragment extends Fragment {

    private static int mListViewResSrcId = R.layout.activity_friends_list_item;
    private ListView mListview;
    private FriendsAdapter mListViewAdapter;

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
        mListview = (ListView) getActivity().findViewById(R.id.friends);
        mListViewAdapter = new FriendsAdapter(getActivity(), mListViewResSrcId, ((TriApplication) getActivity().getApplication()).getgFriends());
        mListview.setAdapter(mListViewAdapter);
        mListview.addFooterView(DateHelper.getTextViewWithText(getActivity(), getActivity().getPackageName()), null, false);
        mListViewAdapter.notifyDataSetChanged();
    }
}
