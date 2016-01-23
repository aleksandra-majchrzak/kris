package com.example.krismobile.warehouses.stocks;

import java.util.Map;

import com.couchbase.lite.Document;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemStocks implements Parcelable{
	
	private int itemId;
	private int warehouseId;
	private double stocks;

	public ItemStocks(Document doc) {
		Map<?,?> props = doc.getProperties();
		this.itemId = (Integer)props.get("ItemId") ;
		this.warehouseId = (Integer)props.get("WarehouseId") ;
		this.stocks = (Double)props.get("Stocks") ;
	}
	
	public ItemStocks(int itemId, int warehouseId, double stocks) {

		this.itemId = itemId;
		this.warehouseId = warehouseId;
		this.stocks = stocks;
	}

	public ItemStocks(Parcel in) {
		this.itemId = in.readInt();
		this.warehouseId = in.readInt();
		this.stocks = in.readDouble();
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public double getStocks() {
		return stocks;
	}

	public void setStocks(double stocks) {
		this.stocks = stocks;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(itemId);
		dest.writeInt(warehouseId);
		dest.writeDouble(stocks);
	}
	
	public static final Parcelable.Creator<ItemStocks> CREATOR = new Parcelable.Creator<ItemStocks>() {
        public ItemStocks createFromParcel(Parcel in) {
            return new ItemStocks(in); 
        }

        public ItemStocks[] newArray(int size) {
            return new ItemStocks[size];
        }
    };

}
