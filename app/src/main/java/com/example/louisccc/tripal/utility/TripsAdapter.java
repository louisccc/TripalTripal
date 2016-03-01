package com.example.louisccc.tripal.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    private static class ViewHolder {
        ImageView trip_thumb;
        TextView desc;
        TextView date;
        TextView cost;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mVi.inflate(mResrc_id, null);
            viewHolder.trip_thumb = (ImageView) convertView.findViewById(R.id.trip_thumb);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.ItemDesc);
            viewHolder.date = (TextView) convertView.findViewById(R.id.ItemDate);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.ItemCurrBalance);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TriTrip trip = mTrips.get(position);
        TriApplication app = (TriApplication) getContext().getApplicationContext();
        viewHolder.desc.setText(trip.getName().trim());
        viewHolder.date.setText(DateHelper.getDateString(trip.getDateFrom())+"~"+DateHelper.getDateString(trip.getDateTo()));
        viewHolder.cost.setText("NT$" + trip.getTotalCost(app));
        return convertView;
    }
}
