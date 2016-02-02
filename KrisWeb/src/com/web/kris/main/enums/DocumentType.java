package com.web.kris.main.enums;

/**
 * Created by Mohru on 2016-02-02.
 */
public enum DocumentType {
    ZS(0), FS(1), WM(2);

    private int value;
    private String name;

    DocumentType(int value){
        this.value = value;

        if(value == 0)
            name = "Zamówienie sprzedazy";
        else if (value == 1 )
            name = "Faktura sprzedarzy";
        else
            name = "Wydanie magazynowe";
    }

    public String getName(){
        return this.name;
    }
}
