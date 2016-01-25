package com.example.louisccc.tripal;

import android.test.AndroidTestCase;

/**
 * Created by louisccc on 1/24/16.
 */
public class DatabaseTest extends AndroidTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }


    public void test1 () throws Exception{
        DBManager db = new DBManager(getContext());
        db.open();
        db.close();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
