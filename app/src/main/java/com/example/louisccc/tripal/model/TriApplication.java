package com.example.louisccc.tripal.model;

import android.app.Application;

import com.example.louisccc.tripal.utility.DateHelper;

import junit.framework.Assert;

import java.sql.SQLException;
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

    private DBManager mDB;
    @Override
    public void onCreate() {
        super.onCreate();
        gTrips = new ArrayList<>();
        gFriends = new ArrayList<>();
        gParticipations = new ArrayList<>();
        gItems = new ArrayList<>();
        gDepts = new ArrayList<>();
        mDB = new DBManager(this);
        DBInit();
//        testInit();
    }

    private void DBInit() {
        try {
            mDB.open();

            if ( mDB.getTrips().size() >= 1 ){
                // second time will enter here
                return;
            }

            // first time will run till here.
            long ret_id;

            TriFriend amy = new TriFriend("A.y Chen", "", "skyjo3@gmail.com", "0921866037" );
            ret_id = mDB.createFriend(amy);
            amy.setlocalId(ret_id);
            Assert.assertTrue( ret_id != -1 );

            TriFriend astrid = new TriFriend( "Astrid Li", "", "astridLi@gmail.com", "0123456789" );
            ret_id = mDB.createFriend(astrid);
            astrid.setlocalId(ret_id);
            Assert.assertTrue( ret_id != -1 );

            gFriends.add(amy);
            gFriends.add(astrid);

            TriTrip nagoya = new TriTrip("Nagoya Trip with Astrid", 40000, 1, DateHelper.getDate(2016, 2, 16), DateHelper.getDate(2016, 2, 20) );
            ret_id = mDB.createTrip(nagoya);
            nagoya.setLocalId(ret_id);
            Assert.assertTrue( ret_id != -1 );

            gTrips.add(nagoya);

            TriParticipation part1 = new TriParticipation(nagoya, amy);
            ret_id = mDB.createParticipation(part1);
            part1.setlocalId(ret_id);
            Assert.assertTrue( ret_id != -1 );

            TriParticipation part2 = new TriParticipation(nagoya, astrid);
            ret_id = mDB.createParticipation(part2);
            part2.setlocalId(ret_id);
            Assert.assertTrue( ret_id != -1 );

            gParticipations.add(part1);
            gParticipations.add(part2);



            mDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void testInit() {
        gTrips.add( new TriTrip("Mountain Ali", 15000, 1, DateHelper.getDate(2015, 12, 31), DateHelper.getDate(2016, 1, 2) ) );
        gTrips.get(0).setLocalId(0);
        gTrips.add( new TriTrip("Hong Kong trip", 20000, 1, DateHelper.getDate(2016, 2, 20), DateHelper.getDate(2016, 2, 24) ) );
        gTrips.get(1).setLocalId(1);

        gFriends.add( new TriFriend( "A.y Chen", "", "skyjo3@gmail.com", "0921866037" ) );
        gFriends.get(0).setlocalId(0);
        gFriends.add( new TriFriend( "Louis Yu", "", "louis29418401@gmail.com", "0915332210" ) );
        gFriends.get(1).setlocalId(1);
        gFriends.add( new TriFriend( "Louis Mom", "", "louis29418401@gmail.com", "0915900390" ) );
        gFriends.get(2).setlocalId(2);

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

        gItems.add( new TriItem(" for eat", 500, gFriends.get(1).getLocalId(), gTrips.get(0).getLocalId(), 0, "bento", DateHelper.getDate(2015, 12, 29) ));
        gItems.get(0).setLocalId(0);
        gItems.add( new TriItem(" refill oil", 300, gFriends.get(0).getLocalId(), gTrips.get(0).getLocalId(), 0, "oil", DateHelper.getDate(2015, 12, 29) ) );
        gItems.get(1).setLocalId(1);
        gItems.add( new TriItem(" for drinks", 200, gFriends.get(1).getLocalId(), gTrips.get(0).getLocalId(), 0, "drinks", DateHelper.getDate(2015, 12, 29) ) );
        gItems.get(2).setLocalId(2);
        gItems.add( new TriItem(" for eat", 1000, gFriends.get(0).getLocalId(), gTrips.get(1).getLocalId(), 0, "123", DateHelper.getDate(2016, 2, 10) ));
        gItems.get(3).setLocalId(3);
        gItems.add( new TriItem(" for play", 700, gFriends.get(0).getLocalId(), gTrips.get(1).getLocalId(), 0, "123", DateHelper.getDate(2016, 2, 10) ));
        gItems.get(4).setLocalId(4);
        gItems.add( new TriItem(" for run", 300, gFriends.get(0).getLocalId(), gTrips.get(1).getLocalId(), 0, "321", DateHelper.getDate(2016, 2, 10) ));
        gItems.get(5).setLocalId(5);

        gDepts.add( new TriDept( gItems.get(0), gFriends.get(1), 30, 500 ) );
        gDepts.get(0).setLocalId(0);
        gDepts.add( new TriDept( gItems.get(0), gFriends.get(0), 70, 0 ) );
        gDepts.get(1).setLocalId(1);
        gDepts.add( new TriDept( gItems.get(1), gFriends.get(1), 50, 200 ) );
        gDepts.get(2).setLocalId(2);
        gDepts.add( new TriDept( gItems.get(1), gFriends.get(0), 50, 100 ) );
        gDepts.get(3).setLocalId(3);
        gDepts.add( new TriDept( gItems.get(2), gFriends.get(1), 50, 0 ) );
        gDepts.get(4).setLocalId(4);
        gDepts.add( new TriDept( gItems.get(2), gFriends.get(0), 50, 200 ) );
        gDepts.get(5).setLocalId(5);
    }

    public void refreshGlobals () {

        Assert.assertTrue(mDB != null);

        try {
            mDB.open();

            ArrayList<TriTrip> trips = mDB.getTrips();
            ArrayList<TriFriend> friends = mDB.getFriends();
            ArrayList<TriParticipation> parts = mDB.getParticipations();
            ArrayList<TriItem> items = mDB.getItems();
            ArrayList<TriDept> depts = mDB.getDepts();

            // update global variables after db query as expected
            setgTrips(trips);
            setgFriends(friends);
            setgParticipations(parts);
            setgItems(items);
            setgDepts(depts);

            mDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<TriTrip> getgTrips() {
        return gTrips;
    }
    public void setgTrips(ArrayList<TriTrip> gTrips) {
        this.gTrips.clear();
        this.gTrips.addAll(gTrips);
    }
    public ArrayList<TriTrip> getgOngoingTrips() {
        ArrayList<TriTrip> trips = new ArrayList<TriTrip>();

        for ( TriTrip t : getgTrips() ) {
            if ( DateHelper.isDateOverlapToday(t.getDateFrom(), t.getDateTo()) ) {
                trips.add(t);
            }
        }
        return trips;
    }
    public ArrayList<TriTrip> getgHistoryTrips() {
        ArrayList<TriTrip> trips = new ArrayList<TriTrip>();

        for ( TriTrip t : getgTrips() ) {
            if ( ! DateHelper.isDateOverlapToday(t.getDateFrom(), t.getDateTo()) ) {
                trips.add(t);
            }
        }

        return trips;
    }


    public ArrayList<TriFriend> getgFriends() {
        return gFriends;
    }
    public void setgFriends(ArrayList<TriFriend> gFriends) {
        this.gFriends.clear();
        this.gFriends.addAll(gFriends);
    }

    public ArrayList<TriParticipation> getgParticipations() {
        return gParticipations;
    }
    public void setgParticipations(ArrayList<TriParticipation> gParticipations) {
        this.gParticipations.clear();
        this.gParticipations.addAll(gParticipations);
    }

    public ArrayList<TriItem> getgItems() {
        return gItems;
    }
    public void setgItems(ArrayList<TriItem> gItems) {
        this.gItems.clear();
        this.gItems.addAll(gItems);
    }

    public ArrayList<TriDept> getgDepts() {
        return gDepts;
    }
    public void setgDepts(ArrayList<TriDept> gDepts) {
        this.gDepts.clear();
        this.gDepts.addAll(gDepts);
    }
}
