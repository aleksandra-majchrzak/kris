package com.web.kris.main.enums;

/**
 * Created by Mohru on 2016-02-01.
 */
public enum ContractorType {
    Seller(0), Buyer(1);

    private int value;
    private String name;

    ContractorType(int value){
        this.value = value;

        if(value == 0)
            name = "Kupiec";
        else
            name = "Dostawca";
    }

    public String getName(){
        return this.name;
    }

}
