package com.example.louisccc.tripal.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class TripsAdapter extends ArrayAdapter<TriTrip> {
    private ArrayList<TriTrip> mTrips;
    private int mResrc_id;
    private LayoutInflater mVi;

    public TripsAdapter(Context ctx, int resrc_id, ArrayList<TriTrip> trips) {
        super(ctx, resrc_id, trips);
        this.mTrips = trips;
        this.mResrc_id = resrc_id;
        this.mVi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mVi.inflate(mResrc_id, null);
        TextView text = (TextView) convertView.findViewById(R.id.ItemDesc);
        TextView date_from = (TextView) convertView.findViewById(R.id.ItemDateFrom);
        TextView date_to = (TextView) convertView.findViewById(R.id.ItemDateTo);
        TextView budget = (TextView) convertView.findViewById(R.id.ItemBudget);
        TextView curr_balance = (TextView) convertView.findViewById(R.id.ItemCurrBalance);
        text.setText(mTrips.get(position).getName());
        date_from.setText("From: " + DateHelper.getDateString(mTrips.get(position).getDateFrom()));
        date_to.setText("To " + DateHelper.getDateString(mTrips.get(position).getDateTo()));
        budget.setText("Budget: " + Double.toString(mTrips.get(position).getBudget()));
        curr_balance.setText("Balance: " + Double.toString(mTrips.get(position).getCurrBalance()));
        return convertView;
    }
}
