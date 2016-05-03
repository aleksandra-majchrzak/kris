package com.example.krismobile.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.couchbase.lite.CouchbaseLiteException;
import com.example.krismobile.R;
import com.example.krismobile.database.DatabaseManager;


public class MainActivity extends Activity {
	
	EditText loginEditText;
	EditText pswdEditText;
	Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        
        setContentView(R.layout.activity_main);
       
        
        loadControls();
        
        final SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
        /*
        if(! shared.contains("server_address_preference")){
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setTitle(R.string.config_parameters)
			.setView(getLayoutInflater().inflate(R.layout.config_dialog, null))
			.setPositiveButton(R.string.ok, new OnClickListener(){
	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					View rootView = getLayoutInflater().inflate(R.layout.config_dialog, null);
					EditText serverAddress = (EditText) rootView.findViewById(R.id.server_address_editText);
					EditText portNumber = (EditText) rootView.findViewById(R.id.port_number_editText);
					EditText bucketName = (EditText) rootView.findViewById(R.id.bucket_name_editText);
					
					shared.edit()
						.putString(SettingsFragment.SERVER_ADDRESS_PREF, serverAddress.getText().toString())
						.putString(SettingsFragment.PORT_NUMBER_PREF, portNumber.getText().toString())
						.putString(SettingsFragment.BUCKET_NAME_PREF, bucketName.getText().toString())
						.commit();
				}
				
			})
			.create().show();
        }
        */
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void loadControls(){
    	this.loginEditText = (EditText) findViewById(R.id.login_edit_text);
    	this.pswdEditText = (EditText) findViewById(R.id.pswd_edit_text);
    	this.loginButton = (Button) findViewById(R.id.login_button);
    	
    	loginButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				String login = loginEditText.getText().toString();
				String password = pswdEditText.getText().toString();
				
				Intent intent = new Intent(MainActivity.this, MenuActivity.class);
				
				/* TODO 
				 * autentykacja logowania - gdzie zapisujemy te dane i czy s¹ jakos szyfrowane*/
				startActivity(intent);
				
				MainActivity.this.finish();
			}
    		
    	});
    }
}
