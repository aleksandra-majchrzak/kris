package com.example.krismobile.contractors;

import com.example.krismobile.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class ContractorsActivity extends Activity {
	
	public static final int REQUEST_ADD_NEW_CONTRACTOR = 1;
	public static final int REQUEST_DELETE_CONTRACTOR = 2;
	public static final int RESULT_OK = 1;
	public static final int RESULT_ERROR = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_contractors);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ContractorsFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.contractors, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if(id == android.R.id.home){
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
//		if(requestCode == RESULT_ADD_NEW_CONTRACTOR){
//			if(resultCode == RESULT_OK){
//				
//			}
//		}
//		else
			super.onActivityResult(requestCode, resultCode, data);
	}
}
