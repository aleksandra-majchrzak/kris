package com.example.krismobile.documents.entities;

public enum DocumentType {
	ZS(0), FS(1), WM(2);
	
	private int value;
    private String name;

    DocumentType(int value){
        this.value = value;

        if(value == 0)
            name = "ZS";
        else if (value == 1 )
            name = "FS";
        else
            name = "WM";
    }

    public String getName(){
        return this.name;
    }

    public int getValue(){
        return this.value;
    }
    
    public static String getDocTypeName(int value){
    	if(value == 0)
            return "ZS";
        else if (value == 1 )
        	return "FS";
        else
        	return "WM";
    }
}
