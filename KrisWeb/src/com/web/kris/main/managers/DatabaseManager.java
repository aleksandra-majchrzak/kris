package com.web.kris.main.managers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.view.*;

import javax.management.Query;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mohru on 2016-01-26.
 */

public class DatabaseManager {

    private static DatabaseManager manager;
    private static CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().autoreleaseAfter(6000).build();
    private static String bucketName = "kris_mobile_db";
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

    public boolean establishConnection(String host, String port, String bucketName){

        System.out.println("in database manager2");
        // Connect to localhost
        cluster = CouchbaseCluster.create(env, host+":"+port);

        DatabaseManager.bucketName = bucketName;
        bucket = cluster.openBucket(bucketName);

        // tylko przy pierwszym polaczeniu
        if(bucket.bucketManager().getDesignDocument("dev_users") == null)
            createViews();

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
}
