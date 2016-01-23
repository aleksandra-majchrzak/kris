package com.example.krismobile.items.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Material implements Parcelable{
	
	private int id;
	private String name;
	private String description;

	public Material() {
		this.id = 0;
		this.name = "";
		this.description = "";
	}
	
	public Material(int id, String nazwa, String description) {

		this.id = id;
		this.name = nazwa;
		this.description = description;
	}

	public Material(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.description = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazwa() {
		return name;
	}

	public void setNazwa(String nazwa) {
		this.name = nazwa;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(description);
		
	}
	
	public static final Parcelable.Creator<Material> CREATOR = new Parcelable.Creator<Material>() {
        public Material createFromParcel(Parcel in) {
            return new Material(in); 
        }

        public Material[] newArray(int size) {
            return new Material[size];
        }
    };

}
