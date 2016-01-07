package com.example.krismobile.database.managers;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
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

}
