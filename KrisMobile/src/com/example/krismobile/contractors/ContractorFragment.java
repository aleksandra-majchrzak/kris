package com.example.krismobile.contractors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.krismobile.R;
import com.example.krismobile.main.base.FragmentBase;

public class ContractorFragment extends FragmentBase {

	private Contractor contractor;
	private TextView contractorCodeTextView;
	private TextView contractorTypeTextView;
	private TextView contractorAddressTextView;
	private TextView contractorNIPTextView;
	private TextView contractorDescriptionTextView;
	
	public ContractorFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_contractor,
				container, false);
		
		this.contractor = ((ContractorActivity) context).getContractor();

		loadControls(rootView);
		
		return rootView;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if (id == R.id.action_edit_contractor) {
			
			Intent intent = new Intent(context, ModifyContractorActivity.class);
			
			Bundle args = new Bundle();
			args.putString("contractorId", contractor.getId());
			intent.putExtras(args);
			
			startActivityForResult(intent,ContractorsActivity.RESULT_ADD_NEW_CONTRACTOR);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	
		if(requestCode == ContractorsActivity.RESULT_ADD_NEW_CONTRACTOR){
			if(resultCode == ContractorsActivity.RESULT_OK){

				contractor = ((ContractorActivity)context).reloadContractor();
				fillControls();	
			}
		}

	}
		
	private void loadControls(View rootView){

		contractorTypeTextView = (TextView) rootView.findViewById(R.id.contractor_type_textView);
		contractorAddressTextView = (TextView) rootView.findViewById(R.id.contractor_address_textView);
		contractorNIPTextView = (TextView) rootView.findViewById(R.id.contractor_NIP_textView);
		contractorDescriptionTextView = (TextView) rootView.findViewById(R.id.contractor_description_textView);
		
		if(!contractor.getId().equals(""))
			fillControls();	
	}
	
	private void fillControls(){
		
		contractorTypeTextView.setText(context.getResources().getStringArray(R.array.contractor_types)[contractor.getTypeId() -1]);
		contractorAddressTextView.setText(contractor.getAddress());
		contractorNIPTextView.setText(contractor.getNIP());
		contractorDescriptionTextView.setText(contractor.getDescription());
	}
}