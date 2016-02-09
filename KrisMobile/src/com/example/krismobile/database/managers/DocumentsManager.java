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
import com.example.krismobile.documents.KrisDocument;
import com.example.krismobile.documents.positions.DocumentPosition;
import com.example.krismobile.documents.positions.DocumentPositionsList;

public class DocumentsManager {
	
	public static final int SALES_ORDER_TYPE = 1;
	public static final int PURCHASE_ORDER_TYPE = 2;
	public static final int SALES_INVOICE_TYPE = 3;
	public static final int PURCHASE_INVOICE_TYPE = 4;
	public static final int WAREHOUSE_RECEIVING_TYPE = 5;
	public static final int WAREHOUSE_DELIVERY_TYPE = 6;
	
	private static DocumentsManager manager;
	private static final String DOC_TYPE = "Document";
	private static final String DOC_POSITION_TYPE = "DocumentPosition";
	private static final String DOC_NUMERATOR_TYPE = "DocumentNumerator";
	
	public static DocumentsManager getInstance(){
		if(manager == null)
			manager = new DocumentsManager();
		
		return manager;
	}
	
	public String saveKrisDocument(KrisDocument krisDocument){
		
		Document document;
		String documentId;
		try {
			
			DatabaseManager.getDatabaseInstance().beginTransaction();
	    
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DocType", DOC_TYPE);
			map.put("Number", krisDocument.getNumber());
			map.put("TypeId", krisDocument.getTypeId());
			map.put("ContractorId", krisDocument.getContractor().getId());
			map.put("ContractorCode", krisDocument.getContractor().getCode());
			map.put("DocumentDate", krisDocument.getDocumentDate().getTime());
			map.put("PaymentDate", krisDocument.getPaymentDate().getTime());
			map.put("Description", krisDocument.getDescription());
			map.put("PaymentForm", krisDocument.getPaymentForm());
			map.put("NetValue", krisDocument.getNetValue());
			map.put("GrossValue", krisDocument.getGrossValue());
			map.put("ModificationTS", new Date().getTime());			
			
			if(krisDocument.getId().equals("")){
				document = DatabaseManager.getDatabaseInstance().createDocument();
				
			}else{
				document = DatabaseManager.getDatabaseInstance().getDocument(krisDocument.getId());	
				map.put("_rev", document.getProperty("_rev"));
				
			}
			
			documentId = document.getId();
			krisDocument.setId(document.getId());
	    
			document.putProperties(map);
			
			saveDocumentPositions(krisDocument);
	
			
			DatabaseManager.getDatabaseInstance().endTransaction(true);
			
			return documentId;
			
		} catch (CouchbaseLiteException e) {

			e.printStackTrace();
			Log.e("Database Error", "Error putting", e);
		}
		
		return null;
	}
	
	public void saveDocumentPositions(KrisDocument krisDocument){
		
		for(DocumentPosition position : krisDocument.getPositionsList()){
			
			Document document;
			String documentId;
			try {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("DocType", DOC_POSITION_TYPE);
				map.put("DocumentId", krisDocument.getId());
				map.put("ItemId", position.getItem().getId());
				map.put("Ordinal", position.getOrdinal());
				map.put("Quantity", position.getQuantity());
				map.put("NetValue", position.getNetValue());
				map.put("GrossValue", position.getGrossValue());	
				map.put("ModificationTS", new Date().getTime());		
				
				if(position.getId().equals("")){
					
						document = DatabaseManager.getDatabaseInstance().createDocument();
					
					
				}else{
					document = DatabaseManager.getDatabaseInstance().getDocument(position.getId());	
					map.put("_rev", document.getProperty("_rev"));
					
					position.setId(document.getId());
				}
				
				documentId = document.getId();

		    
				document.putProperties(map);
			} catch (CouchbaseLiteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
	}
	
	public Document getKrisDocument(String documentId){
		
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
	
	public ArrayList<Document> getAllKrisDocuments(){
		
		ArrayList<Document> documentsList = new ArrayList<Document>();
		View allKrisDocumentsView = getView("allDocuments");
		
		if(allKrisDocumentsView != null){
			
			allKrisDocumentsView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)){
						
		//				ArrayList<Object> keys = getAllKeysToEmit(document);
						
						emitter.emit(document.get("DocumentDate") , document);
					}
				}
				
			}, "3");
			
			documentsList = getDocumentsListFromView(allKrisDocumentsView, 0, null, true);	    
		}
		
		return documentsList;		
	}
	
	public ArrayList<Document> getContractorDocuments(final String contractorId){
		
		ArrayList<Document> documentsList = new ArrayList<Document>();
		View contratorKrisDocumentsView = getView("contractorDocuments");
		
		if(contratorKrisDocumentsView != null){
			
			contratorKrisDocumentsView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE) ){
	//						&& document.get("ContractorId").equals(contractorId)){
						
	//					ArrayList<Object> keys = getAllKeysToEmit(document);
						ArrayList<Object> keys = new ArrayList<Object>();
						keys.add(document.get("ContractorId"));
						keys.add(document.get("DocumentDate"));
						
						emitter.emit(document.get("ContractorId"), document);
						emitter.emit(document.get("DocumentDate"), document);
					}
				}
				
			}, "5");
			
			List<Object> keys = new ArrayList<Object>();
			keys.add(contractorId);
			
			documentsList = getDocumentsListFromView(contratorKrisDocumentsView, 0, keys, true);
		}
		
		return documentsList;		
	}
/*
	private ArrayList<Object> getAllKeysToEmit(Map<String, Object> document){
		ArrayList<Object> keys = new ArrayList<Object>();
		
		keys.add(document.get("DocumentDate"));
        keys.add(document.get("Number"));
        keys.add(document.get("TypeId"));
        keys.add(document.get("ContractorId"));
        keys.add(document.get("ContractorCode"));       
        keys.add(document.get("PaymentDate"));
        keys.add(document.get("PaymentForm"));
        keys.add(document.get("Description"));
        keys.add(document.get("Value"));
        
        return keys;
	}
*/	
	private ArrayList<Document> getDocumentsListFromView(View queryView, int limit, List<Object> keys, boolean descending){
		
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
	
	public boolean deleteKrisDocument(String documentId){
		Document documentToDelete = getKrisDocument(documentId);
		
		try {
			
			documentToDelete.delete();
			return true;
		} catch (CouchbaseLiteException e) {

			e.printStackTrace();
			return false;
		}
	}
	
	public KrisDocument getLatestContractorDocument(final String contractorId){
		
		ArrayList<Document> documentsList = new ArrayList<Document>();
		View latestContratorKrisDocumentView = getView("latestContractorDocument");
		
		if(latestContratorKrisDocumentView != null){
			
			latestContratorKrisDocumentView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)){
				//			&& document.get("ContractorId").equals(contractorId)){
						
						//ArrayList<Object> keys = getAllKeysToEmit(document);
						
						//ArrayList<Object> keys = new ArrayList<Object>();
						//keys.add(document.get("ContractorId"));
						//keys.add(document.get("DocumentDate"));
						
						emitter.emit(document.get("ContractorId") , document);
						emitter.emit(document.get("DocumentDate") , document);
						//emitter.emit(keys , document);
					}
				}
				
			}, "8");
			
			List<Object> keys = new ArrayList<Object>();
			keys.add(contractorId);
			
			documentsList = getDocumentsListFromView(latestContratorKrisDocumentView, 1, keys, true);
		}
		
		if(documentsList.isEmpty())
			return null;
		
		return new KrisDocument(documentsList.get(0));	
	}
	
	public DocumentPositionsList getDocumentPositions(String documentId){
		ArrayList<Document> positionsDocumentsList = new ArrayList<Document>();
		View latestContratorKrisDocumentView = getView("documentPositions");
		
		if(latestContratorKrisDocumentView != null){
			
			latestContratorKrisDocumentView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_POSITION_TYPE)){
				//			&& document.get("ContractorId").equals(contractorId)){
						
						//ArrayList<Object> keys = getAllKeysToEmit(document);
						
						//ArrayList<Object> keys = new ArrayList<Object>();
						//keys.add(document.get("ContractorId"));
						//keys.add(document.get("DocumentDate"));
						
						emitter.emit(document.get("DocumentId") , document.get("Ordinal"));
						//emitter.emit(keys , document);
					}
				}
				
			}, "2");
			
			List<Object> keys = new ArrayList<Object>();
			keys.add(documentId);
			
			positionsDocumentsList = getDocumentsListFromView(latestContratorKrisDocumentView, 0, keys, true);
		}
		
		DocumentPositionsList positionsList = new DocumentPositionsList();
		
		for(Document doc: positionsDocumentsList){
			positionsList.add(new DocumentPosition(doc));
		}
		
		return positionsList;	
	}

	public void deleteAllKrisDocuments(){
		ArrayList<Document> docs = getAllKrisDocuments();
		for(Document doc : docs){
			deleteKrisDocument(doc.getId());
			
			ArrayList<DocumentPosition> docsPos = this.getDocumentPositions(doc.getId());
			for(DocumentPosition docPos : docsPos){
				deleteKrisDocument(docPos.getId());
				
			}
		}
	}
}
