package com.example.krismobile.items;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.ItemsManager;

public class ModifyItemActivity extends Activity {

	
	private Item item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Bundle args = getIntent().getExtras();
		String itemId = args.getString("itemId");
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		
		if(itemId.equals("")){
			getActionBar().setTitle(getResources().getString(R.string.action_add_item));
			item = new Item();
		
		} else{
			Document itemDoc = ItemsManager.getInstance().getItem(itemId);
			Map<?,?> props = itemDoc.getProperties();
			item = new Item(itemDoc);
			
			getActionBar().setTitle((String)props.get("Code"));
			
			
			
		}

		setContentView(R.layout.activity_modify_item);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, new ModifyItemFragment()).commit();
		}
			
	}	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.item_modify, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public Item getItem(){
		return this.item;
	}
}
