package com.example.krismobile.main;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.krismobile.R;
import com.example.krismobile.main.adapters.MenuAdapter;
import com.example.krismobile.main.entities.MenuItem;

public class MenuFragment extends Fragment {
	
	private Context context;
	private ListView menuListView;
	private MenuAdapter menuAdapter;

	public MenuFragment(Context context) {
		this.context = context;
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
		
		menuAdapter = new MenuAdapter(context, 0, itemList);
		
		menuListView.setAdapter(menuAdapter);
		
	}
}