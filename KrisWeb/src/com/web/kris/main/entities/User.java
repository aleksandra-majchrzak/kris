package com.web.kris.main.entities;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

/**
 * Created by Mohru on 2016-01-26.
 */
public class User {

    private String id;
    private String login;
    private String hashedPassword;
    private boolean isAdmin;

    private boolean isActive;

    public User(){
        this.id = "";
        this.login = "";
        this.hashedPassword = "";
        this.isAdmin = false;
        this.isActive = false;
    }

    public User(String login, String hashedPassword, boolean isAdmin, boolean isActive) {
        this.id = "";
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    public User(JsonDocument document) {
          JsonObject content = document.content();

        this.id = document.id();
        this.login = content.getString("Login");
        this.hashedPassword = content.getString("HashedPassword");
        this.isAdmin =  content.getBoolean("IsAdmin");
        this.isActive = content.getBoolean("IsActive");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {

        this.isActive = isActive;
    }

}
