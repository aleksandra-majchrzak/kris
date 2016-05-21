package com.web.kris.main.managers;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.web.kris.main.entities.Document;

import java.util.ArrayList;
import java.util.Date;
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

    public String saveDocument(Document document){    //  save & update
        JsonObject content = JsonObject.empty()
                .put("DocType", DOC_TYPE)
                .put("Number", document.getNumber())
                .put("TypeId", document.getType().getValue())
                .put("ContractorId", document.getContractor().getId())
                .put("DocumentDate", document.getDocumentDate().getTime())
                .put("PaymentDate", document.getPaymentDate().getTime())
                .put("Description", document.getDescription())
                .put("PaymentForm", document.getPaymentForm().getValue())
                .put("NetValue", document.getNetValue())
                .put("NetGrossValue", document.getGrossValue())
                .put("ModificationTS", String.valueOf((new Date()).getTime()));

        JsonDocument inserted = null;

        String docId = DOC_TYPE+String.valueOf(DatabaseManager.getInstance().getBucketInstance().counter(DOC_TYPE, 1, 0));

        if(!document.getId().equals("")){
            JsonDocument doc = JsonDocument.create(document.getId(), content);
            inserted = DatabaseManager.getInstance().getBucketInstance().replace(doc);
        }
        else{
            JsonDocument doc = JsonDocument.create(docId, content);
            inserted = DatabaseManager.getInstance().getBucketInstance().insert(doc);
        }

        return inserted.id();
    }

    public Document getDocument(String documentId){
        return null;
    }

    public List<Document> getAllDocuments(){

        ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_documents", "by_date"));

        List<ViewRow> rows = result.allRows();
        System.out.println(rows);

        List<Document> documents = new ArrayList<>();

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            if(doc != null && doc.content() != null)
                documents.add(new Document(doc));
        }

        return documents;
    }

    public boolean deleteDocument(String documentId){

        DatabaseManager.getInstance().getBucketInstance().remove(documentId);
        return true;

    }
}
