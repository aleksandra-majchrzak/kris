package com.example.krismobile.main;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krismobile.R;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

	private static String SERVER_ADDRESS_PREF = "server_address_preference";
	
	private EditTextPreference serverAddressPref;
	
	public SettingsFragment(){
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		serverAddressPref = (EditTextPreference) findPreference(SERVER_ADDRESS_PREF);
		serverAddressPref.setSummary(serverAddressPref.getText().toString());
		
		
		return rootView;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		if(key.equals(SERVER_ADDRESS_PREF)){
		//	EditTextPreference serverAddressPref = (EditTextPreference) findPreference(SERVER_ADDRESS_PREF);
			serverAddressPref.setSummary(serverAddressPref.getText().toString());
		}
		
	}
	
	@Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
