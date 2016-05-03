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

	public static String SERVER_ADDRESS_PREF = "server_address_preference";
	public static String PORT_NUMBER_PREF = "port_number_preference";
	public static String BUCKET_NAME_PREF = "bucket_name_preference";
	
	private EditTextPreference serverAddressPref;
	private EditTextPreference portNumberPref;
	private EditTextPreference bucketNamePref;
	
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
		String serverAddressPrefText = serverAddressPref.getText() == null ? "" : serverAddressPref.getText();
		serverAddressPref.setSummary(serverAddressPrefText);
		
		portNumberPref = (EditTextPreference) findPreference(PORT_NUMBER_PREF);
		String portNumberPrefText = portNumberPref.getText() == null ? "" : portNumberPref.getText();
		portNumberPref.setSummary(portNumberPrefText);
		
		bucketNamePref = (EditTextPreference) findPreference(BUCKET_NAME_PREF);
		String bucketNamePrefText = bucketNamePref.getText() == null ? "" : bucketNamePref.getText();
		bucketNamePref.setSummary(bucketNamePrefText);
		
		
		return rootView;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		if(key.equals(SERVER_ADDRESS_PREF)){
			serverAddressPref.setSummary(serverAddressPref.getText().toString());
		}
		if(key.equals(PORT_NUMBER_PREF)){
			portNumberPref.setSummary(portNumberPref.getText().toString());
			}
		if(key.equals(BUCKET_NAME_PREF)){
			bucketNamePref.setSummary(bucketNamePref.getText().toString());
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
