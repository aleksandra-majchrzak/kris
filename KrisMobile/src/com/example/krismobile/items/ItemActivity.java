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
			
		getActionBar().setTitle("["+(String)props.get("Code")+"] "+(String)props.get("Name"));
		
		setContentView(R.layout.activity_contractor);
		
		if(getFragmentManager().findFragmentById(R.id.container) == null)
			getFragmentManager().beginTransaction()
				.add(R.id.container, new ItemFragment()).commit();
			
	}	
	
	public Item reloadItem(){
		Document itemDoc = ItemsManager.getInstance().getItem(item.getId());
		Map<?,?> props = itemDoc.getProperties();
		
		Document priceDoc = ItemsManager.getInstance().getItemPrice((String) itemDoc.getProperty("PriceId"));
		Price price = new Price(priceDoc);
		
		
		List<Document> itemStocksDocs = ItemsManager.getInstance().getItemStocks(itemDoc.getId());
		ArrayList<ItemStocks> itemStocks = new ArrayList<ItemStocks>();
		
		for(Document stockDoc : itemStocksDocs){
			itemStocks.add(new ItemStocks(stockDoc));
		}
		
		item.setCode((String)props.get("Code")); 
		item.setName((String)props.get("Name")); 
		item.setSize((String)props.get("Size"));
		item.setMaterial((String)props.get("Material"));
		item.setPrice(price);
		item.setDescription((String)props.get("Description"));
		item.setType((String)props.get("Type"));
		item.setItemStocks(itemStocks);
		
		getActionBar().setTitle("["+(String)props.get("Code")+"] "+(String)props.get("Name"));
		
		return item;
	}
	
	

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
