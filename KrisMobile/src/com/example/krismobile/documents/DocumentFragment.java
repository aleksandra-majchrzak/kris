package com.example.krismobile.documents;

import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.documents.adapters.DocumentItemsAdapter;
import com.example.krismobile.documents.adapters.DocumentPositionsAdapter;
import com.example.krismobile.documents.adapters.DocumentsAdapter;
import com.example.krismobile.main.base.FragmentBase;

public class DocumentFragment extends FragmentBase {
	
	private static final int POSITION_ITEMS = 0;
	private static final int POSITION_POSITIONS  = 1;
	private static final int POSITION_HEADER  = 2;
	
	private KrisDocument document;
	
	private ListView documentItemsListView;
	private DocumentItemsAdapter itemsAdapter;
	
	private ListView documentPositionsListView;
	private DocumentPositionsAdapter positionsAdapter;
	
	private TextView documentNumberTextView;
//	private TextView documentTypeTextView;
	private TextView documentContractorTextView;
	private TextView documentDocumentDateTextView;
	private TextView documentPaymentDateTextView;
	private TextView documentDescriptionTextView;
	private TextView documentPaymentFormTextView;
	private TextView documentNetValueTextView;
	private TextView documentGrossValueTextView;
	
	private static final String ARG_SECTION_NUMBER = "section_number";

	public DocumentFragment() {
	}
	
	public static DocumentFragment newInstance(int sectionNumber){
		
		DocumentFragment fragment = new DocumentFragment();
		
		Bundle args = new Bundle();
		
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		
		fragment.setArguments(args);
		
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle args = getArguments();
		int section = args.getInt(ARG_SECTION_NUMBER);
		
		//czy to nie powinno byc gdziej indziej?
		this.document = ((DocumentActivity) context).getDocument();		
		
		View rootView = null;
		
		switch(section){
		case POSITION_ITEMS:
			rootView = inflater.inflate(R.layout.fragment_document_items,
					container, false);
			
			loadDocumentItems(rootView);
			
			break;
		case POSITION_POSITIONS:
			rootView = inflater.inflate(R.layout.fragment_document_positions,
					container, false);
			
			loadDocumentPositions(rootView);
			
			break;
		case POSITION_HEADER:
			rootView = inflater.inflate(R.layout.fragment_document_header,
					container, false);
			
			loadHeaderControls(rootView);
			
			break;
		}
		
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if (id == R.id.action_edit_document) {
		/*	
			
			Intent intent = new Intent(context, ModifyContractorActivity.class);
			
			Bundle args = new Bundle();
			args.putString("contractorId", contractor.getId());
			intent.putExtras(args);
			
			startActivityForResult(intent,ContractorsActivity.REQUEST_ADD_NEW_CONTRACTOR);
			
			*/
			return true;
		}
		else if(id == R.id.action_delete_document){
			
			deleteDocument();
			return true;
		}
		else if(id == R.id.action_save_document){
			
			DocumentsManager.getInstance().saveKrisDocument(document);
			getActivity().setResult(DocumentsActivity.RESULT_OK);
			getActivity().finish();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	/*
		if(requestCode == ContractorsActivity.REQUEST_ADD_NEW_CONTRACTOR){
			if(resultCode == ContractorsActivity.RESULT_OK){

				contractor = ((ContractorActivity)context).reloadContractor();
				fillControls();	
			}
		}
*/
	}
	
	private void loadDocumentItems(View rootView){
		
		documentItemsListView = (ListView) rootView.findViewById(R.id.document_items_listView);
		
		ItemsManager manager = ItemsManager.getInstance();

		itemsAdapter = new DocumentItemsAdapter(context, manager.getAllItems());
		
		documentItemsListView.setAdapter(itemsAdapter);
		
		documentItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// wyświetlanie dialogu z info o pozycji
				
			}
			
		});
	}
	
	private void loadDocumentPositions(View rootView){
		
		documentPositionsListView = (ListView) rootView.findViewById(R.id.document_positions_listView);
		
		positionsAdapter = new DocumentPositionsAdapter(context, document.getPositionsList());
		
		documentPositionsListView.setAdapter(positionsAdapter);
		
		documentPositionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// wyświetlanie dialogu z info o pozycji
				
			}
			
		});
	}
		
	private void loadHeaderControls(View rootView){

		documentNumberTextView = (TextView) rootView.findViewById(R.id.document_number_textView);
//		documentTypeTextView = (TextView) rootView.findViewById(R.id.document_d);
		documentContractorTextView = (TextView) rootView.findViewById(R.id.document_contractor_textView);
		documentDocumentDateTextView = (TextView) rootView.findViewById(R.id.document_document_date_textView);
		documentPaymentDateTextView = (TextView) rootView.findViewById(R.id.document_payment_date_textView);
		documentDescriptionTextView = (TextView) rootView.findViewById(R.id.document_description_textView);
		documentPaymentFormTextView = (TextView) rootView.findViewById(R.id.document_payment_form_textView);
		documentNetValueTextView = (TextView) rootView.findViewById(R.id.document_netValue_textView);
		documentGrossValueTextView = (TextView) rootView.findViewById(R.id.document_grossValue_textView);
		
		fillControls();	
	}
	
	private void fillControls(){
		java.text.DateFormat df = DateFormat.getDateFormat(context.getApplicationContext());
		
		if(!document.getId().equals("")){
			documentNumberTextView.setText(document.getNumber());
			documentContractorTextView.setText(document.getContractor().getCode());
			documentDocumentDateTextView.setText(df.format(document.getDocumentDate()));
			documentPaymentDateTextView.setText(df.format(document.getPaymentDate()));
			documentDescriptionTextView.setText(document.getDescription());
			documentPaymentFormTextView.setText(context.getResources().getStringArray(R.array.payment_forms)[document.getPaymentForm()]);
			documentNetValueTextView.setText(String.valueOf(document.getNetValue())+context.getResources().getString(R.string.PLN));
			documentGrossValueTextView.setText(String.valueOf(document.getGrossValue())+context.getResources().getString(R.string.PLN));
		}
		else{
			// numer musisz jakis miec wygenerowany wczesniej - wlasciwie ten else nie jest ci potrzebny, bo pola w dokumencie sa domyslnie ustawione
			documentNumberTextView.setText(document.getNumber());
			documentContractorTextView.setText(document.getContractor().getCode());
			documentDocumentDateTextView.setText(df.format(new Date()));
			documentPaymentDateTextView.setText(df.format(new Date()));
			documentDescriptionTextView.setText("");
			documentPaymentFormTextView.setText(context.getResources().getStringArray(R.array.payment_forms)[0]);
			documentNetValueTextView.setText("0"+context.getResources().getString(R.string.PLN));
			documentGrossValueTextView.setText("0"+context.getResources().getString(R.string.PLN));
		}
	}
	
	
	private void deleteDocument(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);

		builder.setMessage(R.string.do_you_want_to_delete_document)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						boolean isDeleted = DocumentsManager.getInstance().deleteKrisDocument(document.getId());
						dialog.cancel();
						
						if(isDeleted){
							Toast.makeText(context, R.string.document_deleted, Toast.LENGTH_LONG).show();
							getActivity().setResult(DocumentsActivity.RESULT_OK);
							getActivity().finish();
						}
						else
							Toast.makeText(context, R.string.document_deleted_error, Toast.LENGTH_LONG).show();
						
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