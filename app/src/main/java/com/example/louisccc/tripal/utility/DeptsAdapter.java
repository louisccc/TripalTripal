package com.example.louisccc.tripal.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriTrip;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */

public class DeptsAdapter extends ArrayAdapter<TriFriend> {
    private ArrayList<TriFriend> mFriends;
    private int mResrc_id;
    private LayoutInflater mVi;
    private TriTrip mTrip;

    public DeptsAdapter(Context ctx, int resrc_id, ArrayList<TriFriend> friends, TriTrip trip) {
        super( ctx, resrc_id, friends );
        this.mFriends = friends;
        this.mResrc_id = resrc_id;
        this.mVi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTrip = trip;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mVi.inflate(mResrc_id, null);
        TextView name = (TextView) convertView.findViewById(R.id.ItemName);
        TextView curr_balance = (TextView) convertView.findViewById(R.id.ItemAmount);
        TextView proportion = (TextView) convertView.findViewById(R.id.ItemProportion);
//        ImageView thumb = (ImageView) convertView.findViewById(R.id.ItemThumbnail);
        name.setText(mFriends.get(position).getName());

        double value_curr_balance = mFriends.get(position).getCurrBalance(getContext(), mTrip);
        curr_balance.setText( "have spend:" + Double.toString(value_curr_balance) );

        if ( mTrip.getTotalCost( (TriApplication)getContext().getApplicationContext() ) <= 0) {
            proportion.setText( "-"  + "%") ;
        }
        else {
            double value_proportion = 100 * value_curr_balance / mTrip.getTotalCost((TriApplication) getContext().getApplicationContext());
            proportion.setText(Double.toString((double) (Math.round(value_proportion * 100) / 100.0)) + "%");
        }
//        thumb.setImageDrawable(getContext().getResources);
        return convertView;
    }
}

