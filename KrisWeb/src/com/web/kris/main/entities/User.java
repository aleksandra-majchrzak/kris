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
        this.login = "";
        this.hashedPassword = "";
        this.isAdmin = false;
        this.isActive = false;
    }

    public User(String login, String hashedPassword, boolean isAdmin, boolean isActive) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
