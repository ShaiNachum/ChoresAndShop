package com.example.choresandshop.Model;

public class Chore {

    private String name;
    private int price;


    public Chore() {
    }

    public String getName() {
        return name;
    }


    public Chore setName(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Chore setPrice(int price) {
        this.price = price;
        return this;
    }
}
