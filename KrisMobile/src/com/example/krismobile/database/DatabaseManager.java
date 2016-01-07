package com.example.krismobile.database;

import java.io.IOException;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

public class DatabaseManager {
	private static final String DB_NAME = "kris_mobile_db";

	private static Manager manager;
	private static Database database;
	
	public static Manager getManagerInstance(Context context) throws IOException{
		if(manager == null)
			manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
		
		return manager;
	}
	
	public static Database getDatabaseInstance() throws CouchbaseLiteException{

		if ((database == null) & (manager != null)) {
	        database = manager.getDatabase(DB_NAME);
	    }
	    return database;
	}
}
