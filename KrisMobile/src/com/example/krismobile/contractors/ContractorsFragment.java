package com.example.krismobile.contractors;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.contractors.adapters.ContractorsAdapter;
import com.example.krismobile.database.managers.ContractorsManager;
import com.example.krismobile.main.base.FragmentBase;

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

public class ContractorsFragment extends FragmentBase {
	
	private ListView contractorsListView;
	private ContractorsAdapter adapter;

	public ContractorsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_contractors,
				container, false);
		
		contractorsListView = (ListView)rootView.findViewById(R.id.contractors_listView);
	
		ContractorsManager manager = ContractorsManager.getInstance();

		adapter = new ContractorsAdapter(context, manager.getAllContractors());
		
		contractorsListView.setAdapter(adapter);
		
		contractorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(context, ContractorActivity.class);
				
				Bundle args = new Bundle();
				args.putString("contractorId", ((Document)adapter.getItem(position)).getId());
				intent.putExtras(args);
				
				startActivityForResult(intent, ContractorsActivity.REQUEST_DELETE_CONTRACTOR);
				
			}
			
		});
		
		contractorsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ModifyContractorDialog dialog = new ModifyContractorDialog(ContractorsFragment.this, ((Document)adapter.getItem(position)).getId());
				dialog.show();
				return true;
			}
			
		});
		
		return rootView;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		if (id == R.id.action_add_contractor) {
			
			Intent intent = new Intent(context, ModifyContractorActivity.class);
			
			Bundle args = new Bundle();
			args.putString("contractorId", "");
			intent.putExtras(args);
			
			startActivityForResult(intent,ContractorsActivity.REQUEST_ADD_NEW_CONTRACTOR);
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
		
		if(requestCode == ContractorsActivity.REQUEST_ADD_NEW_CONTRACTOR){
			if(resultCode == ContractorsActivity.RESULT_OK){

				adapter.notifyDataSetChanged();
			}
		}
		else if(requestCode == ContractorsActivity.REQUEST_DELETE_CONTRACTOR){
			if(resultCode == ContractorsActivity.RESULT_OK){

				adapter.notifyDataSetChanged();
			}
		}

	}
	
	public void refresh(){
		adapter.notifyDataSetChanged();
	}
	
}