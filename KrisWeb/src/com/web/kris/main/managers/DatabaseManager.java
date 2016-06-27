package com.web.kris.main.managers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.Index;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.view.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.management.Query;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static com.couchbase.client.java.query.Index.*;

/**
 * Created by Mohru on 2016-01-26.
 */

public class DatabaseManager {

    private static DatabaseManager manager;
    private static CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().autoreleaseAfter(6000).build();
    private static String bucketName = "kris_mobile_db";
    private String databaseURL;
    private Cluster cluster;
    private Bucket bucket;

    public static DatabaseManager getInstance(){

        if(manager == null)
            manager = new DatabaseManager();

        return manager;
    }

    public Bucket getBucketInstance(){
        if(bucket == null)
            bucket = cluster.openBucket(bucketName);

        return bucket;
    }

    public static String getBucketName(){
        return bucketName;
    }

    public boolean establishConnection(String host, String port, String bucketName){

        System.out.println("in database manager2");

        System.setProperty("com.couchbase.queryEnabled", "true");
        // Connect to localhost
        cluster = CouchbaseCluster.create(env, host+":"+port);


        DatabaseManager.bucketName = bucketName;
        bucket = cluster.openBucket(bucketName);

        databaseURL = "http://"+host+":"+"4984"+"/"+bucketName; // na razie adres servera na sztywno- sprawdz czydziala

        // tylko przy pierwszym polaczeniu
        if(bucket.bucketManager().getDesignDocument("dev_users") == null) {
            createIndex();
            createViews();
            createFilters();
        }

        return true;
    }

    public boolean closeConnection(){

        // Disconnect and clear all allocated  resources
        cluster.disconnect();

        return true;
    }

    private void createViews(){

        BucketManager bucketManager = bucket.bucketManager();

        // Initialize design document
        DesignDocument designDoc = DesignDocument.create(
                "dev_users",
                Arrays.asList(
                        DefaultView.create("by_login",
                                "function (doc, meta) { if(doc.DocType && doc.DocType == \"User\") { emit(meta.id, null); } }"),
                        DefaultView.create("by_login_active",
                                "function (doc, meta) { if(doc.DocType && doc.DocType == \"User\" && doc.IsActive) { emit(meta.id, null); } }")
                )
        );

        // Insert design document into the bucket
        bucketManager.insertDesignDocument(designDoc);

        designDoc = DesignDocument.create(
                "dev_contractors",
                Arrays.asList(
                        DefaultView.create("by_name",
                                "function (doc, meta) { if(doc.DocType && doc.DocType == \"Contractor\") { emit(doc.Code, doc.Id); } }")
                )
        );

        // Insert design document into the bucket
        bucketManager.insertDesignDocument(designDoc);

        designDoc = DesignDocument.create(
                "dev_documents",
                Arrays.asList(
                        DefaultView.create("by_date",
                                "function (doc, meta) { if(doc.DocType && doc.DocType == \"Document\") { emit(doc.DocumentDate, doc.Id); } }")
                )
        );

        // Insert design document into the bucket
        bucketManager.insertDesignDocument(designDoc);

    }

    private void createFilters(){
        BucketManager bucketManager = bucket.bucketManager();

        // Initialize design document
        DesignDocument designDoc = DesignDocument.create(
                "dev_users",
                Arrays.asList(
                        DefaultView.create("user_filter",
                                "function (doc, req) { if(doc.DocType && doc.DocType == \"User\" && doc.IsActive && doc.Login == req.query.text) { return true } else {return false} }")
                )
        );

        // Insert design document into the bucket
        bucketManager.insertDesignDocument(designDoc);

    }

    private void createIndex(){
        bucket.bucketManager().createPrimaryIndex(true, false);
    }


    public static HttpPost createPostForJSONObject( JsonObject params, String url) {

        HttpPost post = new HttpPost(url);
        post.setEntity(createStringEntity(params));
        return post;
    }

    public static HttpPut createPutForJSONObject( JsonObject params, String url) {

        HttpPut put = new HttpPut(url);
        put.setEntity(createStringEntity(params));
        return put;
    }

    private static HttpEntity createStringEntity(JsonObject params) {

        StringEntity se = new StringEntity(params.toString(), "UTF-8");
        se.setContentType("application/json; charset=UTF-8");
        return se;
    }

    public void postData(JsonObject json) {

        HttpClient httpClient = new DefaultHttpClient();
        int statusCode = 0;
        HttpPost post = createPostForJSONObject(json, this.databaseURL);

        System.out.println("In postData "+ databaseURL);

        try {
            HttpResponse response = httpClient.execute(post);
            statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 409) {
                //do logging n fail response
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8"); // its in json
                System.out.println(responseString);
            } else if (statusCode == 200) {
                // success response
                System.out.println("postData: post OK");
            } else {
                // do logging n fail response
                System.out.println("postData: post error" +String.valueOf(statusCode));
            }

            //String responseString=new BasicResponseHandler().handleResponse(response);
            //System.out.println(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putData(JsonObject json) {

        HttpClient httpClient = new DefaultHttpClient();
        int statusCode =0;
        HttpPut put =  createPutForJSONObject(json, this.databaseURL);

        System.out.println("In put data "+databaseURL);

        try {
            HttpResponse response = httpClient.execute(put);
            statusCode= response.getStatusLine().getStatusCode();

            if(statusCode==409){
                //do logging n fail response
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8"); // its in json
                System.out.println(responseString);
            } else if (statusCode == 200) {
                // success response
                System.out.println("putData: post OK");
            } else {
                // do logging n fail response
                System.out.println("putData: post error"+ String.valueOf(statusCode));
            }

            //String responseString=new BasicResponseHandler().handleResponse(response);
            //System.out.println(responseString);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
