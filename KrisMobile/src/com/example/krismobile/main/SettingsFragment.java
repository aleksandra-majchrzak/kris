package com.example.krismobile.main;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.krismobile.R;

public class SettingsFragment extends PreferenceFragment {

	public SettingsFragment(){
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);
    }

}
