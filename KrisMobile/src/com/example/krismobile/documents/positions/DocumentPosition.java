package com.example.krismobile.documents.positions;

import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

import com.couchbase.lite.Document;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.items.Item;

public class DocumentPosition implements Parcelable{
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
	
	public DocumentPosition(Parcel in) {

		this.id = in.readString();
		this.documentId = in.readString();
		this.item = in.readParcelable(Item.class.getClassLoader());
		this.ordinal = in.readInt();
		this.quantity = in.readDouble();
		this.netValue = in.readDouble();
		this.grossValue = in.readDouble();
	}
	
	public DocumentPosition(Document doc) {
		Map<?,?> props = doc.getProperties();
		
		Document documentItem = ItemsManager.getInstance().getItem((String)props.get("ItemId"));
		
		Item item = new Item(documentItem);
		
		this.id = doc.getId();
		this.documentId = (String)props.get("DocumentId");
		this.item = item;
		this.ordinal = (Integer)props.get("Ordinal");
		this.quantity = (Double)props.get("Quantity");
		this.netValue = (Double)props.get("NetValue");
		this.grossValue = (Double)props.get("GrossValue");
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(documentId);
		dest.writeParcelable(item, flags);
		dest.writeInt(ordinal);
		dest.writeDouble(quantity);
		dest.writeDouble(netValue);
		dest.writeDouble(grossValue);
	}	

	public static final Parcelable.Creator<DocumentPosition> CREATOR = new Parcelable.Creator<DocumentPosition>() {
        public DocumentPosition createFromParcel(Parcel in) {
            return new DocumentPosition(in); 
        }

        public DocumentPosition[] newArray(int size) {
            return new DocumentPosition[size];
        }
    };
	
}
