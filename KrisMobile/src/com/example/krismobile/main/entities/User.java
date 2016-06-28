package com.example.krismobile.main.entities;

import com.couchbase.lite.Document;

public class User {
	
	private String id;
	private String login;
	private String hashedPassword;
	private boolean isAdmin;
	private boolean isActive;
	
	public User(Document userDocument){
			
		this.id = userDocument.getId();
		this.login = (String) userDocument.getProperty("Login");
		this.hashedPassword = (String) userDocument.getProperty("HashedPassword");
		this.isAdmin =  (Boolean) userDocument.getProperty("IsAdmin");
		this.isActive=  (Boolean) userDocument.getProperty("IsActive");
	}
	
	public User(){
		this.id = "";
		this.login = "";
		this.hashedPassword = "";
		this.isAdmin = false;
		this.isActive= false;
	}
	
	public User(String id, String login, String hashedPassword, boolean isAdmin, boolean isActive) {

		this.id = id;
		this.login = login;
		this.hashedPassword = hashedPassword;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
