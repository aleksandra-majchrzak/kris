package com.example.krismobile.contractors;

public class Contractor {
	
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

}
