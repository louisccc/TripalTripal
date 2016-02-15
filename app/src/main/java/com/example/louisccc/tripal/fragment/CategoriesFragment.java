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

import java.util.ArrayList;

/**
 * Created by louisccc on 1/3/16.
 */
public class CategoriesFragment extends Fragment {

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
                ((TriApplication)getActivity().getApplication()).getgFriends());
        mListview.setAdapter(mListViewAdapter);
        TextView textview = new TextView(getActivity());
        textview.setText(getActivity().getPackageName());
        textview.setTextColor(0xffa4a6a8);
        textview.setPadding(0, 0, 0, 14);
        textview.setGravity(Gravity.CENTER);
        mListview.addHeaderView(textview, null, false);

        mListViewAdapter.notifyDataSetChanged();
    }

    private class FriendsAdapter extends ArrayAdapter<TriFriend> {
        private ArrayList<TriFriend> mFriends;
        private int mResrc_id;
        private LayoutInflater mVi;

        public FriendsAdapter(Context ctx, int resrc_id, ArrayList<TriFriend> friends) {
            super( ctx, resrc_id, friends );
            this.mFriends = friends;
            this.mResrc_id = resrc_id;
            this.mVi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mVi.inflate(mResrc_id, null);
            TextView text = (TextView) convertView.findViewById(R.id.ItemDesc);
            text.setText(mFriends.get(position).getName());
            return convertView;
        }
    }
}
