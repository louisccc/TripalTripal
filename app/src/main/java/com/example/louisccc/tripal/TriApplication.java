package com.example.louisccc.tripal;

import android.app.Application;

import com.example.louisccc.tripal.model.*;
import com.example.louisccc.tripal.utility.DateHelper;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/11/16.
 */
public class TriApplication extends Application {
    private ArrayList<TriTrip> gTrips;
    private ArrayList<TriFriend> gFriends;
    private ArrayList<TriParticipation> gParticipations;
    private ArrayList<TriItem> gItems;
    private ArrayList<TriDept> gDepts;

    private static TriApplication mInstance = null;
    public static synchronized TriApplication getInstance(){
        if (mInstance == null) {
            mInstance = new TriApplication();
        }
        return mInstance;
    }

    public TriApplication(){
        gTrips = new ArrayList<TriTrip>();
        gTrips.add( new TriTrip("Mountain Ali", 15000, 1, DateHelper.getDate(2015, 12, 31), DateHelper.getDate(2016, 1, 2) ) );
        gTrips.get(0).setLocalId(0);
        gTrips.add( new TriTrip("Hong Kong trip", 20000, 1, DateHelper.getDate(2016, 2, 20), DateHelper.getDate(2016, 2, 24) ) );
        gTrips.get(1).setLocalId(1);

        gFriends = new ArrayList<TriFriend>();
        gFriends.add( new TriFriend( "A.y Chen", "", "skyjo3@gmail.com", "0921866037" ) );
        gFriends.get(0).setlocalId(0);
        gFriends.add( new TriFriend( "Louis Yu", "", "louis29418401@gmail.com", "0915332210" ) );
        gFriends.get(1).setlocalId(1);
        gFriends.add( new TriFriend( "Louis Mom", "", "louis29418401@gmail.com", "0915900390" ) );
        gFriends.get(2).setlocalId(2);

        gParticipations = new ArrayList<TriParticipation>();
        gParticipations.add( new TriParticipation( gTrips.get(0), gFriends.get(0) ) );
        gParticipations.get(0).setlocalId(0);
        gParticipations.add( new TriParticipation( gTrips.get(0), gFriends.get(1) ) );
        gParticipations.get(1).setlocalId(1);
        gParticipations.add( new TriParticipation( gTrips.get(1), gFriends.get(0) ) );
        gParticipations.get(2).setlocalId(2);
        gParticipations.add( new TriParticipation( gTrips.get(1), gFriends.get(1) ) );
        gParticipations.get(3).setlocalId(3);
        gParticipations.add( new TriParticipation( gTrips.get(1), gFriends.get(2) ) );
        gParticipations.get(4).setlocalId(4);

        gItems = new ArrayList<TriItem>();
        gItems.add( new TriItem(" for eat", 500, gFriends.get(1).getLocalId(), gTrips.get(0).getLocalId(), 0, "bento", DateHelper.getDate(2015, 12, 29) ));
        gItems.get(0).setlocalId(0);
        gItems.add( new TriItem(" refill oil", 300, gFriends.get(0).getLocalId(), gTrips.get(0).getLocalId(), 0, "oil", DateHelper.getDate(2015, 12, 29) ) );
        gItems.get(1).setlocalId(1);
        gItems.add( new TriItem(" for drinks", 200, gFriends.get(1).getLocalId(), gTrips.get(0).getLocalId(), 0, "drinks", DateHelper.getDate(2015, 12, 29) ) );
        gItems.get(2).setlocalId(2);
        gItems.add( new TriItem(" for eat", 1000, gFriends.get(0).getLocalId(), gTrips.get(1).getLocalId(), 0, "123", DateHelper.getDate(2016, 2, 10) ));
        gItems.get(3).setlocalId(3);
        gItems.add( new TriItem(" for play", 700, gFriends.get(0).getLocalId(), gTrips.get(1).getLocalId(), 0, "123", DateHelper.getDate(2016, 2, 10) ));
        gItems.get(4).setlocalId(4);
        gItems.add( new TriItem(" for run", 300, gFriends.get(0).getLocalId(), gTrips.get(1).getLocalId(), 0, "321", DateHelper.getDate(2016, 2, 10) ));
        gItems.get(5).setlocalId(5);

        gDepts = new ArrayList<TriDept>();
        gDepts.add( new TriDept( gItems.get(0), gFriends.get(1), 30, 500 ) );
        gDepts.get(0).setlocalId(0);
        gDepts.add( new TriDept( gItems.get(0), gFriends.get(0), 70, 0 ) );
        gDepts.get(1).setlocalId(1);
        gDepts.add( new TriDept( gItems.get(1), gFriends.get(1), 50, 200 ) );
        gDepts.get(2).setlocalId(2);
        gDepts.add( new TriDept( gItems.get(1), gFriends.get(0), 50, 100 ) );
        gDepts.get(3).setlocalId(3);
        gDepts.add( new TriDept( gItems.get(2), gFriends.get(1), 50, 0 ) );
        gDepts.get(4).setlocalId(4);
        gDepts.add( new TriDept( gItems.get(2), gFriends.get(0), 50, 200 ) );
        gDepts.get(5).setlocalId(5);
    }

    public ArrayList<TriTrip> getgTrips() {
        return gTrips;
    }
    public ArrayList<TriTrip> getgOngoingTrips() {
        ArrayList<TriTrip> trips = new ArrayList<TriTrip>();

        for ( TriTrip t : getgTrips() ) {
            if (DateHelper.getCalendarDay(0).getTime().after(t.getDateFrom()) &&
                DateHelper.getCalendarDay(0).getTime().before(t.getDateTo()) ) {
                trips.add(t);
            }
        }
        return trips;
    }
    public ArrayList<TriTrip> getgHistoryTrips() {
        ArrayList<TriTrip> trips = new ArrayList<TriTrip>();

        for ( TriTrip t : getgTrips() ) {
            if (DateHelper.getCalendarDay(0).getTime().after(t.getDateFrom()) &&
                    DateHelper.getCalendarDay(0).getTime().before(t.getDateTo()) ) {}
            else {
                trips.add(t);
            }
        }

        return trips;
    }
    public void setgTrips(ArrayList<TriTrip> gTrips) {
        this.gTrips = gTrips;
    }

    public ArrayList<TriFriend> getgFriends() {
        return gFriends;
    }

    public void setgFriends(ArrayList<TriFriend> gFriends) {
        this.gFriends = gFriends;
    }

    public ArrayList<TriParticipation> getgParticipations() {
        return gParticipations;
    }

    public void setgParticipations(ArrayList<TriParticipation> gParticipations) {
        this.gParticipations = gParticipations;
    }

    public ArrayList<TriItem> getgItems() {
        return gItems;
    }

    public void setgItems(ArrayList<TriItem> gItems) {
        this.gItems = gItems;
    }

    public ArrayList<TriDept> getgDepts() {
        return gDepts;
    }

    public void setgDepts(ArrayList<TriDept> gDepts) {
        this.gDepts = gDepts;
    }
}
