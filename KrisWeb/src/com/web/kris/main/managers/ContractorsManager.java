package com.web.kris.main.managers;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;

import com.web.kris.main.entities.Contractor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mohru on 2016-01-26.
 */
public class ContractorsManager {

    public static final int SELLER_TYPE = 1;
    public static final int BUYER_TYPE = 2;

    private static ContractorsManager manager;
    private static final String DOC_TYPE = "Contractor";

    public static ContractorsManager getInstance(){
        if(manager == null)
            manager = new ContractorsManager();

        return manager;
    }

    public String saveContractor(Contractor contractor){    //  save & update

        JsonObject content = JsonObject.empty()
                .put("DocType", DOC_TYPE)
                .put("Code", contractor.getCode())
                .put("TypeId", contractor.getType().ordinal()+1)
                .put("NIP", contractor.getNIP())
                .put("Address", contractor.getAddress())
                .put("Description", contractor.getDescription())
                .put("ModificationTS", String.valueOf((new Date()).getTime()));

        JsonDocument inserted = null;

        String docId = UUID.randomUUID().toString().toLowerCase();

        while(DatabaseManager.getInstance().getBucketInstance().exists(docId))
            docId = UUID.randomUUID().toString().toLowerCase();

        if(!contractor.getId().equals("")){
            JsonDocument doc = JsonDocument.create(contractor.getId(), content);
            inserted = DatabaseManager.getInstance().getBucketInstance().replace(doc);
    //        DatabaseManager.getInstance().putData(content);
        }
        else{
            JsonDocument doc = JsonDocument.create(docId, content);
            inserted = DatabaseManager.getInstance().getBucketInstance().insert(doc);
    //        DatabaseManager.getInstance().postData(content);
        }

        return  inserted.id();
    }

    public Contractor getContractor(String contractorId){

        JsonDocument contractorDoc = DatabaseManager.getInstance().getBucketInstance().get(contractorId);

        return new Contractor(contractorDoc);
    }

    public List<Contractor> getAllContractors(){

        ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_contractors", "by_name"));

        List<ViewRow> rows = result.allRows();
//        System.out.println(rows);

        List<Contractor> contractors = new ArrayList<>();

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            if(doc != null && doc.content() != null)
                contractors.add(new Contractor(doc));
        }

        return contractors;
    }

    public boolean deleteContractor(String contractorId){

        DatabaseManager.getInstance().getBucketInstance().remove(contractorId);
        return true;
    }
}
