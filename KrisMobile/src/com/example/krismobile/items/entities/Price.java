package com.example.krismobile.items.entities;

import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

import com.couchbase.lite.Document;

public class Price implements Parcelable{
	
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

	public Price(Parcel in) {
		this.id = in.readString();
		this.netPrice = in.readDouble();
		this.grossPrice = in.readDouble();
	}
	
	public Price(Document doc) {
		Map<?,?> props = doc.getProperties();
		this.id = (String)props.get("Id");
		this.netPrice = (Double)props.get("NetPrice");
		this.grossPrice = (Double)props.get("GrossPrice");
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeDouble(this.netPrice);
		dest.writeDouble(this.grossPrice);
		
	}
	
	public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        public Price createFromParcel(Parcel in) {
            return new Price(in); 
        }

        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

}
