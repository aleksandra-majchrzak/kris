package com.web.kris.main.managers;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.*;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.web.kris.main.entities.DocumentPositionsList;
import com.web.kris.main.entities.Item;
import com.web.kris.main.entities.ItemStocks;
import com.web.kris.main.entities.Price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.*;

/**
 * Created by Mohru on 2016-01-26.
 */
public class ItemsManager {

    private static ItemsManager manager;
    private static final String DOC_TYPE = "Item";
    private static final String PRICE_TYPE = "Price";
    private static final String STOCKS_TYPE = "ItemStocks";

    public static ItemsManager getInstance(){
        if(manager == null)
            manager = new ItemsManager();

        return manager;
    }

    public List<Item> getAllItems(){

        ViewResult result = DatabaseManager.getInstance().getBucketInstance().query(ViewQuery.from("dev_items", "by_code"));

        List<ViewRow> rows = result.allRows();
//        System.out.println(rows);

        List<Item> items = new ArrayList<>();

        for (ViewRow row : rows) {
            JsonDocument doc = row.document();

            if(doc != null && doc.content() != null)
                items.add(new Item(doc));
        }

        return items;
    }

    public String saveItem(Item item){    //  save & update

        JsonObject content = JsonObject.empty()
                .put("DocType", DOC_TYPE)
                .put("Code", item.getCode())
                .put("Name", item.getName())
                .put("Type", item.getType())
                .put("PriceId", item.getPrice().getId())
                .put("Size", item.getSize())
                .put("Material", item.getMaterial())
                .put("Description", item.getDescription())
                .put("GrossPrice", item.getPrice().getGrossPrice())
                .put("NetPrice", item.getPrice().getNetPrice())
                .put("ModificationTS", String.valueOf((new Date()).getTime()));

        JsonDocument inserted = null;

        String docId = UUID.randomUUID().toString().toLowerCase();

        while(DatabaseManager.getInstance().getBucketInstance().exists(docId))
            docId = UUID.randomUUID().toString().toLowerCase();

        if(!item.getId().equals("")){
            JsonDocument doc = JsonDocument.create(item.getId(), content);
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

    public String savePrice(Price price){

        JsonObject content = JsonObject.empty()
                .put("DocType", PRICE_TYPE)
                .put("NetPrice", price.getNetPrice())
                .put("GrossPrice", price.getGrossPrice())
                .put("ModificationTS", String.valueOf((new Date()).getTime()));

        JsonDocument inserted = null;

        String docId = UUID.randomUUID().toString().toLowerCase();

        while(DatabaseManager.getInstance().getBucketInstance().exists(docId))
            docId = UUID.randomUUID().toString().toLowerCase();

        if(!price.getId().equals("")){
            JsonDocument doc = JsonDocument.create(price.getId(), content);
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

    public Item getItem(String itemId){

        String bucketName = DatabaseManager.getBucketName();

        Statement statement = select(bucketName + ".*, meta("+bucketName+").id")
                .from(i(DatabaseManager.getBucketName()))
                .where(x("DocType").eq(x("$DocType"))
                        .and(x("meta("+bucketName+").id")).eq(x("$ItemId")));

        JsonObject placeholderValues = JsonObject.create().put("$DocType", DOC_TYPE);
        placeholderValues.put("$ItemId", itemId);
        ParameterizedN1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);

        N1qlQueryResult result = DatabaseManager.getInstance().getBucketInstance().query(q);

        if(result.finalSuccess()){
            return new Item(result.allRows().get(0).value());
        }

        return new Item();
    }

    public Price getItemPrice(String priceId){

        String bucketName = DatabaseManager.getBucketName();

        Statement statement = select(bucketName + ".*, meta("+bucketName+").id")
                .from(i(DatabaseManager.getBucketName()))
                .where(x("DocType").eq(x("$DocType"))
                        .and(x("meta("+bucketName+").id")).eq(x("$PriceId")));

        JsonObject placeholderValues = JsonObject.create().put("$DocType", PRICE_TYPE);
        placeholderValues.put("$PriceId", priceId);
        ParameterizedN1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);

        N1qlQueryResult result = DatabaseManager.getInstance().getBucketInstance().query(q);

        if(result.finalSuccess()){
            return new Price(result.allRows().get(0).value());
        }

        return new Price();
    }

    public List<ItemStocks> getItemStocks(String priceId){

        List<ItemStocks> stocks = new ArrayList<ItemStocks>();
        String bucketName = DatabaseManager.getBucketName();

        Statement statement = select(bucketName + ".*, meta("+bucketName+").id")
                .from(i(DatabaseManager.getBucketName()))
                .where(x("DocType").eq(x("$DocType"))
                        .and(x("meta("+bucketName+").id")).eq(x("$PriceId")));

        JsonObject placeholderValues = JsonObject.create().put("$DocType", STOCKS_TYPE);
        placeholderValues.put("$PriceId", priceId);
        ParameterizedN1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);

        N1qlQueryResult result = DatabaseManager.getInstance().getBucketInstance().query(q);

        for (N1qlQueryRow row : result) {
         //   System.out.println(row);
            stocks.add(new ItemStocks(row.value()));
        }

        return stocks;
    }

    public List<String> getAllItemTypes(){

        List<String> itemTypes = new ArrayList<String>();

        Statement statement = select("distinct Type")
                .from(i(DatabaseManager.getBucketName()))
                .where(x("DocType").eq(x("$DocType")));

        JsonObject placeholderValues = JsonObject.create().put("$DocType", DOC_TYPE);
        ParameterizedN1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);

        N1qlQueryResult result = DatabaseManager.getInstance().getBucketInstance().query(q);

        for (N1qlQueryRow row : result) {
            itemTypes.add(row.value().getString("Type"));
        }

        return itemTypes;
    }

    public List<String> getAllItemMaterials(){

        List<String> itemMaterials = new ArrayList<String>();

        Statement statement = select("distinct Material")
                .from(i(DatabaseManager.getBucketName()))
                .where(x("DocType").eq(x("$DocType")));

        JsonObject placeholderValues = JsonObject.create().put("$DocType", DOC_TYPE);
        ParameterizedN1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);

        N1qlQueryResult result = DatabaseManager.getInstance().getBucketInstance().query(q);

        for (N1qlQueryRow row : result) {
            itemMaterials.add(row.value().getString("Material"));
        }

        return itemMaterials;
    }

    public List<String> getAllItemSizes(){

        List<String> itemSizes = new ArrayList<String>();

        Statement statement = select("distinct Size")
                .from(i(DatabaseManager.getBucketName()))
                .where(x("DocType").eq(x("$DocType")));

        JsonObject placeholderValues = JsonObject.create().put("$DocType", DOC_TYPE);
        ParameterizedN1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);

        N1qlQueryResult result = DatabaseManager.getInstance().getBucketInstance().query(q);

        for (N1qlQueryRow row : result) {
            itemSizes.add(row.value().getString("Size"));
        }

        return itemSizes;
    }


    public boolean deleteItem(String itemId){

        DatabaseManager.getInstance().getBucketInstance().remove(itemId);
        return true;

    }
}
