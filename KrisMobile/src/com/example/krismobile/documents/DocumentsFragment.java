package com.example.krismobile.documents;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.contractors.ContractorsActivity;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.documents.adapters.DocumentsAdapter;
import com.example.krismobile.main.base.FragmentBase;

public class DocumentsFragment extends FragmentBase {

	private ListView documentsListView;
	private DocumentsAdapter adapter;

	public DocumentsFragment() {
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
		
		documentsListView = (ListView)rootView.findViewById(R.id.documents_listView);
	
		DocumentsManager manager = DocumentsManager.getInstance();

		adapter = new DocumentsAdapter(context, manager.getAllKrisDocuments());
		
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
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if(requestCode == DocumentsActivity.REQUEST_REFRESH){
			if(resultCode == DocumentsActivity.RESULT_OK){

				adapter.notifyDataSetChanged();
			}
		}

	}
	
}
