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

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.TriApplication;
import com.example.louisccc.tripal.TripActivity;
import com.example.louisccc.tripal.TripsAdapter;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

/**
 * Created by louisccc on 1/3/16.
 */
public class DashBoardFragment extends Fragment {
    ListView mListview;
    TripsAdapter mListViewAdapter;

    ListView mListview2;
    TripsAdapter mListViewAdapter2;

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

        /* ongoing trips */
        mListview = (ListView)getActivity().findViewById(R.id.trips_ongoing);
        mListViewAdapter = new TripsAdapter(getActivity(),
                R.layout.activity_dashboard_trips_list_item,
                TriApplication.getInstance().getgOngoingTrips() );
        mListview.setAdapter(mListViewAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriTrip trip = (TriTrip) parent.getAdapter().getItem(position);
                Intent i = new Intent(getActivity(), TripActivity.class);
                i.putExtra("trip", trip);
                startActivity(i);
            }
        });
        mListview.addHeaderView( DateHelper.getTextViewWithText( getActivity(), "Your ongoing trips" ) );
        mListview.setEmptyView( getActivity().findViewById(R.id.trips_ongoing_empty) );

        mListViewAdapter.notifyDataSetChanged();

        /* history trips */

        mListview2 = (ListView)getActivity().findViewById(R.id.trips_history);
        mListViewAdapter2 = new TripsAdapter(getActivity(),
                                            R.layout.activity_dashboard_trips_list_item,
                                            TriApplication.getInstance().getgHistoryTrips() );
        mListview2.setAdapter(mListViewAdapter2);
        mListview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TriTrip trip = (TriTrip) parent.getAdapter().getItem(position);
                Intent i = new Intent(getActivity(), TripActivity.class);
                i.putExtra("trip", trip);
                startActivity(i);
            }
        });
        mListview2.addHeaderView( DateHelper.getTextViewWithText( getActivity(), "History trips" ) );
        mListview2.setEmptyView( getActivity().findViewById(R.id.trips_history_empty) );
        mListViewAdapter2.notifyDataSetChanged();
    }


}
