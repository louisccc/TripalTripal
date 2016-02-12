package com.example.louisccc.tripal.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.louisccc.tripal.R;

/**
 * Created by louisccc on 1/3/16.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
