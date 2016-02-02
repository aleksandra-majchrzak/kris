package com.web.kris.main.managers;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.web.kris.main.entities.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mohru on 2016-01-26.
 */
public class UsersManager {

    private static UsersManager manager;
    private static final String DOC_TYPE = "User";

    public static UsersManager getInstance(){
        if(manager == null)
            manager = new UsersManager();

        return manager;
    }
    public String saveUser(User user){    //  save & update

        JsonObject content = JsonObject.empty()
                .put("DocType", DOC_TYPE)
                .put("Login", user.getLogin())
                .put("HashedPassword", user.getHashedPassword())
                .put("IsAdmin", user.getIsAdmin())
                .put("IsActive", user.getIsActive())
                .put("ModificationTS", String.valueOf((new Date()).getTime()));

        JsonDocument inserted = null;

        String docId = user.getLogin();

        try {
            if (!user.getId().equals("")) {
                JsonDocument doc = JsonDocument.create(user.getId(), content);
                inserted = DatabaseManager.getInstance().getBucketInstance().replace(doc);
            } else {
                // tylko do wersji rozwojowej- slabo jest za kazdym razem sprawdzac czy user jest pierwszy
                ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_users", "by_login"));
                if(result.totalRows() == 0){
                    content.put("IsAdmin", true)
                            .put("IsActive", true);
                    user.setIsAdmin(true);
                    user.setIsActive(true);
                }

                JsonDocument doc = JsonDocument.create(docId, content);
                inserted = DatabaseManager.getInstance().getBucketInstance().insert(doc);
            }
        }catch(DocumentAlreadyExistsException e){
            return "";
        }

        return inserted.id();
    }

    public User getUser(String userId){
        return null;
    }

    public User authenticateUser(String login, String password){

        ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_users", "by_login_active"));
        String hashedPswd = hashPswd(password);

        List<ViewRow> rows = result.allRows();
        System.out.println(rows);

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            if(doc != null && doc.content() != null)
                if(doc.content().getString("Login").equals(login) && doc.content().getString("HashedPassword").equals(hashedPswd))
                return new User(doc);
        }

        return null;
    }

    public List<User> getAllUsers(){
        ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_users", "by_login"));

        List<ViewRow> rows = result.allRows();
        System.out.println(rows);

        List<User> users = new ArrayList<>();

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            if(doc != null && doc.content() != null)
                users.add(new User(doc));
        }

        return users;
    }

    public boolean deleteUser(String userId){

        DatabaseManager.getInstance().getBucketInstance().remove(userId);
        return true;
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
