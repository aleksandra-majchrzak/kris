package com.example.krismobile.contractors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.krismobile.R;
import com.example.krismobile.database.managers.ContractorsManager;
import com.example.krismobile.main.base.FragmentBase;

public class ModifyContractorFragment  extends FragmentBase {
	
	private Contractor contractor;
	private EditText contractorCodeEditText;
	private Spinner contractorTypeSpinner;
	private EditText contractorAddressEditText;
	private EditText contractorNIPEditText;
	private EditText contractorDescriptionEditText;


	public ModifyContractorFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_contractor_modify,
				container, false);
		
		this.contractor = ((ModifyContractorActivity) context).getContractor();

		loadControls(rootView);
		
		return rootView;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if (id == R.id.action_save_contractor) {
			
			saveContractor();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
		
	private void loadControls(View rootView){
		contractorCodeEditText = (EditText) rootView.findViewById(R.id.contractor_code_editText);
		contractorTypeSpinner = (Spinner) rootView.findViewById(R.id.contractor_type_spinner);
		contractorAddressEditText = (EditText) rootView.findViewById(R.id.contractor_address_editText);
		contractorNIPEditText = (EditText) rootView.findViewById(R.id.contractor_NIP_editText);
		contractorDescriptionEditText = (EditText) rootView.findViewById(R.id.contractor_description_editText);
		
		if(!contractor.getId().equals("")){
			contractorCodeEditText.setText(contractor.getCode());
			contractorTypeSpinner.setSelection(contractor.getTypeId() -1 );
			contractorAddressEditText.setText(contractor.getAddress());
			contractorNIPEditText.setText(contractor.getNIP());
			contractorDescriptionEditText.setText(contractor.getDescription());
		}
	}
		
	private void saveContractor(){
			
		// tu powinny byc sprawdzane jakies warunki odnosnie zapisu kontrahenta

		contractor.setTypeId(contractorTypeSpinner.getSelectedItemPosition() +1);
		contractor.setCode(contractorCodeEditText.getText().toString());
		contractor.setAddress(contractorAddressEditText.getText().toString());
		contractor.setNIP(contractorNIPEditText.getText().toString());
		contractor.setDescription(contractorDescriptionEditText.getText().toString());
			
		ContractorsManager.getInstance().saveContractor(contractor);
			
		getActivity().setResult(ContractorsActivity.RESULT_OK);
		getActivity().finish();
	}

}
