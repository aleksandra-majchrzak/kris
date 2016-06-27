package com.web.kris.main.entities;

import com.couchbase.client.java.document.json.JsonObject;

/**
 * Created by Mohru on 2016-01-26.
 */
public class ItemStocks {
    private String itemId;
    private String warehouseId;
    private double stocks;

    public ItemStocks() {

        this.itemId = "";
        this.warehouseId = "";
        this.stocks = 0;
    }

    public ItemStocks(String itemId, String warehouseId, double stocks) {

        this.itemId = itemId;
        this.warehouseId = warehouseId;
        this.stocks = stocks;
    }

    public ItemStocks(JsonObject itemStocks) {

        this.itemId = itemStocks.getString("ItemId");
        this.warehouseId = itemStocks.getString("WarehouseId");
        this.stocks = itemStocks.getDouble("Stocks");
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public double getStocks() {
        return stocks;
    }

    public void setStocks(double stocks) {
        this.stocks = stocks;
    }

}
