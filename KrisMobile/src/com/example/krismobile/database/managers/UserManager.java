package com.example.krismobile.database.managers;

import java.util.ArrayList;
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
import com.couchbase.lite.Reducer;
import com.couchbase.lite.View;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.items.Item;

public class UserManager {
	
	private static UserManager manager;
	private String DOC_TYPE = "User";
	
	
	public static UserManager getInstance(){
		if(manager == null)
			manager = new UserManager();
		
		return manager;
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

	
	public boolean authenticateUser(String login, String hashedPassword){
		
		Document user = null;
		View userView = getView("getUser");

		
		if(userView != null){
			
			userView.setMapReduce(new Mapper(){

				@Override
				public void map(Map<String, Object> document, Emitter emitter) {
					
					if(document.get("DocType").equals(DOC_TYPE)){

						emitter.emit(document.get("Login"), null);
					}
				}
				
			},
			new Reducer(){

				@Override
				public Object reduce(List<Object> keys, List<Object> values, boolean rereduce) {
					
					if(! values.isEmpty())
						return values.get(0);
					else
						return null;
				}
				
			}, "1");
			
			List<Object> keys = new ArrayList<Object>();
			keys.add(login);
			
			user = getUserFromView(userView, 0, keys, false);
		}
		
		if(user == null)
			return false;
		
		if(user.getProperty("HashedPassword").equals(hashedPassword))
			return true;
		
		return false;	
	}
	
	
	
	private Document getUserFromView(View queryView, int limit, List<Object> keys, boolean descending){
		
		Document userDocument = null;
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
	          
	          userDocument = doc;
	          break;
	       }
	       
	       
	    } catch (CouchbaseLiteException e) {
	    	
	        Log.e("KrisMobile", "Error querying view.", e);
	        
	    }	  
		
		return userDocument;
		
	}
}
