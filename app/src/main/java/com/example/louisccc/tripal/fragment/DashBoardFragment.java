package com.example.louisccc.tripal.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.activity.TripActivity;
import com.example.louisccc.tripal.utility.TripsAdapter;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by louisccc on 1/3/16.
 */
public class DashBoardFragment extends Fragment {

    private static int mDashBoardFragmentViewResSrcId = R.layout.activity_dashboard;
    private static int mOngoingTripsListViewItemResSrcId = R.layout.activity_dashboard_trips_list_item;
    private static int mHistoryTripsListViewItemResSrcId = R.layout.activity_dashboard_trips_list_item;

    private TextView mRangeTextView;
    private TextView mYearSpentTextView;
    private TextView mBudgetTextView;

    private ListView mOngoingTripsListView;
    private TripsAdapter mOngoingTripsListViewAdapter;

    private ListView mHistoryTripsListView;
    private TripsAdapter mHistoryTripsListViewAdapter;

    private TriApplication mApp;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(mDashBoardFragmentViewResSrcId, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mApp = (TriApplication) getActivity().getApplication();
        mApp.refreshGlobals();

        mRangeTextView = (TextView) getActivity().findViewById(R.id.trips_range);
        mYearSpentTextView = (TextView) getActivity().findViewById(R.id.trips_total_year_spend);
        mBudgetTextView = (TextView) getActivity().findViewById(R.id.trips_remaining_year_budget);

        ArrayList<TriTrip> trips = mApp.getgTrips();
        double totalYearSpent = 0;
        Date from = null;
        Date to = null;
        for (TriTrip trip : trips) {
            totalYearSpent += trip.getTotalCost((TriApplication) getActivity().getApplication());
            if (from != null && to != null) {
                if(trip.getDateFrom().compareTo(from) <= 0) {
                    from = trip.getDateFrom();
                }
                if(trip.getDateTo().compareTo(to) >= 0) {
                    to = trip.getDateTo();
                }
            }
            else { from = trip.getDateFrom(); to = trip.getDateTo(); }
        }

        mRangeTextView.setText(DateHelper.getDateString(from) + "~" + DateHelper.getDateString(to));
        mYearSpentTextView.setText("NT$" + totalYearSpent);
        mBudgetTextView.setText("NT$0");


        /* ongoing trips */
        mOngoingTripsListView = (ListView) getActivity().findViewById(R.id.trips_ongoing);
        mOngoingTripsListViewAdapter = new TripsAdapter(getActivity(), mOngoingTripsListViewItemResSrcId, ((TriApplication) getActivity().getApplication()).getgOngoingTrips());
        mOngoingTripsListView.setAdapter(mOngoingTripsListViewAdapter);
        mOngoingTripsListView.setEmptyView(getActivity().findViewById(R.id.trips_ongoing_empty));
        mOngoingTripsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriTrip trip = (TriTrip) parent.getAdapter().getItem(position);
                Intent i = new Intent(parent.getContext(), TripActivity.class);
                i.putExtra("trip", trip);
                startActivity(i);
            }
        });
        mOngoingTripsListViewAdapter.notifyDataSetChanged();

        /* history trips */
        mHistoryTripsListView = (ListView) getActivity().findViewById(R.id.trips_history);
        mHistoryTripsListViewAdapter = new TripsAdapter(getActivity(), mHistoryTripsListViewItemResSrcId, ((TriApplication) getActivity().getApplication()).getgHistoryTrips());
        mHistoryTripsListView.setAdapter(mHistoryTripsListViewAdapter);
        mHistoryTripsListView.setEmptyView(getActivity().findViewById(R.id.trips_history_empty));
        mHistoryTripsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriTrip trip = (TriTrip) parent.getItemAtPosition(position);
                Intent i = new Intent(parent.getContext(), TripActivity.class);
                i.putExtra("trip", trip);
                startActivity(i);
            }
        });
        mHistoryTripsListViewAdapter.notifyDataSetChanged();
    }

}
