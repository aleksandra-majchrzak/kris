package com.example.krismobile.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
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
    	
    	loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String login = loginEditText.getText().toString();
				String password = pswdEditText.getText().toString();
				
				Intent intent = new Intent(MainActivity.this, MenuActivity.class);
				
				/* TODO 
				 * autentykacja logowania - gdzie zapisujemy te dane i czy s¹ jakos szyfrowane*/
				startActivity(intent);
			}
    		
    	});
    }
}
