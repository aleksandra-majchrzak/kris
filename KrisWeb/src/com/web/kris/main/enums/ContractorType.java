package com.web.kris.main.enums;

/**
 * Created by Mohru on 2016-02-01.
 */
public enum ContractorType {
    Seller(1), Buyer(2);

    private int value;
    private String name;

    ContractorType(int value){
        this.value = value;

        if(value == 1)
            name = "Dostawca";
        else
            name = "Kupiec";
    }

    public static ContractorType value(int value){
        if(value == 1)
            return Seller;
        else
            return Buyer;
    }

    public String getName(){
        return this.name;
    }

    public int getValue(){
        return this.value;
    }

}
