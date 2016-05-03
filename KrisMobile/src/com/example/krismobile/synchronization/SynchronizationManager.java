package com.example.krismobile.synchronization;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.krismobile.main.SettingsFragment;

public class SynchronizationManager implements ChangeListener {
	
	private static SynchronizationManager manager;
	private static Context context;
	public static String TAG = "KrisMobile";
	private ProgressDialog progressDialog;
	
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
		
	    Replication pull = DatabaseManager.getDatabaseInstance().createPullReplication(this.createSyncURL(false));
	    Replication push = DatabaseManager.getDatabaseInstance().createPushReplication(this.createSyncURL(false));
	    
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

        Replication replication = event.getSource();
        Log.d(TAG, "Replication : " + replication + " changed.");
        if (!replication.isRunning()) {
            String msg = String.format("Replicator %s not running", replication);
            Log.d(TAG, msg);
            
            progressDialog.dismiss();
            replication.stop();
        }
        else {
            int processed = replication.getCompletedChangesCount();
            int total = replication.getChangesCount();
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

}
