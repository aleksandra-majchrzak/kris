package com.example.krismobile.database.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.View;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.items.Item;

public class ItemsManager {
	
//	public static final int TROUSERS_TYPE = 1;
//	public static final int JACKET_TYPE = 2;
//	public static final int WEISTCOAT_TYPE = 3;
//	public static final int SUIT_TYPE = 4;
//	public static final int JACKET_TYPE = 5;
	
	private static ItemsManager manager;
	private static final String DOC_TYPE = "Item";
	private static final String SIZE_TYPE = "Size";
	private static final String MATERIAL_TYPE = "Material";
	private static final String PRICE_TYPE = "Price";
	private static final String STOCKS_TYPE = "ItemStocks";
	
	public static ItemsManager getInstance(){
		if(manager == null)
			manager = new ItemsManager();
		
		return manager;
	}

	
	public String saveItem(Item item){
		
		Document itemDocument;
		String itemDocumentId;
		
		Document priceDocument;
		String priceDocumentId;
		
		
		try {
			Map<String, Object> priceMap =  new HashMap<String, Object>();
			priceMap.put("DocType", PRICE_TYPE);
			priceMap.put("NetPrice", item.getPrice().getNetPrice());
			priceMap.put("GrossPrice", item.getPrice().getGrossPrice());
			
			if(item.getPrice().getId().equals("")){
				priceDocument = DatabaseManager.getDatabaseInstance().createDocument();
				
			}else{
				priceDocument = DatabaseManager.getDatabaseInstance().getDocument(item.getPrice().getId());	
				priceMap.put("_rev", priceDocument.getProperty("_rev"));
			}
			
			priceDocumentId = priceDocument.getId();
			priceDocument.putProperties(priceMap);
			
	    
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("DocType", DOC_TYPE);
//			map.put("Id", contractor.getId());
			itemMap.put("Code", item.getCode());
			itemMap.put("Name", item.getName());
			itemMap.put("Size", item.getSize());
			itemMap.put("Material", item.getMaterial());
			itemMap.put("PriceId", priceDocumentId);
			itemMap.put("Description", item.getDescription());
			itemMap.put("Type", item.getType());
			itemMap.put("ModificationTS", new Date().getTime());			
			
			if(item.getId().equals("")){
				itemDocument = DatabaseManager.getDatabaseInstance().createDocument();
				
			}else{
				itemDocument = DatabaseManager.getDatabaseInstance().getDocument(item.getId());	
				itemMap.put("_rev", itemDocument.getProperty("_rev"));
			}
			
			itemDocumentId = itemDocument.getId();

	    
			itemDocument.putProperties(itemMap);
	
			return itemDocumentId;
			
		} catch (CouchbaseLiteException e) {

			e.printStackTrace();
			Log.e("Database Error", "Error putting", e);
		}
		
		return null;
	}
	
	public Document getItem(String documentId){
		
		Document document = null;
		try {
			
			document = DatabaseManager.getDatabaseInstance().getDocument(documentId);
			
		} catch (CouchbaseLiteException e) {
			
			e.printStackTrace();
		}
		
		return document;
	}

	public Document getItemPrice(String documentId){

		Document priceDocment = null;
		try {
			priceDocment = DatabaseManager.getDatabaseInstance().getDocument(documentId);
			
		} catch (CouchbaseLiteException e) {
			
			e.printStackTrace();
		}
		
		return priceDocment;
	}
	
	private View getView(String name) {
	    View view = null;
	    try {
	    	
	    	view = DatabaseManager.getDatabaseInstance().getView(name);
	    	
	    }catch (CouchbaseLiteException e) {
	    	
	        e.printStackTrace();
	    }
	    return view;
	}
	
	public ArrayList<Document> getItemStocks(String itemId){
		
		ArrayList<Document> itemsList = new ArrayList<Document>();
		View itemStocksView = getView("itemStocks");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(itemStocksView != null){
			
			itemStocksView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(STOCKS_TYPE)){

						emitter.emit(document.get("itemId") , document);
					}
				}
				
			}, "1");
			
			List<Object> keys = new ArrayList<Object>();
			keys.add(itemId);
			
			itemsList = geItemsListFromView(itemStocksView, 0, keys, false);
		}
		
		return itemsList;		
	}
	
	public ArrayList<Document> getWarehouseStocks(String warehouseId){
		
		ArrayList<Document> itemsList = new ArrayList<Document>();
		View warehouseStocksView = getView("warehouseStocks");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(warehouseStocksView != null){
			
			warehouseStocksView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(STOCKS_TYPE)){

						emitter.emit(document.get("warehouseId") , document);
					}
				}
				
			}, "1");
			
			List<Object> keys = new ArrayList<Object>();
			keys.add(warehouseId);
			
			itemsList = geItemsListFromView(warehouseStocksView, 0, keys, false);
		}
		
		return itemsList;		
	}
	
	public ArrayList<Document> getAllItems(){
		
		ArrayList<Document> itemsList = new ArrayList<Document>();
		View allItemsView = getView("allItems");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(allItemsView != null){
			
			allItemsView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)){
						
						//ArrayList<Object> keys = new ArrayList<Object>();
                        //keys.add(document.get("Code"));
                        //keys.add(document.get("Name"));
                        //keys.add(document.get("SizeId"));
                        //keys.add(document.get("MaterialId"));
                        //keys.add(document.get("PriceId"));
                       // keys.add(document.get("Description"));
						//keys.add(document.get("TypeId"));
						
						emitter.emit(document.get("Name") , document);
					}
				}
				
			}, "3");
			
			itemsList = geItemsListFromView(allItemsView, 0, null, false);
		}
		
		return itemsList;		
	}
	
	private ArrayList<Document> geItemsListFromView(View queryView, int limit, List<Object> keys, boolean descending){
		
		ArrayList<Document> documentsList = new ArrayList<Document>();
		Query orderedQuery = queryView.createQuery();
		
		if(limit > 0)
			orderedQuery.setLimit(limit);
		
		if(keys != null && ! keys.isEmpty())
			orderedQuery.setKeys(keys);
		
		orderedQuery.setDescending(descending);

		try {
			
	        QueryEnumerator results = orderedQuery.run();

	       for (Iterator<QueryRow> it = results; it.hasNext();) {
	          QueryRow row = it.next();
	          Document doc = row.getDocument();
	          
	          documentsList.add(doc);

	          Log.i("KrisMobile", "Found party:" + row);
	       }
	       
	       
	    } catch (CouchbaseLiteException e) {
	    	
	        Log.e("KrisMobile", "Error querying view.", e);
	        
	    }	  
		
		return documentsList;
		
	}
	
	public String[] getItemTypes(){
		String[] itemTypes = null;
		View itemTypesView = getView("allItemTypes");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(itemTypesView != null){
			
			itemTypesView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)){
						
						emitter.emit(document.get("Type") , document.get("Type"));
					}
				}
				
			}, "1");
			
			itemTypes = geValuesArrayFromView(itemTypesView, 0, null, false);
		}
		
		return itemTypes;		
	}
	
	
	public String[] getItemSizes(){
		
		String[] itemSizes = null;
		View itemSizesView = getView("allItemSizes");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(itemSizesView != null){
			
			itemSizesView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)){
						
						emitter.emit(document.get("Size") , document.get("Size"));
					}
				}
				
			}, "1");
			
			itemSizes = geValuesArrayFromView(itemSizesView, 0, null, false);
		}
		
		return itemSizes;		
	}
	
	public String[] getItemMaterials(){
		
		String[] itemMaterials = null;
		View itemMaterialsView = getView("allItemMaterials");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(itemMaterialsView != null){
			
			itemMaterialsView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)){
						
						emitter.emit(document.get("Material") , document.get("Material"));
					}
				}
				
			}, "1");
			
			itemMaterials = geValuesArrayFromView(itemMaterialsView, 0, null, false);
		}
		
		return itemMaterials;		
	}
	
	
	private String[] geValuesArrayFromView(View queryView, int limit, List<Object> keys, boolean descending){
		
		ArrayList<String> valuesArray = new ArrayList<String>();
		Query orderedQuery = queryView.createQuery();
		
		if(limit > 0)
			orderedQuery.setLimit(limit);
		
		if(keys != null && ! keys.isEmpty())
			orderedQuery.setKeys(keys);
		
		orderedQuery.setDescending(descending);

		try {
			
	        QueryEnumerator results = orderedQuery.run();

	       for (Iterator<QueryRow> it = results; it.hasNext();) {
	          QueryRow row = it.next();
	          String doc = (String)row.getValue();
	          
	          valuesArray.add(doc);

	          Log.i("KrisMobile", "Found party:" + row);
	       }
	       
	       
	    } catch (CouchbaseLiteException e) {
	    	
	        Log.e("KrisMobile", "Error querying view.", e);
	        
	    }	  
		
		return valuesArray.toArray(new String[valuesArray.size()]);
		
	}

	public boolean deleteItem(String itemId){
		Document itemToDelete = getItem(itemId);
		
		try {
			
			itemToDelete.delete();
			return true;
		} catch (CouchbaseLiteException e) {

			e.printStackTrace();
			return false;
		}
	}

}
