package com.example.louisccc.tripal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louisccc.tripal.model.TriFriend;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */

public class FriendsAdapter extends ArrayAdapter<TriFriend> {
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
        TextView name = (TextView) convertView.findViewById(R.id.ItemName);
        TextView contact = (TextView) convertView.findViewById(R.id.ItemContact);
//        ImageView thumb = (ImageView) convertView.findViewById(R.id.ItemThumbnail);
        name.setText(mFriends.get(position).getName());
        contact.setText(mFriends.get(position).getEmail());
//        thumb.setImageDrawable(getContext().getResources);
        return convertView;
    }
}

