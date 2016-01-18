package com.example.krismobile.contractors;

import java.util.Map;

import com.couchbase.lite.Document;

import android.os.Parcel;
import android.os.Parcelable;

public class Contractor implements Parcelable{
	
	private String id;
	private String code;
	private int typeId;
	private String address;
	private String description;
	private String NIP;
	
	public Contractor(){
		this.id = "";
		this.code = "";
		this.typeId = 1;
		this.address = "";
		this.description = "";
		this.NIP = "";
	}
	
	public Contractor(String id, String code, int typeId, String address,
			String description, String NIP) {

		this.id = id;
		this.code = code;
		this.typeId = typeId;
		this.address = address;
		this.description = description;
		this.NIP = NIP;
	}
	
	public Contractor(Parcel in){

		this.id = in.readString();
		this.code = in.readString();
		this.typeId = in.readInt();
		this.address = in.readString();
		this.description = in.readString();
		this.NIP = in.readString();
    }
	
	public Contractor(Document document){

		Map<?,?> contractorProps = document.getProperties();
		
		this.id = document.getId();
		this.code = (String)contractorProps.get("Code");
		this.typeId = (Integer)contractorProps.get("TypeId");
		this.address = (String)contractorProps.get("Address");
		this.description = (String)contractorProps.get("Description");
		this.NIP = (String)contractorProps.get("NIP");
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

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
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

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(code);
		dest.writeInt(typeId);
		dest.writeString(address);
		dest.writeString(description);
		dest.writeString(NIP);
		
	}
	
	public static final Parcelable.Creator<Contractor> CREATOR = new Parcelable.Creator<Contractor>() {
        public Contractor createFromParcel(Parcel in) {
            return new Contractor(in); 
        }

        public Contractor[] newArray(int size) {
            return new Contractor[size];
        }
    };

}
