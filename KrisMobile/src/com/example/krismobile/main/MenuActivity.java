package com.example.krismobile.main;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.example.krismobile.R;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.main.adapters.MainSectionsPagerAdapter;

public class MenuActivity extends Activity {

	MainSectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
		
		setContentView(R.layout.activity_menu);

		mSectionsPagerAdapter = new MainSectionsPagerAdapter(getFragmentManager(), this);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		try {
			
			DatabaseManager.getManagerInstance(this);
			Database database = DatabaseManager.getDatabaseInstance();
			//database.delete();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch(CouchbaseLiteException e){
			
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu, menu);
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
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		DatabaseManager.close();
	}
}
