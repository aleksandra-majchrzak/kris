package com.web.kris.main.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohru on 2016-01-26.
 */
public class Item {

    private String id;
    private String code;
    private String name;
    private String size;
    private String material;
    private Price price;
    private String description;
    private String type;
    private List<ItemStocks> itemStocks;
    // zdjęcie ??chyba by sie przydalo


    public Item() {
        this.id = "";
        this.code = "";
        this.name = "";
        this.size = "";
        this.material = "";
        this.price = null;
        this.description = "";
        this.type = "";
        new ArrayList<ItemStocks>();
    }

    public Item(String id, String code, String name,
                String size, String material, Price price,
                String description, String type, ArrayList<ItemStocks> itemStocks) {

        this.id = id;
        this.code = code;
        this.name = name;
        this.size = size;
        this.material = material;
        this.price = price;
        this.description = description;
        this.type = type;
        this.itemStocks = itemStocks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ItemStocks> getItemStocks() {
        return itemStocks;
    }

    public void setItemStocks(List<ItemStocks> itemStocks) {
        this.itemStocks = itemStocks;
    }
}