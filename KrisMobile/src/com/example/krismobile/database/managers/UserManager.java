package com.example.krismobile.database.managers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import com.couchbase.lite.Reducer;
import com.couchbase.lite.View;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.items.Item;
import com.example.krismobile.main.entities.User;

public class UserManager {
	
	private static UserManager manager;
	private String DOC_TYPE = "User";
	
	
	public static UserManager getInstance(){
		if(manager == null)
			manager = new UserManager();
		
		return manager;
	}
	
	public String saveUser(User user){
		
		Document userDocument;
		String userDocumentId = "";
		
		
		try {
			Map<String, Object> userMap =  new HashMap<String, Object>();
			userMap.put("DocType", DOC_TYPE);
			userMap.put("Login", user.getLogin());
			userMap.put("HashedPassword", user.getHashedPassword());
			userMap.put("IsAdmin", user.isAdmin());
			userMap.put("IsActive", user.isActive());
			
			if(user.getId().equals("")){
				userDocument = DatabaseManager.getDatabaseInstance().createDocument();
				
			}else{
				userDocument = DatabaseManager.getDatabaseInstance().getDocument(user.getId());	
				userMap.put("_rev", userDocument.getProperty("_rev"));
			}
			
			userDocumentId = userDocument.getId();
			userDocument.putProperties(userMap);
			
			return userDocumentId;
			
		} catch (CouchbaseLiteException e) {

			e.printStackTrace();
			Log.e("Database Error", "Error putting", e);
		}
		
		return userDocumentId;
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

	public User getUser(String login){
		
		User user = null;
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
			
			Document userDocument = getUserFromView(userView, 0, keys, false);
			
			if(userDocument != null)
				return new User(userDocument);
			else
				return null;
		}
		
		return null;
	}
	
	public boolean authenticateUser(String login, String hashedPassword){
		
		User user = getUser(login);
		
		if(user == null)
			return false;
		
		if(user.getHashedPassword().equals(hashedPassword))
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
	
	public String hashPswd(String pswd){

        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] md5hash = new byte[32];
            
            md.update(pswd.getBytes("iso-8859-1"), 0, pswd.length());
            md5hash = md.digest();
            
            return convertToHex(md5hash);

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	    }
        
        return "";
    }

    private static String convertToHex(byte[] data) {
    	
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < data.length; i++) {
        	
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                
                halfbyte = data[i] & 0x0F;
                
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }
}
