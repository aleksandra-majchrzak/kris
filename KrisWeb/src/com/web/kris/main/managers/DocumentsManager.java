package com.web.kris.main.managers;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.web.kris.main.entities.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohru on 2016-01-26.
 */
public class DocumentsManager {

    private static DocumentsManager manager;
    private static final String DOC_TYPE = "Document";

    public static DocumentsManager getInstance(){
        if(manager == null)
            manager = new DocumentsManager();

        return manager;
    }

    public String saveDcoument(Document document){    //  save & update
        return "";
    }

    public Document getDcoument(String documentId){
        return null;
    }

    public List<Document> getAllDocuments(){

        ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_documents", "by_date"));

        List<ViewRow> rows = result.allRows();
        System.out.println(rows);

        List<Document> documents = new ArrayList<>();

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            documents.add(new Document(doc));
        }

        return documents;
    }

    public boolean deleteDcoument(String documentId){
        return true;
    }
}
