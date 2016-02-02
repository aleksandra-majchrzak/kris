package com.web.kris.main.managers;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.web.kris.main.entities.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mohru on 2016-01-26.
 */
public class UserManager {

    private static UserManager manager;
    private static final String DOC_TYPE = "User";

    public static UserManager getInstance(){
        if(manager == null)
            manager = new UserManager();

        return manager;
    }
    public String saveUser(User user){    //  save & update

        return "";
    }

    public User getUser(String userId){
        return null;
    }

    public User authenticateUser(String login, String password){

        ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_contractors", "by_name"));
        String hashedPswd = hashPswd(password);

        List<ViewRow> rows = result.allRows();
        System.out.println(rows);

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            if(doc != null && doc.content() != null)
                if(doc.content().getString("Login").equals(login) && doc.content().equals(hashedPswd))
                return new User(doc);
        }

        return null;
    }

    public boolean deleteUser(String userId){

        return true;
    }

    private String hashPswd(String pswd){

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
