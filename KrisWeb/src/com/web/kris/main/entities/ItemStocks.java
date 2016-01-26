package com.web.kris.main.entities;

/**
 * Created by Mohru on 2016-01-26.
 */
public class ItemStocks {
    private int itemId;
    private int warehouseId;
    private double stocks;

    public ItemStocks(int itemId, int warehouseId, double stocks) {

        this.itemId = itemId;
        this.warehouseId = warehouseId;
        this.stocks = stocks;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public double getStocks() {
        return stocks;
    }

    public void setStocks(double stocks) {
        this.stocks = stocks;
    }

}
