package com.example.louisccc.tripal;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by louisccc on 1/6/16.
 */
public class SettingsActivity extends Activity{
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
