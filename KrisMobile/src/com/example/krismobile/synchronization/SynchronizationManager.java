package com.example.krismobile.synchronization;

import java.net.MalformedURLException;
import java.net.URL;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.replicator.Replication;
import com.example.krismobile.database.DatabaseManager;

public class SynchronizationManager {
	
	public static void setupConnection(){
		
	}
	
	private URL createSyncURL(boolean isEncrypted){
	    URL syncURL = null;
	    String host = "https://10.0.2.2";	// docelowo powinnam zastapic to adresem ip serwera pobranym z shared preferences ustawianych w preferancjach??
	    String port = "4984";
	    String dbName = "couchbaseevents";
	    try {
	        syncURL = new URL(host + ":" + port + "/" + dbName);
	    } catch (MalformedURLException me) {
	        me.printStackTrace();
	    }
	    return syncURL;
	}
	
	private void startReplications() throws CouchbaseLiteException {
		
	    Replication pull = DatabaseManager.getDatabaseInstance().createPullReplication(this.createSyncURL(false));
	    Replication push = DatabaseManager.getDatabaseInstance().createPushReplication(this.createSyncURL(false));
	    
	    pull.setContinuous(true);
	    push.setContinuous(true);
	    
	    pull.start();
	    push.start();
	    
	}

}
