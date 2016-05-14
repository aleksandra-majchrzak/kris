package com.web.kris.main.entities;

import com.couchbase.client.java.document.*;
import com.couchbase.client.java.document.json.JsonObject;
import com.web.kris.main.enums.ContractorType;

/**
 * Created by Mohru on 2016-01-26.
 */
public class Contractor {

    private String id;
    private String code;
    private ContractorType type;
    private String address;
    private String description;
    private String NIP;

    public Contractor(){
        this.id = "";
        this.code = "";
        this.type = ContractorType.Buyer;
        this.address = "";
        this.description = "";
        this.NIP = "";
    }

    public Contractor(String id, String code, int typeId, String address,
                      String description, String NIP) {

        this.id = id;
        this.code = code;
        this.type = ContractorType.values()[typeId];
        this.address = address;
        this.description = description;
        this.NIP = NIP;
    }

    public Contractor(JsonDocument document){
        JsonObject content = document.content();

        this.id = document.id();
        this.code = content.getString("Code");
        this.type = ContractorType.value(content.getInt("TypeId"));
        this.address = content.getString("Address");
        this.description = content.getString("Description");
        this.NIP = content.getString("NIP");
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

    public ContractorType getType() {
        return type;
    }

    public void setType(ContractorType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String nIP) {
        NIP = nIP;
    }
}
