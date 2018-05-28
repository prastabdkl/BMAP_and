package com.example.prastabdkl.bmap;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Prastab Dhakal on 12/17/2016.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
