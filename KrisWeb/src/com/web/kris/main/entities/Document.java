package com.web.kris.main.entities;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.web.kris.main.enums.DocumentType;
import com.web.kris.main.enums.PaymentForm;
import com.web.kris.main.managers.ContractorsManager;

import java.util.Date;

/**
 * Created by Mohru on 2016-01-26.
 */
public class Document {

    private String id;
    private String number;
    private DocumentType type;
    private Contractor contractor;
    private Date documentDate;
    private Date paymentDate;
    private String description;
    private PaymentForm paymentForm;
    private double netValue;
    private double grossValue;


    public Document() {
        this.id = "";
        this.number = "";
        this.type = DocumentType.values()[0];
        this.contractor = new Contractor();
        this.documentDate = new Date();
        this.paymentDate = new Date();
        this.description = "";
        this.paymentForm = PaymentForm.Cash;
        this.netValue = 0.0;
        this.netValue = 0.0;
    }

    public Document(String id, String number, int typeId,
                        Contractor contractor, Date documentDate, Date paymentDate,
                        String description, int paymentForm, double netValue, double grossValue) {

        this.id = id;
        this.number = number;
        this.type = DocumentType.values()[typeId];
        this.contractor = contractor;
        this.documentDate = documentDate;
        this.paymentDate = paymentDate;
        this.description = description;
        this.paymentForm = PaymentForm.values()[paymentForm];
        this.netValue = netValue;
        this.grossValue = grossValue;
    }

    public Document(JsonDocument document) {

        JsonObject content = document.content();

        this.id = document.id();
        this.number = content.getString("Number");
        this.type = DocumentType.values()[content.getInt("TypeId")];
        this.contractor = ContractorsManager.getInstance().getContractor(content.getString("ContractorId"));
        this.documentDate = new Date(Long.valueOf(content.getString("DocumentDate")));
        this.paymentDate = new Date (Long.valueOf(content.getString("PaymentDate")));
        this.description = content.getString("Description");
        this.paymentForm = PaymentForm.values()[content.getInt("PaymentForm")];
        this.netValue = content.getDouble("NetValue");
        this.grossValue = content.getDouble("GrossValue");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentForm getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(int paymentForm) {
        this.paymentForm = PaymentForm.values()[paymentForm];
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
