package com.example.louisccc.tripal;

import android.test.AndroidTestCase;

import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriParticipation;
import com.example.louisccc.tripal.utility.DateHelper;
import com.example.louisccc.tripal.model.DBManager;
import com.example.louisccc.tripal.model.TriTrip;

import junit.framework.Assert;

/**
 * Created by louisccc on 1/24/16.
 */
public class DatabaseTest extends AndroidTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }


    public void test1 () throws Exception{
        long ret_id;
        DBManager db = new DBManager(getContext());
        db.open();

        db.deleteAllTable(); // clean all tables data

        /*
            create Friends
         */

        TriFriend friend1, friend2, friend3, friend4;
        friend1 = new TriFriend();
        ret_id = db.createFriend(friend1);
        friend1.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        friend2 = new TriFriend( "A.y Chen", "", "skyjo3@gmail.com", "0921866037" );
        ret_id = db.createFriend(friend2);
        friend2.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        friend3 = new TriFriend( "Louis Yu", "", "louis29418401@gmail.com", "0915332210" );
        ret_id = db.createFriend(friend3);
        friend3.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        friend4 = new TriFriend( "Louis Mom", "", "louis29418401@gmail.com", "0915900390" );
        ret_id = db.createFriend(friend4);
        friend4.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );
        /*
            create Trips
         */
        TriTrip trip1, trip2, trip3;
        trip1 = new TriTrip(); // test clean constructor
        ret_id = db.createTrip(trip1);
        trip1.setLocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        trip2 = new TriTrip("Mountain Ali", 15000, 1, DateHelper.getDate(2015, 12, 31), DateHelper.getDate(2016, 1, 2) );
        ret_id = db.createTrip(trip2);
        trip2.setLocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        trip3 = new TriTrip("Hong Kong trip", 20000, 1, DateHelper.getDate(2016, 2, 20), DateHelper.getDate(2016, 2, 24) );
        ret_id = db.createTrip(trip3);
        trip3.setLocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        /*
            create participation
         */
        TriParticipation part1, part2, part3, part4, part5;
        part1 = new TriParticipation(trip2, friend2);
        part2 = new TriParticipation(trip2, friend3);
        part3 = new TriParticipation(trip3, friend2);
        part4 = new TriParticipation(trip3, friend3);
        part5 = new TriParticipation(trip3, friend4);

        ret_id = db.createParticipation(part1);
        part1.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        ret_id = db.createParticipation(part2);
        part2.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        ret_id = db.createParticipation(part3);
        part3.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        ret_id = db.createParticipation(part4);
        part4.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        ret_id = db.createParticipation(part5);
        part5.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        db.exportDB();

        db.close();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
