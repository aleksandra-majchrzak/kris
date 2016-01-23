package com.example.krismobile.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.items.entities.Price;
import com.example.krismobile.warehouses.stocks.ItemStocks;

public class ItemActivity extends Activity {
	
	private Item item;
	private boolean isNewDocument = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Bundle args = getIntent().getExtras();
		String itemId = args.getString("itemId");
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Document itemDoc = ItemsManager.getInstance().getItem(itemId);
		Map<?,?> props = itemDoc.getProperties();
		
		Document priceDoc = ItemsManager.getInstance().getItemPrice((String)itemDoc.getProperty("PriceId"));
		Price price = new Price(priceDoc);
		
		
		List<Document> itemStocksDocs = ItemsManager.getInstance().getItemStocks(itemId);
		ArrayList<ItemStocks> itemStocks = new ArrayList<ItemStocks>() ;
		
		for(Document stockDoc : itemStocksDocs){
			itemStocks.add(new ItemStocks(stockDoc));
		}
		
		item = new Item(itemDoc.getId(), 
								(String)props.get("Code"), 
								(String)props.get("Name"), 
								(String)props.get("Size"),
								(String)props.get("Material"),
								price,
								(String)props.get("Description"),
								(String)props.get("Type"),
								itemStocks);
			
		getActionBar().setTitle((String)props.get("Code"));
		
		setContentView(R.layout.activity_contractor);
		
		if(getFragmentManager().findFragmentById(R.id.container) == null)
			getFragmentManager().beginTransaction()
				.add(R.id.container, new ItemFragment()).commit();
			
	}	
/*	
	public Contractor reloadDocument(){
		Document documentDoc = ContractorsManager.getInstance().getContractor(contractor.getId());
		Map<?,?> props = contractorDoc.getProperties();
		
		contractor.setCode((String)props.get("Code"));
		contractor.setTypeId((Integer)props.get("TypeId"));
		contractor.setAddress((String)props.get("Address"));
		contractor.setDescription((String)props.get("Description"));
		contractor.setNIP((String)props.get("NIP"));
		
		getActionBar().setTitle((String)props.get("Code"));
		
		return contractor;
	}
*/	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.item, menu);
		
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
