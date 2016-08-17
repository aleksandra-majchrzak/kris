package com.example.krismobile.synchronization;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.couchbase.lite.replicator.Replication.ChangeEvent;
import com.couchbase.lite.replicator.Replication.ChangeListener;
import com.example.krismobile.R;
import com.example.krismobile.database.DatabaseManager;
import com.example.krismobile.database.managers.UserManager;
import com.example.krismobile.main.MainActivity;
import com.example.krismobile.main.MenuActivity;
import com.example.krismobile.main.SettingsFragment;

public class SynchronizationManager implements ChangeListener {
	
	private static SynchronizationManager manager;
	private static Context context;
	public static String TAG = "KrisMobile";
	private ProgressDialog progressDialog;
	private boolean isLoginSync = false;
	private String login;
	private String hashedPassword;
	
	Replication pull;
    Replication push;
	
	public static SynchronizationManager getManagerInstance(Context context){
		if(manager == null){
			manager = new SynchronizationManager();
		}
		
		SynchronizationManager.context = context;
		return manager;
	}
	
	public void setupConnection() throws CouchbaseLiteException{
		manager.startReplications();
	}
	
	private URL createSyncURL(boolean isEncrypted){
		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
	    URL syncURL = null;
/*	    String host = "https://192.168.0.11";
	    String port = "8091";
	    String dbName = "kris_mobile_db";
*/	    
	    String host = shared.getString(SettingsFragment.SERVER_ADDRESS_PREF, "");
	    String port = shared.getString(SettingsFragment.PORT_NUMBER_PREF, "");
	    String dbName = shared.getString(SettingsFragment.BUCKET_NAME_PREF, "");
	    try {
	        syncURL = new URL( "http://"+host + ":" + port + "/" + dbName);
	    } catch (MalformedURLException me) {
	        me.printStackTrace();
	    }
	    return syncURL;
	}
	
	private void startReplications() throws CouchbaseLiteException {
		
	    pull = DatabaseManager.getDatabaseInstance().createPullReplication(this.createSyncURL(false));
	    push = DatabaseManager.getDatabaseInstance().createPushReplication(this.createSyncURL(false));
	    
	    pull.setContinuous(false);
	    push.setContinuous(false);
	    
	    
	    
	    pull.start();
	    push.start();
	    
	    
	    
	    progressDialog = showLoadingSpinner();
	    
	    pull.addChangeListener(this);
	    push.addChangeListener(this);
	}

    @Override
    public void changed(Replication.ChangeEvent event) {

    	boolean active = (pull.getStatus() == Replication.ReplicationStatus.REPLICATION_ACTIVE) ||
    	        ((! isLoginSync && push.getStatus() == Replication.ReplicationStatus.REPLICATION_ACTIVE));
        
        if (! active) {
            String msg = "Replications not running";
            Log.d(TAG, msg);
            
            progressDialog.dismiss();
            
            if(isLoginSync){
            	
            	if(UserManager.getInstance().authenticateUser(login, hashedPassword)){
            		
            		Intent intent = new Intent(context, MenuActivity.class);
					
					context.startActivity(intent);
					
					((Activity) context).finish();
					
            	}
            	else{
            		Toast.makeText(context, "B³êdny login lub has³o", Toast.LENGTH_LONG).show();
            	}
            	
            	isLoginSync = false;
            }
        }
        else {
        	
        	int total = push.getCompletedChangesCount() + pull.getCompletedChangesCount();
        	int processed = push.getChangesCount() + pull.getChangesCount();
        	
            progressDialog.setMax(total);
            progressDialog.setProgress(processed);

            String msg = String.format("Replicator processed %d / %d", processed, total);
            Log.d(TAG, msg);
        }

        if (event.getError() != null) {
            showError("Sync error", event.getError());
        }

    }

    public void showError(final String errorMessage, final Throwable throwable) {

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = String.format("%s: %s", errorMessage, throwable);
                Log.e(TAG, msg, throwable);
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });

    }
    
    private ProgressDialog showLoadingSpinner() {
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle(context.getResources().getString(R.string.synchronization));
        progress.setMessage(context.getString(R.string.synchro_in_progress));
        progress.show();
        return progress;
    }
    
    public void startFirstLoginReplication(String login, String password) throws CouchbaseLiteException{
    	
    	isLoginSync = true;
    	this.login = login;
    	hashedPassword = UserManager.getInstance().hashPswd(password);
    	
    	pull = DatabaseManager.getDatabaseInstance().createPullReplication(this.createSyncURL(false));
 	    
   // 	List<String> channels = new ArrayList<String>();
   // 	channels.add(login);
    //	pull.setChannels(channels);
    	
 	    pull.setContinuous(false);
 	    
 	    pull.addChangeListener(this); 
 	    
 	    pull.start();
 	    
 	    progressDialog = showLoadingSpinner();
 	    
 	      
    }

}
