package com.example.krismobile.payments.entities;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;


public class Payment implements Parcelable{
	
	private String id;
	private String paymentName;
	private double value;
	private Date paymentDate;
	private String description;
	private boolean isPaid;

	public Payment(){
		this.id = "";
		this.paymentName = "";
		this.value = 0;
		this.paymentDate = new Date();
		this.description = "";
		this.isPaid = false;
	}
	
	public Payment(String id, String paymentName, double value, Date paymentDate, String description, boolean isPaid) {
		this.id = id;
		this.paymentName = paymentName;
		this.value = value;
		this.paymentDate = paymentDate;
		this.description = description;
		this.isPaid = isPaid;
	}

	public Payment(Parcel in) {
		this.id = in.readString();
		this.paymentName = in.readString();
		this.value = in.readDouble();
		this.paymentDate = new Date(in.readLong());
		this.description = in.readString();
		this.isPaid = in.readInt() == 1;
	}

	public double getValue() {
		return value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
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

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(paymentName);
		dest.writeDouble(value);
		dest.writeLong(paymentDate.getTime());
		dest.writeString(description);
		dest.writeInt(isPaid ? 1 : 0);
	}
	
	public static final Parcelable.Creator<Payment> CREATOR = new Parcelable.Creator<Payment>() {
        public Payment createFromParcel(Parcel in) {
            return new Payment(in); 
        }

        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

}
