package com.example.krismobile.database.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.example.krismobile.contractors.Contractor;
import com.example.krismobile.database.DatabaseManager;

public class ContractorsManager {
	
	public String saveContractor(Contractor contractor){
		
		Document document;
		try {
			
			document = DatabaseManager.getDatabaseInstance().createDocument();
			String documentId = document.getId();
	    
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contractorId", contractor.getId());
			map.put("contractorCode", contractor.getCode());
			map.put("contractorTypeId", contractor.getTypeId());
			map.put("contractorNIP", contractor.getNIP());
			map.put("contractorAddress", contractor.getAddress());
			map.put("contractorDescription", contractor.getDescription());
	    
			document.putProperties(map);
			
			return documentId;
			
		} catch (CouchbaseLiteException e) {

			e.printStackTrace();
			Log.e("Database Error", "Error putting", e);
		}
		
		return null;
	}
	
	public Document getContractor(String documentId){
		
		Document document = null;
		try {
			
			document = DatabaseManager.getDatabaseInstance().getDocument(documentId);
			
		} catch (CouchbaseLiteException e) {
			
			e.printStackTrace();
		}
		
		return document;
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
	
	public ArrayList<Document> getAllContractors(){
		
		ArrayList<Document> contractorsList = new ArrayList<Document>();
		View allContractorsView = getView("allContractors");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(allContractorsView != null){
			
			allContractorsView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("contractorCode") != null){
						
						ArrayList<Object> keys = new ArrayList<Object>();
                        keys.add(document.get("contractorCode"));
                        keys.add(document.get("contactorId"));
                        keys.add(document.get("contractorTypeId"));
                        keys.add(document.get("contractorNIP"));
                        keys.add(document.get("contractorAddress"));
                        keys.add(document.get("contractorDescription"));
						
						emitter.emit(keys , document);
					}
			//		emitter.emit((Integer) document.get("contactorId") , null);
			//		emitter.emit((Integer) document.get("contractorTypeId") , null);
			//		emitter.emit((String) document.get("contractorNIP") , null);
			//		emitter.emit((String) document.get("contractorAddress") , null);
			//		emitter.emit((String) document.get("contractorDescription") , null);
				}
				
			}, "2");
			
			Query orderedQuery = allContractorsView.createQuery();

			try {
				
		        QueryEnumerator results = orderedQuery.run();
		       /* Iterate through the rows to get the document ids */
		       for (Iterator<QueryRow> it = results; it.hasNext();) {
		          QueryRow row = it.next();
		          Document doc = row.getDocument();
	//	           Event event = eventRepository.getById(docId);
		          
		          contractorsList.add(doc);
 
		          Log.i("KrisMobile", "Found party:" + row);
		       }
		       
		       
		    } catch (CouchbaseLiteException e) {
		    	
		        Log.e("KrisMobile", "Error querying view.", e);
		        
		    }	    
		}
		
		return contractorsList;
		
	}

}
