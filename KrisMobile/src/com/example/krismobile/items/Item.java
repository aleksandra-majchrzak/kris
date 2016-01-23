package com.example.krismobile.items;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

import com.couchbase.lite.Document;
import com.example.krismobile.contractors.Contractor;
import com.example.krismobile.database.managers.ItemsManager;
import com.example.krismobile.items.entities.Material;
import com.example.krismobile.items.entities.Price;
import com.example.krismobile.items.entities.Size;
import com.example.krismobile.warehouses.stocks.ItemStocks;

public class Item implements Parcelable{
	
	private String id;
	private String code;
	private String name;
	private String size;
	private String material;
//	private Size size;
//	private Material material;	
	private Price price;
	private String description;
	private String type;
	private List<ItemStocks> itemStocks;
	// zdjêcie ??chyba by sie przydalo
	
	
	public Item() {
		this.id = "";
		this.code = "";
		this.name = "";		
		this.size = "";
		this.material = "";
		//this.size = null;
		//this.material = null;
		this.price = null;
		this.description = "";
		this.type = "";
		 new ArrayList<ItemStocks>();
	}
	
	public Item(String id, String code, String name,
			String size, String material, Price price,
			String description, String type, ArrayList<ItemStocks> itemStocks) {

		this.id = id;
		this.code = code;
		this.name = name;
		this.size = size;
		this.material = material;
		this.price = price;
		this.description = description;
		this.type = type;
		this.itemStocks = itemStocks;
	}
	
	public Item(Parcel in){
		this.id = in.readString();
		this.code = in.readString();
		this.name = in.readString();
		this.size = in.readString();
		this.material = in.readString();
		//this.size = in.readParcelable(Size.class.getClassLoader());
		//this.material = in.readParcelable(Material.class.getClassLoader());
		this.price = in.readParcelable(Price.class.getClassLoader());
		this.description = in.readString();
		this.type = in.readString();
		in.readTypedList(this.itemStocks, ItemStocks.CREATOR);
	}
	
	public Item(Document document){

		Map<?,?> props = document.getProperties();
		
		//Document documentContractor = ItemsManager.getInstance().getContractor((String)props.get("ContractorId"));
		
		//dodaj odtworzenie z Document (konstruktor)
		Document priceDoc = ItemsManager.getInstance().getItemPrice((String) document.getProperty("PriceId"));
		Price price = new Price(priceDoc);
		
		
		List<Document> itemStocksDocs = ItemsManager.getInstance().getItemStocks(document.getId());
		ArrayList<ItemStocks> itemStocks = new ArrayList<ItemStocks>();
		
		for(Document stockDoc : itemStocksDocs){
			itemStocks.add(new ItemStocks(stockDoc));
		}
		
//		Size size = new Size();
//		Material material = new Material();
		
		this.id = document.getId();
		this.code = (String)props.get("Code");
		this.name = (String)props.get("Name");
		this.size = (String)props.get("Size");
		this.material = (String)props.get("Material");
		this.price = price;
		this.description = (String)props.get("Description");
		this.type = (String)props.get("Type");
		this.itemStocks = itemStocks;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ItemStocks> getItemStocks() {
		return itemStocks;
	}

	public void setItemStocks(List<ItemStocks> itemStocks) {
		this.itemStocks = itemStocks;
	}

	public static Parcelable.Creator<Item> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(code);
		dest.writeString(name);
		dest.writeString(size);
		dest.writeString(material);
		dest.writeParcelable(price, flags);
		dest.writeString(description);
		dest.writeString(type);
		dest.writeTypedList(itemStocks);
	}	
	
	public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in); 
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
