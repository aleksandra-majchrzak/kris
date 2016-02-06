package com.web.kris.main.enums;

/**
 * Created by Mohru on 2016-02-05.
 */
public enum PaymentForm {
    Cash(0), Transfer(1), COD(2), Card(3);

    private int value;
    private String name;

    PaymentForm(int value){
        this.value = value;

        if(value == 0)
            name = "Gotowka";
        else if (value == 1 )
            name = "Przelew";
        else if(value == 2)
            name = "Pobranie";
        else
            name = "Karta";
    }

    public String getName(){
        return this.name;
    }

    public int getValue(){
        return this.value;
    }
}
