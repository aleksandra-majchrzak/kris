package com.web.kris.main.entities;

import java.util.Date;

/**
 * Created by Mohru on 2016-01-26.
 */
public class Document {

    private String id;
    private String number;
    private int typeId;
    private Contractor contractor;
    private Date documentDate;
    private Date paymentDate;
    private String description;
    private int paymentForm;
    private double value;


    public Document() {
        this.id = "";
        this.number = "";
        this.typeId = 0;
        this.contractor = new Contractor();
        this.documentDate = new Date();
        this.paymentDate = new Date();
        this.description = "";
        this.paymentForm = 0;
        this.value = 0.0;
    }

    public Document(String id, String number, int typeId,
                        Contractor contractor, Date documentDate, Date paymentDate,
                        String description, int paymentForm, double value) {

        this.id = id;
        this.number = number;
        this.typeId = typeId;
        this.contractor = contractor;
        this.documentDate = documentDate;
        this.paymentDate = paymentDate;
        this.description = description;
        this.paymentForm = paymentForm;
        this.value = value;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public int getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(int paymentForm) {
        this.paymentForm = paymentForm;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
