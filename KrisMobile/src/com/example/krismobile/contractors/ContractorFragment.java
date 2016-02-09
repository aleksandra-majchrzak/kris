package com.example.krismobile.contractors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krismobile.R;
import com.example.krismobile.contractors.documents.ContractorDocumentsActivity;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.database.managers.ContractorsManager;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.documents.DocumentActivity;
import com.example.krismobile.documents.KrisDocument;
import com.example.krismobile.main.base.FragmentBase;
import com.example.krismobile.main.utilities.DateUtilities;

public class ContractorFragment extends FragmentBase {

	private Contractor contractor;
//	private TextView contractorCodeTextView;
	private TextView contractorTypeTextView;
	private TextView contractorAddressTextView;
	private TextView contractorNIPTextView;
	private TextView contractorDescriptionTextView;
	private TextView contractorLatestDocumentTextView;
	private TextView contractorLatestDocumentDateTextView;
	
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

//		DocumentsManager.getInstance().deleteAllKrisDocuments();
		

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
			
			startActivityForResult(intent,ContractorActivity.REQUEST_REFRESH_VIEW);
			return true;
		}
		else if(id == R.id.action_delete_contractor){
			
			deleteContractor();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	
	/*	if(requestCode == ContractorsActivity.REQUEST_ADD_NEW_CONTRACTOR){
			if(resultCode == ContractorsActivity.RESULT_OK){

				contractor = ((ContractorActivity)context).reloadContractor();
				fillControls();	
			}
		}
		else */ if(requestCode == ContractorActivity.REQUEST_REFRESH_VIEW){
			if(resultCode == ContractorActivity.RESULT_OK || resultCode == DocumentActivity.RESULT_OK){

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
		contractorLatestDocumentTextView = (TextView) rootView.findViewById(R.id.contractor_document_number_textView);
		contractorLatestDocumentDateTextView = (TextView) rootView.findViewById(R.id.contractor_document_date_textView);
		
		LinearLayout documentLinearLayout = (LinearLayout) rootView.findViewById(R.id.contractor_document_row_ll);
		documentLinearLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, ContractorDocumentsActivity.class);
				//Intent intent = new Intent(context, TmpPagedActivity.class);
				
				Bundle args = new Bundle();
				args.putParcelable("Contractor", contractor);
				intent.putExtras(args);
				
				startActivityForResult(intent, ContractorActivity.REQUEST_REFRESH_VIEW);
				
			}
		});
		
		ImageView menuDocumentsImageView = (ImageView) rootView.findViewById(R.id.contractor_documents_more_imageView);
/*		menuDocumentsImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
*/		
		if(!contractor.getId().equals(""))
			fillControls();	
	}
	
	private void fillControls(){
		
		contractorTypeTextView.setText(context.getResources().getStringArray(R.array.contractor_types)[contractor.getTypeId() -1]);
		contractorAddressTextView.setText(contractor.getAddress());
		contractorNIPTextView.setText(contractor.getNIP());
		contractorDescriptionTextView.setText(contractor.getDescription());
		
		KrisDocument latestDocument = DocumentsManager.getInstance().getLatestContractorDocument(contractor.getId());
		
		if(latestDocument != null){
			contractorLatestDocumentTextView.setText(latestDocument.getNumber());
			contractorLatestDocumentDateTextView.setVisibility(View.VISIBLE);
			contractorLatestDocumentDateTextView.setText(DateUtilities.contvertDateToString(context, latestDocument.getDocumentDate()));
		}
		else{
			contractorLatestDocumentTextView.setText(R.string.none);
			contractorLatestDocumentDateTextView.setVisibility(View.GONE);
		}
	}
	
	
	private void deleteContractor(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);

		builder.setMessage(R.string.do_you_want_to_delete_contractor)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						boolean isDeleted = ContractorsManager.getInstance().deleteContractor(contractor.getId());
						dialog.cancel();
						
						if(isDeleted){
							Toast.makeText(context, R.string.contractor_deleted, Toast.LENGTH_LONG).show();
							getActivity().setResult(ContractorsActivity.RESULT_OK);
							getActivity().finish();
						}
						else
							Toast.makeText(context, R.string.contractor_deleted_error, Toast.LENGTH_LONG).show();
						
					}
				})
				.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();					
					}
				});
		
		builder.create().show();
	}
}