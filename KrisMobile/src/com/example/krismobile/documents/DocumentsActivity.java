package com.example.krismobile.documents;

import com.example.krismobile.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class DocumentsActivity extends Activity {
	
	public static final int REQUEST_ADD_NEW_DOCUMENT = 1;
	public static final int REQUEST_DELETE_DOCUMENT = 2;
	public static final int RESULT_OK = 1;
	public static final int RESULT_ERROR = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_documents);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new DocumentsFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.documents, menu);
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

}
