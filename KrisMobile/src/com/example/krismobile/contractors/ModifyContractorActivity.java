package com.example.krismobile.contractors;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.ContractorsManager;

public class ModifyContractorActivity extends Activity {

	private Contractor contractor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Bundle args = getIntent().getExtras();
		String contractorId = args.getString("contractorId");
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if(contractorId.equals("")){
			getActionBar().setTitle(getResources().getString(R.string.action_add_contractor));
			contractor = new Contractor();
		
		} else{
			Document contractorDoc = ContractorsManager.getInstance().getContractor(contractorId);
			Map<?,?> props = contractorDoc.getProperties();
			contractor = new Contractor(contractorDoc);
			
			getActionBar().setTitle((String)props.get("Code"));
		}
		
		setContentView(R.layout.activity_modify_contractor);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ModifyContractorFragment()).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.contractor_modify, menu);
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

	public Contractor getContractor() {
		return contractor;
	}

}