package com.example.krismobile.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krismobile.R;
import com.example.krismobile.database.DatabaseManager;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

	public static String SERVER_ADDRESS_PREF = "server_address_preference";
	public static String PORT_NUMBER_PREF = "port_number_preference";
	public static String BUCKET_NAME_PREF = "bucket_name_preference";
	private static String DELETE_DATABASE = "delete_database";
	
	private EditTextPreference serverAddressPref;
	private EditTextPreference portNumberPref;
	private EditTextPreference bucketNamePref;
	private Preference clearDatabase;
	
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
		
		clearDatabase = (Preference) findPreference(DELETE_DATABASE);
		clearDatabase.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(SettingsFragment.this.getActivity());
				builder.setMessage(R.string.do_you_want_to_delete_database)
					.setPositiveButton(R.string.yes, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							DatabaseManager.getInstance().deleteDatabase();
							
						}
					})
					.setNegativeButton(R.string.no, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						
						}
					})
					.create().show();
				
				
				return true;
			}
		});
		
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
