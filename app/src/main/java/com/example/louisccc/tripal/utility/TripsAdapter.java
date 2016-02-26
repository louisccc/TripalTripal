package com.example.louisccc.tripal.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

import org.w3c.dom.Text;

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
        TriTrip trip = mTrips.get(position);
        TriApplication app = (TriApplication) getContext().getApplicationContext();
        TextView text = (TextView) convertView.findViewById(R.id.ItemDesc);
        TextView date = (TextView) convertView.findViewById(R.id.ItemDate);
        TextView curr_balance = (TextView) convertView.findViewById(R.id.ItemCurrBalance);
        text.setText(trip.getName());
        date.setText(DateHelper.getDateString(trip.getDateFrom())+"~"+DateHelper.getDateString(trip.getDateTo()));
        curr_balance.setText("NT$" + Double.toString(trip.getCurrBalance(app)));
        return convertView;
    }
}
