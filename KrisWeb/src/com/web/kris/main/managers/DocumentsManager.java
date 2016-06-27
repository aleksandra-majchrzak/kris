package com.web.kris.main.managers;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.*;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.web.kris.main.entities.Document;
import com.web.kris.main.entities.DocumentPosition;
import com.web.kris.main.entities.DocumentPositionsList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.*;

/**
 * Created by Mohru on 2016-01-26.
 */
public class DocumentsManager {

    private static DocumentsManager manager;
    private static final String DOC_TYPE = "Document";
    private static final String DOC_POSITION_TYPE = "DocumentPosition";
    private static final String DOC_NUMERATOR_TYPE = "DocumentNumerator";

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
                .put("ContractorCode", document.getContractor().getCode())
                .put("DocumentDate", String.valueOf(document.getDocumentDate().getTime()))
                .put("PaymentDate", String.valueOf(document.getPaymentDate().getTime()))
                .put("Description", document.getDescription())
                .put("PaymentForm", document.getPaymentForm().getValue())
                .put("NetValue", document.getNetValue())
                .put("GrossValue", document.getGrossValue())
                .put("ModificationTS", String.valueOf((new Date()).getTime()));

        JsonDocument inserted = null;

        String docId = UUID.randomUUID().toString().toLowerCase();

        while(DatabaseManager.getInstance().getBucketInstance().exists(docId))
            docId = UUID.randomUUID().toString().toLowerCase();

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

    public DocumentPositionsList getDocumentPositions(String documentId){

        DocumentPositionsList positionsList = new DocumentPositionsList();
        String bucketName = DatabaseManager.getBucketName();

        Statement statement = select(bucketName + ".*, meta("+bucketName+").id")
                .from(i(DatabaseManager.getBucketName()))
                .where(x("DocType").eq(x("$DocType"))
                .and(x("DocumentId")).eq(x("$DocumentId")));

        JsonObject placeholderValues = JsonObject.create().put("$DocType", DOC_POSITION_TYPE);
        placeholderValues.put("$DocumentId", documentId);
        ParameterizedN1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);

        N1qlQueryResult result = DatabaseManager.getInstance().getBucketInstance().query(q);

        for (N1qlQueryRow row : result) {
            System.out.println(row);
            positionsList.add(new DocumentPosition(row.value()));
        }

        return positionsList;
    }

    public boolean deleteDocument(String documentId){

        DatabaseManager.getInstance().getBucketInstance().remove(documentId);
        return true;

    }
}
