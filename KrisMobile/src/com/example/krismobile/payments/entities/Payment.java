package com.example.krismobile.payments.entities;

import android.os.Parcel;
import android.os.Parcelable;


public class Payment implements Parcelable{
	
	private String paymentName;
	private double value;
	private String paymentDate;
	private String contractorCode;
	private boolean isPaid;

	public Payment(){
		this.paymentName = "";
		this.value = 0;
		this.paymentDate = "";
		this.contractorCode = "";
		this.isPaid = false;
	}
	
	public Payment(String paymentName, double value, String paymentDate, String contractorCode, boolean isPaid) {
		this.paymentName = paymentName;
		this.value = value;
		this.paymentDate = paymentDate;
		this.contractorCode = contractorCode;
		this.isPaid = isPaid;
	}

	public Payment(Parcel in) {
		this.paymentName = in.readString();
		this.value = in.readDouble();
		this.paymentDate = in.readString();
		this.contractorCode = in.readString();
		this.isPaid = in.readInt() == 1;
	}

	public double getValue() {
		return value;
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

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getContractorCode() {
		return contractorCode;
	}

	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
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
		dest.writeString(paymentName);
		dest.writeDouble(value);
		dest.writeString(paymentDate);
		dest.writeString(contractorCode);
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
