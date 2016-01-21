package com.example.krismobile.contractors.documents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.example.krismobile.R;
import com.example.krismobile.contractors.Contractor;

public class ContractorDocumentsActivity extends Activity {

	private Contractor contractor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_documents);
		
		Bundle args = this.getIntent().getExtras();
		contractor = (Contractor)args.getParcelable("Contractor");
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ContractorDocumentsFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.contractors_documents, menu);
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
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public Contractor getContractor() {
		return contractor;
	}
}
