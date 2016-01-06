package com.example.krismobile.main.entities;

public class MenuItem {
	
	String name;
	int resourceId;

	public MenuItem(String name, int resourceId) {
		super();
		this.name = name;
		this.resourceId = resourceId;
	}
	
	public String getName() {
		return name;
	}

	public int getResourceId() {
		return resourceId;
	}

}
