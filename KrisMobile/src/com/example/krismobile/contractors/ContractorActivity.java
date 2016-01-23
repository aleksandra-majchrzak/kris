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

public class ContractorActivity extends Activity {
	
	public static final int REQUEST_REFRESH_VIEW = 1;
	public static final int RESULT_OK = 1;
	private Contractor contractor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Bundle args = getIntent().getExtras();
		String contractorId = args.getString("contractorId");
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		

		Document contractorDoc = ContractorsManager.getInstance().getContractor(contractorId);
		Map<?,?> props = contractorDoc.getProperties();
		contractor = new Contractor(contractorDoc.getId(), 
								(String)props.get("Code"), 
								(Integer)props.get("TypeId"), 
								(String)props.get("Address"), 
								(String)props.get("Description"),
								(String)props.get("NIP"));
			
		getActionBar().setTitle((String)props.get("Code"));
		
		setContentView(R.layout.activity_contractor);
		
		if(getFragmentManager().findFragmentById(R.id.container) == null)
			getFragmentManager().beginTransaction()
				.add(R.id.container, new ContractorFragment()).commit();

	}
	
	public Contractor reloadContractor(){
		Document contractorDoc = ContractorsManager.getInstance().getContractor(contractor.getId());
		Map<?,?> props = contractorDoc.getProperties();
		
		contractor.setCode((String)props.get("Code"));
		contractor.setTypeId((Integer)props.get("TypeId"));
		contractor.setAddress((String)props.get("Address"));
		contractor.setDescription((String)props.get("Description"));
		contractor.setNIP((String)props.get("NIP"));
		
		getActionBar().setTitle((String)props.get("Code"));
		
		return contractor;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.contractor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		
		}else if(id == android.R.id.home){
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public Contractor getContractor() {
		return contractor;
	}

}
