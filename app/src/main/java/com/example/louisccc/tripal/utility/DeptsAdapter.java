package com.example.louisccc.tripal.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.activity.EditItemActivity;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriDept;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.model.TriTrip;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */

public class DeptsAdapter extends ArrayAdapter<TriFriend> {
    private ArrayList<TriFriend> mFriends;
    private int mResrc_id;
    private LayoutInflater mVi;
    private TriItem mItem;

    public DeptsAdapter(Context ctx, int resrc_id, ArrayList<TriFriend> friends, TriItem item) {
        super( ctx, resrc_id, friends );
        this.mFriends = friends;
        this.mResrc_id = resrc_id;
        this.mVi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItem = item;
    }


    static class ViewHolder {
        TextView name;
        EditText paid;
        EditText proportion;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        convertView = mVi.inflate(mResrc_id, null);
        TextView name = (TextView) convertView.findViewById(R.id.ItemName);
        EditText curr_balance = (EditText) convertView.findViewById(R.id.ItemAmount);
        EditText proportion = (EditText) convertView.findViewById(R.id.ItemProportion);
        ImageView thumb = (ImageView) convertView.findViewById(R.id.ItemThumbnail);
        name.setText(mFriends.get(position).getName());
        if ( mItem != null ) {

            for (TriDept dept : ((TriApplication) getContext().getApplicationContext()).getgDepts()) {
                if (dept.getUserId() == mFriends.get(position).getLocalId() && mItem.getLocalId() == dept.getItemId() ){
                    curr_balance.setText("" + dept.getPaid() );
                    proportion.setText("" + dept.getProportion() );
                    break;
                }
            }
        }
        return convertView;
    }
}

