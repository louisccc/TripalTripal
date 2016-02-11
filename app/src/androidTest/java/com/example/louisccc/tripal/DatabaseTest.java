package com.example.louisccc.tripal;

import android.test.AndroidTestCase;

import com.example.louisccc.tripal.model.TriDept;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.model.TriParticipation;
import com.example.louisccc.tripal.utility.DateHelper;
import com.example.louisccc.tripal.model.DBManager;
import com.example.louisccc.tripal.model.TriTrip;

import junit.framework.Assert;

import java.util.Date;

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

        friend1 = new TriFriend(); // test clean constructor
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
        ret_id = db.createParticipation(part1);
        part1.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        part2 = new TriParticipation(trip2, friend3);
        ret_id = db.createParticipation(part2);
        part2.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        part3 = new TriParticipation(trip3, friend2);
        ret_id = db.createParticipation(part3);
        part3.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        part4 = new TriParticipation(trip3, friend3);
        ret_id = db.createParticipation(part4);
        part4.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        part5 = new TriParticipation(trip3, friend4);
        ret_id = db.createParticipation(part5);
        part5.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        /*
            create items
         */
        TriItem item1, item2, item3, item4, item5, item6, item7;

        item1 = new TriItem();
        ret_id = db.createItem(item1);
        item1.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        item2 = new TriItem(" for eat", 500, friend3.getLocalId(), trip2.getLocalId(), 0, "bento", DateHelper.getDate(2015, 12, 29) );
        ret_id = db.createItem(item2);
        item2.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        item3 = new TriItem(" refill oil", 300, friend2.getLocalId(), trip2.getLocalId(), 0, "oil", DateHelper.getDate(2015, 12, 29) );
        ret_id = db.createItem(item3);
        item3.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        item4 = new TriItem(" for drinks", 200, friend3.getLocalId(), trip2.getLocalId(), 0, "drinks", DateHelper.getDate(2015, 12, 29) );
        ret_id = db.createItem(item4);
        item4.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        item5 = new TriItem(" for eat", 1000, friend2.getLocalId(), trip3.getLocalId(), 0, "123", DateHelper.getDate(2016, 2, 10) );
        ret_id = db.createItem(item5);
        item5.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        item6 = new TriItem(" for play", 700, friend2.getLocalId(), trip3.getLocalId(), 0, "123", DateHelper.getDate(2016, 2, 10) );
        ret_id = db.createItem(item6);
        item6.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        item7 = new TriItem(" for run", 300, friend2.getLocalId(), trip3.getLocalId(), 0, "321", DateHelper.getDate(2016, 2, 10) );
        ret_id = db.createItem(item7);
        item7.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        /*
            create dept relation
         */
        TriDept dept1, dept2, dept3, dept4, dept5, dept6, dept7;

        dept1 = new TriDept();
        ret_id = db.createDept(dept1);
        dept1.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        dept2 = new TriDept( item2, friend3, 30, 500 );
        ret_id = db.createDept(dept2);
        dept2.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        dept3 = new TriDept( item2, friend2, 70, 0 );
        ret_id = db.createDept(dept3);
        dept3.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        dept4 = new TriDept( item3, friend3, 50, 200 );
        ret_id = db.createDept(dept4);
        dept4.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        dept5 = new TriDept( item3, friend2, 50, 100 );
        ret_id = db.createDept(dept5);
        dept5.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        dept6 = new TriDept( item4, friend3, 50, 0 );
        ret_id = db.createDept(dept6);
        dept6.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        dept7 = new TriDept( item4, friend2, 50, 200 );
        ret_id = db.createDept(dept7);
        dept7.setlocalId(ret_id);
        Assert.assertTrue( ret_id != -1 );

        db.exportDB();

        db.close();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
