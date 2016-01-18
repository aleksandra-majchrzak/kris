package com.example.krismobile.documents;

import java.util.Date;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

import com.couchbase.lite.Document;
import com.example.krismobile.contractors.Contractor;
import com.example.krismobile.database.managers.ContractorsManager;

public class KrisDocument implements Parcelable{
	
	private String id;
	private String number;
	private int typeId;
	private Contractor contractor;
	private Date documentDate;
	private Date paymentDate;
	private String description;
	private int paymentForm;
	private double value;
	
	
	public KrisDocument() {
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
	
	public KrisDocument(String id, String number, int typeId,
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
	
	public KrisDocument(Parcel in){
		this.id = in.readString();
		this.number = in.readString();
		this.typeId = in.readInt();
		this.contractor = in.readParcelable(Contractor.class.getClassLoader());
		this.documentDate = new Date(in.readLong());
		this.paymentDate = new Date(in.readLong());
		this.description = in.readString();
		this.paymentForm = in.readInt();
		this.value = in.readDouble();
	}
	
	public KrisDocument(Document document){

		Map<?,?> props = document.getProperties();
		
		Document documentContractor = ContractorsManager.getInstance().getContractor((String)props.get("ContractorId"));
		
		Contractor contractor = new Contractor(documentContractor);
		
		this.id = document.getId();
		this.number = (String)props.get("Number");
		this.typeId = (Integer)props.get("TypeId");
		this.contractor = contractor;
		this.documentDate = new Date((Long)props.get("DocumentDate"));
		this.paymentDate = new Date((Long)props.get("PaymentDate"));
		this.description = (String)props.get("Description");
		this.paymentForm = (Integer)props.get("PaymentForm");
		this.value = (Double)props.get("Value");
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

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(number);
		dest.writeInt(typeId);
		dest.writeParcelable(contractor, flags);
		dest.writeLong(documentDate.getTime());
		dest.writeLong(paymentDate.getTime());
		dest.writeString(description);
		dest.writeInt(paymentForm);
		dest.writeDouble(value);
	}	
	
	public static final Parcelable.Creator<KrisDocument> CREATOR = new Parcelable.Creator<KrisDocument>() {
        public KrisDocument createFromParcel(Parcel in) {
            return new KrisDocument(in); 
        }

        public KrisDocument[] newArray(int size) {
            return new KrisDocument[size];
        }
    };
}
