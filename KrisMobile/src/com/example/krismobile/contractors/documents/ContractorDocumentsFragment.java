package com.example.krismobile.contractors.documents;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.contractors.Contractor;
import com.example.krismobile.contractors.adapters.ContractorDocumentsAdapter;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.documents.DocumentActivity;
import com.example.krismobile.documents.DocumentsActivity;
import com.example.krismobile.main.base.FragmentBase;

public class ContractorDocumentsFragment extends FragmentBase{
	
	private ListView documentsListView;
	private ContractorDocumentsAdapter adapter;
	private Contractor contractor;
	
	public ContractorDocumentsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_documents,
				container, false);
		
		contractor = ((ContractorDocumentsActivity) context).getContractor();
		
		documentsListView = (ListView)rootView.findViewById(R.id.documents_listView);
	
		DocumentsManager manager = DocumentsManager.getInstance();

		adapter = new ContractorDocumentsAdapter(context, manager.getContractorDocuments(contractor.getId()), contractor);
		
		documentsListView.setAdapter(adapter);
		
		documentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(context, DocumentActivity.class);
				//Intent intent = new Intent(context, TmpPagedActivity.class);
				
				Bundle args = new Bundle();
				args.putString("documentId", ((Document)adapter.getItem(position)).getId());
				intent.putExtras(args);
				
				startActivityForResult(intent, DocumentsActivity.REQUEST_REFRESH);
				
			}
			
		});
/*		
		documentsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ModifyDocumentDialog dialog = new ModifyDocumentDialog(DocumentsFragment.this, ((Document)adapter.getItem(position)).getId());
				dialog.show();
				return true;
			}
			
		});
*/	
		
		return rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if (id == R.id.action_add_document) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			final Bundle docTypBundle = new Bundle();
			
			
			builder.setTitle(R.string.document_type)					
			.setSingleChoiceItems(R.array.document_types, 0, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						docTypBundle.putInt("docTypeId", which);
					}
				})

				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						Intent intent = new Intent(context, DocumentActivity.class);
						//Intent intent = new Intent(context, TmpPagedActivity.class);
						
						Bundle args = new Bundle();
						args.putString("documentId", "");
						args.putParcelable("contractor", contractor);
						args.putInt("docTypeId", docTypBundle.getInt("docTypeId"));
						intent.putExtras(args);
						
						startActivityForResult(intent,DocumentsActivity.REQUEST_REFRESH);

					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int id) {

					}
				});

				builder.create().show();
			
/*			
			Intent intent = new Intent(context, DocumentActivity.class);
			//Intent intent = new Intent(context, TmpPagedActivity.class);
			
			Bundle args = new Bundle();
			args.putString("documentId", "");
			args.putParcelable("contractor", contractor);
			intent.putExtras(args);
			
			startActivityForResult(intent,DocumentsActivity.REQUEST_REFRESH);
*/
			return true;
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if(requestCode == DocumentsActivity.REQUEST_REFRESH){
			if(resultCode == DocumentsActivity.RESULT_OK){
				
				getActivity().setResult(DocumentsActivity.RESULT_OK);

				adapter.notifyDataSetChanged();
			}
		}

	}
	
	public Contractor getContractor(){
		return this.contractor;
	}
}
