package com.example.krismobile.items;

import android.app.Activity;
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
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.items.adapters.ItemsAdapter;
import com.example.krismobile.main.base.FragmentBase;

public class ItemsFragment extends FragmentBase {

	private ListView documentsListView;
	private ItemsAdapter adapter;

	public ItemsFragment() {
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
	
		ItemsManager manager = ItemsManager.getInstance();

		adapter = new ItemsAdapter(context, manager.getAllItems());
		
		documentsListView.setAdapter(adapter);
		
		documentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(context, ItemActivity.class);
				//Intent intent = new Intent(context, TmpPagedActivity.class);
				
				Bundle args = new Bundle();
				args.putString("itemId", ((Document)adapter.getItem(position)).getId());
				intent.putExtras(args);
				
				startActivityForResult(intent, ItemsActivity.REQUEST_REFRESH);
				
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
		
		if (id == R.id.action_add_item) {
			
			Intent intent = new Intent(context, ModifyItemActivity.class);
			
			Bundle args = new Bundle();
			args.putString("itemId", "");
			intent.putExtras(args);
			
			startActivityForResult(intent,ItemsActivity.REQUEST_REFRESH);
			return true;
			
		}
		else if(id == android.R.id.home){
	        NavUtils.navigateUpFromSameTask((Activity)context);
	        return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if(requestCode == ItemsActivity.REQUEST_REFRESH){
			if(resultCode == ItemsActivity.RESULT_OK){

				adapter.notifyDataSetChanged();
			}
		}

	}
	
}
