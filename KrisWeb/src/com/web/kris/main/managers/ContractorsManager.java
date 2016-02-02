package com.web.kris.main.managers;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.web.kris.main.entities.Contractor;

import java.util.ArrayList;
import java.util.List;

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
        return "";
    }

    public Contractor getContractor(String contractorId){

        JsonDocument contractorDoc = DatabaseManager.getInstance().getBucketInstance().get(contractorId);

        return new Contractor(contractorDoc);
    }

    public List<Contractor> getAllContractors(){

        ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_contractors", "by_name"));

        List<ViewRow> rows = result.allRows();
        System.out.println(rows);

        List<Contractor> contractors = new ArrayList<>();

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            contractors.add(new Contractor(doc));
        }

        return contractors;
    }

    public boolean deleteContractor(String contractorId){
        return true;
    }
}
