package com.example.louisccc.tripal.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriItem;
import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class RecordsAdapter extends ArrayAdapter<TriItem> {
    private ArrayList<TriItem> mItems;
    private int mResrc_id;
    private LayoutInflater mVi;

    public RecordsAdapter(Context ctx, int resrc_id, ArrayList<TriItem> items) {
        super(ctx, resrc_id, items);
        this.mItems = items;
        this.mResrc_id = resrc_id;
        this.mVi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mVi.inflate(mResrc_id, null);
        TextView text = (TextView) convertView.findViewById(R.id.ItemName);
        TextView name = (TextView) convertView.findViewById(R.id.ItemContact);

        text.setText(mItems.get(position).getName() + ", " + mItems.get(position).getAmount() );
        name.setText(mItems.get(position).getNote());
        return convertView;
    }
}