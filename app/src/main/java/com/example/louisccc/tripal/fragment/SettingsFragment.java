package com.example.louisccc.tripal.fragment;

import android.os.Bundle;

import com.example.louisccc.tripal.R;
import com.github.machinarius.preferencefragment.PreferenceFragment;

/**
 * Created by louisccc on 1/3/16.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
