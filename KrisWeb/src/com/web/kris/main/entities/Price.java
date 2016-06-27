package com.web.kris.main.entities;

import com.couchbase.client.java.document.json.JsonObject;

/**
 * Created by Mohru on 2016-01-26.
 */
public class Price {

    private String id;
    private double netPrice;
    private double grossPrice;

    public Price(){

        this.id = "";
        this.netPrice = 0;
        this.grossPrice = 0;
    }

    public Price(String id, double netPrice, double grossPrice) {
        this.id = id;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
    }

    public Price(JsonObject price) {
        this.id = price.getString("id");
        this.netPrice = price.getDouble("NetPrice");
        this.grossPrice = price.getDouble("GrossPrice");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(double grossPrice) {
        this.grossPrice = grossPrice;
    }

}
