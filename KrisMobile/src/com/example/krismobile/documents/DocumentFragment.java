package com.example.krismobile.documents;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
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
import com.example.krismobile.documents.positions.DocumentPosition;
import com.example.krismobile.documents.positions.DocumentPositionDialog;
import com.example.krismobile.documents.positions.DocumentPositionObserver;
import com.example.krismobile.items.Item;
import com.example.krismobile.main.base.FragmentBase;

public class DocumentFragment extends FragmentBase implements Observer{
	
	private static final int POSITION_ITEMS = 0;
	private static final int POSITION_POSITIONS  = 1;
	private static final int POSITION_HEADER  = 2;
	
	private KrisDocument document;
	
	private int section;
	
	private ListView documentItemsListView;
	private static DocumentItemsAdapter itemsAdapter;
	
	private ListView documentPositionsListView;
	private static DocumentPositionsAdapter positionsAdapter;
	
	private TextView documentNumberTextView;
//	private TextView documentTypeTextView;
	private TextView documentContractorTextView;
	private TextView documentDocumentDateTextView;
	private TextView documentPaymentDateTextView;
	private EditText documentDescriptionEditText;
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
		section = args.getInt(ARG_SECTION_NUMBER);
		
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
		
		
		this.document = ((DocumentActivity) context).getDocument();	
		
		if( ! this.document.getPositionsList().hasObserverOfType(this))
			this.document.getPositionsList().addObserver(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if (id == R.id.action_edit_document) {
			
			((DocumentActivity)getActivity()).setIsBeingEdited(true);
			getActivity().invalidateOptionsMenu();
			
			documentDescriptionEditText.setEnabled(((DocumentActivity)getActivity()).getIsBeingEdited());
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
			
			document.setDescription(documentDescriptionEditText.getText().toString());
			
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

		if(itemsAdapter == null)
			itemsAdapter = new DocumentItemsAdapter(context, manager.getAllItems(), document.getPositionsList());
		
		documentItemsListView.setAdapter(itemsAdapter);
		
		documentItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Item item = new Item((Document) itemsAdapter.getItem(position));
				DocumentPosition newPosition = document.getPositionsList().getByItemId(item.getId());
				boolean isPositionNew = false;
				
				if(newPosition == null){
				
					newPosition = new DocumentPosition();
				
					newPosition.setDocumentId(document.getId());
					newPosition.setQuantity(1);
					newPosition.setItem(item);
					newPosition.setNetValue(item.getPrice().getNetPrice());
					newPosition.setGrossValue(item.getPrice().getGrossPrice());
					newPosition.setOrdinal(document.getPositionsList().size() +1 );
					newPosition.addObserver(new DocumentPositionObserver());
					
					isPositionNew = true;
				}
				
				// wyœwietlanie dialogu z info o pozycji
				 DialogFragment newFragment = DocumentPositionDialog
			                .newInstance(newPosition, true, isPositionNew);
			        newFragment.show(getFragmentManager(), "dialog");
				
			}
			
		});
		
	}
	
	private void loadDocumentPositions(View rootView){
		
		documentPositionsListView = (ListView) rootView.findViewById(R.id.document_positions_listView);
		
		if(positionsAdapter == null)
			positionsAdapter = new DocumentPositionsAdapter(context, document.getPositionsList());
		
		documentPositionsListView.setAdapter(positionsAdapter);
		
		documentPositionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				boolean canEdit = ((DocumentActivity)context).getIsBeingEdited();
					
				

				
				 DialogFragment newFragment = DocumentPositionDialog
			                .newInstance(document.getPositionsList().get(position).copy(), canEdit, false);
			        newFragment.show(getFragmentManager(), "dialog");
				
			}
			
		});
		
		documentPositionsListView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
			
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				
				builder.setTitle(R.string.do_you_want_to_delete_position)
				.setPositiveButton(R.string.yes, new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// DocumentPosition positionToDelete = (DocumentPosition) positionsAdapter.getItem(which);
						
						document.getPositionsList().remove(position);
						positionsAdapter.notifyDataSetChanged();
					}
					
				})
				.setNegativeButton(R.string.no, new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {}
					
				})
				.create().show();
				
				return true;
			}
			
		});
	}
		
	private void loadHeaderControls(View rootView){

		documentNumberTextView = (TextView) rootView.findViewById(R.id.document_number_textView);
//		documentTypeTextView = (TextView) rootView.findViewById(R.id.document_d);
		documentContractorTextView = (TextView) rootView.findViewById(R.id.document_contractor_textView);
		documentDocumentDateTextView = (TextView) rootView.findViewById(R.id.document_document_date_textView);
		documentPaymentDateTextView = (TextView) rootView.findViewById(R.id.document_payment_date_textView);
		documentDescriptionEditText = (EditText) rootView.findViewById(R.id.document_description_editText);
		documentPaymentFormTextView = (TextView) rootView.findViewById(R.id.document_payment_form_textView);
		documentNetValueTextView = (TextView) rootView.findViewById(R.id.document_netValue_textView);
		documentGrossValueTextView = (TextView) rootView.findViewById(R.id.document_grossValue_textView);

		documentDescriptionEditText.setEnabled(((DocumentActivity)getActivity()).getIsBeingEdited());
		
		if(! documentDescriptionEditText.isEnabled())
			documentDescriptionEditText.setTextColor(context.getResources().getColor(android.R.color.secondary_text_dark));
		
		fillControls();	
	}
	
	private void fillControls(){
		java.text.DateFormat df = DateFormat.getDateFormat(context.getApplicationContext());
		
		documentNumberTextView.setText(document.getNumber());
		documentContractorTextView.setText(document.getContractor().getCode());
		documentDocumentDateTextView.setText(df.format(document.getDocumentDate()));
		documentPaymentDateTextView.setText(df.format(document.getPaymentDate()));
		documentDescriptionEditText.setText(document.getDescription());
		documentPaymentFormTextView.setText(context.getResources().getStringArray(R.array.payment_forms)[document.getPaymentForm()]);
		documentNetValueTextView.setText(String.valueOf(document.getNetValue())+context.getResources().getString(R.string.PLN));
		documentGrossValueTextView.setText(String.valueOf(document.getGrossValue())+context.getResources().getString(R.string.PLN));

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
	
	@Override
	public void onDetach(){
		itemsAdapter = null;
		positionsAdapter = null;
		
		super.onDetach();
	}

	@Override
	public void update(Observable observable, Object data) {

		
		itemsAdapter.notifyDataSetChanged();
		positionsAdapter.notifyDataSetChanged();
		
		document.setNetValue(document.getPositionsList().getDocumentValueNet());
		document.setGrossValue(document.getPositionsList().getDocumentValueGross());

	}
}