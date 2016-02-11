package com.example.louisccc.tripal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.model.DBManager;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by louisccc on 1/3/16.
 */
public class DashBoardFragment extends Fragment {
    DBManager mDB;
    ListView mListview;
    TripsAdapter mListViewAdapter;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dashboard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDB = new DBManager(getActivity());

        mListview = (ListView)getActivity().findViewById(R.id.trips);
        mListViewAdapter = new TripsAdapter(getActivity(),
                                            R.layout.activity_dashboard_trips_list_item,
                                            TriApplication.getInstance().getgTrips());
        mListview.setAdapter(mListViewAdapter);
        TextView textview = new TextView(getActivity());
        textview.setText("Headers zone");
        textview.setTextColor(0xffa4a6a8);
        textview.setPadding(0, 0, 0, 14);
        textview.setGravity(Gravity.CENTER);
        mListview.addHeaderView(textview, null, false);

        mListViewAdapter.notifyDataSetChanged();

//        setupFooterButtons();
    }

    private class TripsAdapter extends ArrayAdapter<TriTrip> {
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
            TextView amount = (TextView) convertView.findViewById(R.id.ItemAmount);
            text.setText(mTrips.get(position).getName());
            date_from.setText(DateHelper.getDateString(mTrips.get(position).getDateFrom()));
            date_to.setText(DateHelper.getDateString(mTrips.get(position).getDateTo()));
            amount.setText(Double.toString(mTrips.get(position).getBudget()));
            return convertView;
        }
    }

}
