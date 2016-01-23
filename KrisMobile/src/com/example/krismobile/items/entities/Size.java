package com.example.krismobile.items.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Size implements Parcelable{
	
	private int id;
	private String name;

	public Size() {

		this.id = 0;
		this.name ="";
	}
	
	public Size(int id, String name) {

		this.id = id;
		this.name = name;
	}

	public Size(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.name);
		
	}
	
	public static final Parcelable.Creator<Size> CREATOR = new Parcelable.Creator<Size>() {
        public Size createFromParcel(Parcel in) {
            return new Size(in); 
        }

        public Size[] newArray(int size) {
            return new Size[size];
        }
    };

}
