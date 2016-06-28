package com.example.krismobile.database;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.example.krismobile.R;
import com.example.krismobile.database.managers.ContractorsManager;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.database.managers.PaymentsManager;
import com.example.krismobile.database.managers.UserManager;

public class DatabaseManager {
	private static final String DB_NAME = "kris_mobile_db";

	private static Manager manager;
	private static DatabaseManager databaseManager;
	private static Database database;
	private static Context context;
	private static String SHARED = "KrisPreferences";
	
	public static Manager getManagerInstance(Context context) throws IOException{
		
		if(databaseManager == null){
			databaseManager = new DatabaseManager();
		}
		if(manager == null){
			manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
			DatabaseManager.context = context;
		}
		
		return manager;
	}
	
	public static DatabaseManager getInstance(){
		if(databaseManager == null)
			databaseManager = new DatabaseManager();
		
		return databaseManager;
	}
	
	public static Database getDatabaseInstance() throws CouchbaseLiteException{

		if ((database == null) && (manager != null)) {
	        database = manager.getDatabase(DB_NAME);
	    }
	    return database;
	}
	
	public static void close(){
		database.close();
		manager = null;
		database = null;
	}
	
	public boolean login(String login, String password){

        String hashedPswd = hashPswd(password);
        SharedPreferences prefs = context.getSharedPreferences(SHARED, 0);
        boolean loginResult = false;
        
        if(prefs.contains("Login")){
        	loginResult = UserManager.getInstance().authenticateUser(login, hashedPswd);
        }
        else{
        	// tu powinno byc nawiazanie polaczenia z serverem i sprawdzenie uzytkownika
        	if(loginResult){
        		// uzytkownik powinien byc zaladowany do bazy
        		prefs.edit().putString("Login", login);
        	}
        }
        
         
       return loginResult;
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
	
	public void deleteDatabase(){
		
		try {
			
			getDatabaseInstance().delete();
			
		} catch (CouchbaseLiteException e) {
			
			Toast.makeText(context, R.string.database_could_not_be_deleted, Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
		Toast.makeText(context, R.string.database_deleted, Toast.LENGTH_LONG).show();
	}
}
