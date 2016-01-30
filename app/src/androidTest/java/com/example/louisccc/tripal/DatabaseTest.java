package com.example.louisccc.tripal;

import android.test.AndroidTestCase;

import com.example.louisccc.tripal.com.example.louisccc.tripal.utility.DateHelper;
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
        TriTrip trip;
        long ret_id;
        DBManager db = new DBManager(getContext());
        db.open();

        db.deleteAllTable(); // clean all tables data

        trip = new TriTrip(); // test clean constructor
        ret_id = db.createTrip(trip);
        Assert.assertTrue( ret_id != -1 );

        trip = new TriTrip("Mountain Ali", 15000, 1, DateHelper.getDate(2015, 12, 31), DateHelper.getDate(2016, 1, 2) );
        ret_id = db.createTrip(trip);
        Assert.assertTrue( ret_id != -1 );

        trip = new TriTrip("Hong Kong trip", 20000, 1, DateHelper.getDate(2016, 2, 20), DateHelper.getDate(2016, 2, 24) );
        ret_id = db.createTrip(trip);
        Assert.assertTrue( ret_id != -1 );

        db.exportDB();

        db.close();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
