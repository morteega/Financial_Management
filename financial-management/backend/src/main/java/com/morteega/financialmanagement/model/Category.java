package com.morteega.financialmanagement.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Category {
    String name;

    public Category(){}

    public Category(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    
}
