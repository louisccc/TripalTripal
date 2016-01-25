package com.example.louisccc.tripal;

import android.test.AndroidTestCase;

import com.example.louisccc.tripal.model.TriRecord;

import junit.framework.Assert;

/**
 * Created by louisccc on 1/24/16.
 */
public class TriRecordTest extends AndroidTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }


    public void test1 () throws Exception{
        TriRecord record = null;
        Assert.assertTrue(record == null);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
