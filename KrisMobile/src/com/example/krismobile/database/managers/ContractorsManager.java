package com.example.krismobile.database.managers;

import java.util.ArrayList;
import java.util.Date;
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
import com.example.krismobile.documents.positions.DocumentPosition;

public class ContractorsManager {
	
	public static final int SELLER_TYPE = 1;
	public static final int BUYER_TYPE = 2;
	
	private static ContractorsManager manager;
	private static final String DOC_TYPE = "Contractor";
	
	public static ContractorsManager getInstance(){
		if(manager == null)
			manager = new ContractorsManager();
		
		return manager;
	}
	
	public String saveContractor(Contractor contractor){
		
		Document document;
		String documentId;
		try {
	    
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DocType", DOC_TYPE);
//			map.put("Id", contractor.getId());
			map.put("Code", contractor.getCode());
			map.put("TypeId", contractor.getTypeId());
			map.put("NIP", contractor.getNIP());
			map.put("Address", contractor.getAddress());
			map.put("Description", contractor.getDescription());
			map.put("ModificationTS", new Date().getTime());			
			
			if(contractor.getId().equals("")){
				document = DatabaseManager.getDatabaseInstance().createDocument();
				
			}else{
				document = DatabaseManager.getDatabaseInstance().getDocument(contractor.getId());	
				map.put("_rev", document.getProperty("_rev"));
			}
			
			documentId = document.getId();

	    
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
					
					if(document.get("DocType").equals(DOC_TYPE)){
						
						ArrayList<Object> keys = new ArrayList<Object>();
                        keys.add(document.get("Code"));
//                        keys.add(document.get("Id"));
                        keys.add(document.get("TypeId"));
                        keys.add(document.get("NIP"));
                        keys.add(document.get("Address"));
                        keys.add(document.get("Description"));
						
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
	
	public boolean deleteContractor(String contractorId){
		Document contractorToDelete = getContractor(contractorId);
		
		try {
			
			contractorToDelete.delete();
			return true;
		} catch (CouchbaseLiteException e) {

			e.printStackTrace();
			return false;
		}
	}
}
