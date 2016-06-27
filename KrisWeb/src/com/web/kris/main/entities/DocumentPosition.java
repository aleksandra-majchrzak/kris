package com.web.kris.main.entities;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.web.kris.main.managers.DatabaseManager;
import com.web.kris.main.managers.ItemsManager;

import java.util.Observable;

/**
 * Created by Mohru on 2016-06-26.
 */
public class DocumentPosition extends Observable {
    String id;
    String documentId;
    Item item;
    int ordinal;
    double quantity;
    double netValue;
    double grossValue;

    public DocumentPosition(){
        this.id = "";
        this.documentId = "";
        this.item = null;
        this.ordinal = 0;
        this.quantity = 0;
        this.netValue = 0;
        this.grossValue = 0;
    }

    public DocumentPosition(String id, String documentId, Item item,
                            int ordinal, double quantity, double netValue, double grossValue) {

        this.id = id;
        this.documentId = documentId;
        this.item = item;
        this.ordinal = ordinal;
        this.quantity = quantity;
        this.netValue = netValue;
        this.grossValue = grossValue;
    }

    public DocumentPosition(JsonDocument documentPosition) {

        JsonObject content = documentPosition.content();

        Item item = ItemsManager.getInstance().getItem(content.getString("ItemId"));

        this.id = documentPosition.id();
        this.documentId = content.getString("DocumentId");
        this.item = item;
        this.ordinal = content.getInt("Ordinal");
        this.quantity = content.getDouble("Quantity");
        this.netValue =  content.getDouble("NetValue");
        this.grossValue =  content.getDouble("GrossValue");
    }

    public DocumentPosition(JsonObject documentPosition) {

        Item item = ItemsManager.getInstance().getItem(documentPosition.getString("ItemId"));

        this.id = documentPosition.getString("id");
        this.documentId = documentPosition.getString("DocumentId");
        this.item = item;
        this.ordinal = documentPosition.getInt("Ordinal");
        this.quantity = documentPosition.getDouble("Quantity");
        this.netValue =  documentPosition.getDouble("NetValue");
        this.grossValue =  documentPosition.getDouble("GrossValue");
    }

    public DocumentPosition copy(){
        return new DocumentPosition(this.id, this.documentId, this.item,
                this.ordinal, this.quantity, this.netValue, this.grossValue);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String document) {
        this.documentId = document;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;

        this.setChanged();
        this.notifyObservers("quantity");
    }

    public double getNetValue() {
        return netValue;
    }

    public void setNetValue(double netValue) {
        this.netValue = netValue;
    }

    public double getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(double grossValue) {
        this.grossValue = grossValue;
    }

}