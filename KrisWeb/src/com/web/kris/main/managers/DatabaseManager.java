package com.web.kris.main.managers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;

import javax.management.Query;
import java.util.List;

/**
 * Created by Mohru on 2016-01-26.
 */

public class DatabaseManager {

    private static DatabaseManager manager;
    private static CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().autoreleaseAfter(6000).build();
    private Cluster cluster;
    private Bucket bucket;

    public static DatabaseManager getInstance(){

        if(manager == null)
            manager = new DatabaseManager();

        return manager;
    }

    public Bucket getBucketInstance(){
        if(bucket == null)
            bucket = cluster.openBucket();

        return bucket;
    }

    public boolean establishConnection(){

        System.out.println("in database manager2");
        // Connect to localhost
        cluster = CouchbaseCluster.create(env);

// Open the default bucket and the "beer-sample" one
        bucket = cluster.openBucket();

//        Bucket beerSampleBucket = cluster.openBucket("beer-sample");


        //just an example
        //ViewResult result = beerSampleBucket.query(ViewQuery.from("dev_beer", "by_name"));
/*        ViewResult result = bucket.query(ViewQuery.from("dev_contractors", "by_name"));

        List<ViewRow> rows = result.allRows();
        System.out.println(rows);

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            if (doc.content().getString("DocType").equals("Contractor")) {
                System.out.println(doc.content().getString("Code"));
            }
        }
*/
        return true;
    }

    public boolean closeConnection(){

        // Disconnect and clear all allocated  resources
        cluster.disconnect();

        return true;
    }
}
