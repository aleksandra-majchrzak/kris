package com.example.krismobile.documents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

import com.couchbase.lite.Document;
import com.example.krismobile.contractors.Contractor;
import com.example.krismobile.database.managers.ContractorsManager;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.documents.entities.DocumentType;
import com.example.krismobile.documents.positions.DocumentPosition;
import com.example.krismobile.documents.positions.DocumentPositionsList;
import com.example.krismobile.warehouses.stocks.ItemStocks;

public class KrisDocument implements Parcelable{
	
	private String id;
	private String number;
	private int typeId;
	private Contractor contractor;
	private Date documentDate;
	private Date paymentDate;
	private String description;
	private int paymentForm;
	private double netValue;
	private double grossValue;
	
	private DocumentPositionsList positionsList;
	
	public KrisDocument(int docTypeId) {
		
		Date today = new Date();
		
		// doc type musi byc ustawiany dynamicznie !!!
		Document numerator = DocumentsManager.getInstance().getDocumentNumerator(docTypeId, today.getMonth() +1 , today.getYear() + 1900);
		int counter = (numerator != null) ? (Integer)numerator.getProperty("Counter") : 1;
		
		String number = DocumentType.getDocTypeName(docTypeId) 
				+"/"+ String.valueOf(today.getMonth() +1) 
				+"/"+ String.valueOf(today.getYear() + 1900)
				+"/"+"usrName"
				+"/"+String.valueOf(counter);
		
		this.id = "";
		this.number = number;
		this.typeId = docTypeId;
		this.contractor = new Contractor();
		this.documentDate = today;
		this.paymentDate = today;
		this.description = "";
		this.paymentForm = 0;
		this.netValue = 0.0;
		this.grossValue = 0.0;
		this.positionsList = new DocumentPositionsList();
	}
	
	private KrisDocument(String id, String number, int typeId,
			Contractor contractor, Date documentDate, Date paymentDate,
			String description, int paymentForm, double netValue, double grossValue, DocumentPositionsList positionsList) {

		this.id = id;
		this.number = number;
		this.typeId = typeId;
		this.contractor = contractor;
		this.documentDate = documentDate;
		this.paymentDate = paymentDate;
		this.description = description;
		this.paymentForm = paymentForm;
		this.netValue = netValue;
		this.grossValue = grossValue;
		this.positionsList = positionsList;
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
		this.netValue = in.readDouble();
		this.grossValue = in.readDouble();
		in.readTypedList(this.positionsList, DocumentPosition.CREATOR);
	}
	
	public KrisDocument(Document document){

		Map<?,?> props = document.getProperties();
		
		Document documentContractor = ContractorsManager.getInstance().getContractor((String)props.get("ContractorId"));
		
		Contractor contractor = new Contractor(documentContractor);
		
		DocumentPositionsList positionsList = DocumentsManager.getInstance().getDocumentPositions(document.getId());
		
		this.id = document.getId();
		this.number = (String)props.get("Number");
		this.typeId = (Integer)props.get("TypeId");
		this.contractor = contractor;
		this.documentDate = new Date((Long)props.get("DocumentDate"));
		this.paymentDate = new Date((Long)props.get("PaymentDate"));
		this.description = (String)props.get("Description");
		this.paymentForm = (Integer)props.get("PaymentForm");
		this.netValue = (Double)props.get("NetValue");
		this.grossValue = (Double)props.get("GrossValue");
		this.positionsList = positionsList;
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

	public DocumentPositionsList getPositionsList() {
		return positionsList;
	}

	public void setPositionsList(DocumentPositionsList positionsList) {
		this.positionsList = positionsList;
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
		dest.writeDouble(netValue);
		dest.writeDouble(grossValue);
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
