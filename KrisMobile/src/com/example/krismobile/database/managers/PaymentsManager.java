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
import com.example.krismobile.payments.entities.Payment;

public class PaymentsManager {
	
	private static PaymentsManager manager;
	private static final String DOC_TYPE = "Payment";
	
	public static PaymentsManager getInstance(){
		if(manager == null)
			manager = new PaymentsManager();
		
		return manager;
	}

	public String savePayment(Payment payment){
		
		Document document;
		String documentId;
		try {
	    
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DocType", DOC_TYPE);
			map.put("Description", payment.getDescription());
			map.put("PaymentName", payment.getPaymentName());
			map.put("Value", payment.getValue());
			map.put("PaymentDate", payment.getPaymentDate().getTime());
			map.put("IsPaid", payment.isPaid());		
			
			if(payment.getId().equals("")){
				document = DatabaseManager.getDatabaseInstance().createDocument();
				
			}else{
				document = DatabaseManager.getDatabaseInstance().getDocument(payment.getId());	
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
	
	public Document getPayment(String documentId){
		
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
	
	public ArrayList<Document> getDuePayments(){
		
		ArrayList<Document> paymentsList = new ArrayList<Document>();
		View duePaymentsView = getView("duePayments");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(duePaymentsView != null){
			
			duePaymentsView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)
							&& document.get("IsPaid").equals(false)){
						
						ArrayList<Object> keys = new ArrayList<Object>();
                        keys.add(document.get("Code"));
                        keys.add(document.get("Description"));
                        keys.add(document.get("PaymentName"));
                        keys.add(document.get("Value"));
                        keys.add(document.get("PaymentDate"));
                        keys.add(document.get("IsPaid"));
						
						emitter.emit(keys , document);
					}
				}
				
			}, "4");
			
			Query orderedQuery = duePaymentsView.createQuery();

			try {
				
		        QueryEnumerator results = orderedQuery.run();
		       /* Iterate through the rows to get the document ids */
		       for (Iterator<QueryRow> it = results; it.hasNext();) {
		          QueryRow row = it.next();
		          Document doc = row.getDocument();
	//	           Event event = eventRepository.getById(docId);
		          
		          paymentsList.add(doc);
 
		          Log.i("KrisMobile", "Found party:" + row);
		       }
		       
		       
		    } catch (CouchbaseLiteException e) {
		    	
		        Log.e("KrisMobile", "Error querying view.", e);
		        
		    }	    
		}
		
		return paymentsList;		
	}
	
	public ArrayList<Document> getFinalizedPayments(){
		
		ArrayList<Document> paymentsList = new ArrayList<Document>();
		View finalizedPaymentsView = getView("finalizedPayments");
		
//		Log.i("KrisMobile", DatabaseManager.getDatabaseInstance())
		
		if(finalizedPaymentsView != null){
			
			finalizedPaymentsView.setMap(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)
							&& document.get("IsPaid").equals(true)){
						
						ArrayList<Object> keys = new ArrayList<Object>();
                        keys.add(document.get("Code"));
                        keys.add(document.get("Description"));
                        keys.add(document.get("PaymentName"));
                        keys.add(document.get("Value"));
                        keys.add(document.get("PaymentDate"));
                        keys.add(document.get("IsPaid"));
						
						emitter.emit(keys , document);
					}
				}
				
			}, "4");
			
			Query orderedQuery = finalizedPaymentsView.createQuery();

			try {
				
		        QueryEnumerator results = orderedQuery.run();
		       /* Iterate through the rows to get the document ids */
		       for (Iterator<QueryRow> it = results; it.hasNext();) {
		          QueryRow row = it.next();
		          Document doc = row.getDocument();
	//	           Event event = eventRepository.getById(docId);
		          
		          paymentsList.add(doc);
 
		          Log.i("KrisMobile", "Found party:" + row);
		       }
		       
		       
		    } catch (CouchbaseLiteException e) {
		    	
		        Log.e("KrisMobile", "Error querying view.", e);
		        
		    }	    
		}
		
		return paymentsList;		
	}
	
	public boolean deletePayment(String paymentId){
		Document paymentToDelete = getPayment(paymentId);
		
		try {
			
			paymentToDelete.delete();
			return true;
		} catch (CouchbaseLiteException e) {

			e.printStackTrace();
			return false;
		}
	}
}
