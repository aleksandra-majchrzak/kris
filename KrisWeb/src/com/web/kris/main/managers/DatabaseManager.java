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

/**
 * Created by Mohru on 2016-01-26.
 */

public class DatabaseManager {

    private static DatabaseManager manager;
    private Cluster cluster;
    private Bucket bucket;

    public static DatabaseManager getInstance(){


        if(manager == null)
            manager = new DatabaseManager();

        System.out.println("in database manager");

        return manager;
    }

    public boolean establishConnection(){
/*
        // Set up at least two URIs in case one server fails

        List<URI> servers = new ArrayList<URI>();
        servers.add("http://<host>:8091/pools");
        servers.add("http://<host>:8091/pools");

    // Create a client talking to the default bucket

        CouchbaseClient cbc = new CouchbaseClient(servers, "default", "");

    // Create a client talking to the default bucket

        CouchbaseClient cbc = new CouchbaseClient(servers, "default", "");

        System.err.println(cbc.get(“thisname") +
                " is off developing with Couchbase!");


 */
        CouchbaseEnvironment env =DefaultCouchbaseEnvironment.builder().autoreleaseAfter(6000).build();

        System.out.println("in database manager2");
        // Connect to localhost
        cluster = CouchbaseCluster.create(env);

// Open the default bucket and the "beer-sample" one
        bucket = cluster.openBucket();

        Bucket beerSampleBucket = cluster.openBucket("beer-sample");


        //just an example
        ViewResult result = beerSampleBucket.query(ViewQuery.from("dev_beer", "by_name"));

        for (ViewRow row : result) {
            JsonDocument doc = row.document();

            if (doc.content().getString("type").equals("beer")) {
                System.out.println(doc.content().getString("name"));
            }
        }
/*
        bucket
                .async()
                .query(Query.simple(select("*").from("beer-sample").limit(10)))
                .flatMap(AsyncQueryResult::rows)
                .toBlocking()
                .forEach(row -> System.out.println(row.value()));
*/
        return true;
    }

    public boolean closeConnection(){

        // Disconnect and clear all allocated  resources
        cluster.disconnect();

        return true;
    }
}
