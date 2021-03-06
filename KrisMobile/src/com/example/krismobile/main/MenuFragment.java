package com.example.krismobile.main;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.replicator.Replication;
import com.example.krismobile.R;
import com.example.krismobile.contractors.ContractorsActivity;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.documents.DocumentsActivity;
import com.example.krismobile.items.ItemsActivity;
import com.example.krismobile.main.adapters.MenuAdapter;
import com.example.krismobile.main.base.FragmentBase;
import com.example.krismobile.main.entities.MenuItem;
import com.example.krismobile.payments.PaymentsActivity;
import com.example.krismobile.synchronization.SynchronizationManager;

public class MenuFragment extends FragmentBase {
	
	private final int POSITION_CONTRACTORS = 0;
	private final int POSITION_DOCUMENTS = 1;
	private final int POSITION_PAYMENTS = 2;
	private final int POSITION_ITEMS = 3;
	private final int POSITION_WAREHOUSES = 4;
	private final int POSITION_SYNCHRONIZATION = 5;
	
	private ListView menuListView;
	private MenuAdapter menuAdapter;

	public MenuFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_menu, container,
				false);
		
		rootView.setBackgroundResource(R.drawable.blank_background2);
		
		loadContent(rootView);
		
		return rootView;
	}
	
	private void loadContent(View view){
		menuListView = (ListView) view.findViewById(R.id.menu_list_view);
		
		// tu musze tworzyc te liste itemow do menu
		ArrayList<MenuItem> itemList= new ArrayList<MenuItem>();
		
		itemList.add(new MenuItem(context.getResources().getString(R.string.contractors), R.drawable.contractors));
		itemList.add(new MenuItem(context.getResources().getString(R.string.documents), R.drawable.documents));
		itemList.add(new MenuItem(context.getResources().getString(R.string.payments), R.drawable.payments));
		itemList.add(new MenuItem(context.getResources().getString(R.string.items), R.drawable.items));
		itemList.add(new MenuItem(context.getResources().getString(R.string.warehouses), R.drawable.warehouses));
		itemList.add(new MenuItem(context.getResources().getString(R.string.synchronization), R.drawable.synchronization));
		
		menuAdapter = new MenuAdapter(context, 0, itemList);
		
		menuListView.setAdapter(menuAdapter);
		menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = null;
				
				switch(position){
				case POSITION_CONTRACTORS:
					
					intent = new Intent(context, ContractorsActivity.class);
					startActivity(intent);
					break;
					
				case POSITION_DOCUMENTS:
					
					intent = new Intent(context, DocumentsActivity.class);
					startActivity(intent);
					break;
					
				case POSITION_PAYMENTS:
					intent = new Intent(context, PaymentsActivity.class);
					startActivity(intent);
					break;
					
				case POSITION_ITEMS:
					intent = new Intent(context, ItemsActivity.class);
					startActivity(intent);
					break;
					
				case POSITION_WAREHOUSES:
					break;
					
				case POSITION_SYNCHRONIZATION:
					try {
						
						SynchronizationManager.getManagerInstance(context).setupConnection();
						
					} catch (CouchbaseLiteException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					break;
				}			
			}
					
		});
		
	}
}